package com.chih.mecm.cmyx.main.news.chat

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.*
import androidx.annotation.NonNull
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.chih.mecm.cmyx.ChihApplication
import com.chih.mecm.cmyx.app.AppManager
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.ACTION_CHAT_MESSAGE_RECEIVER
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.PARAM_CHAT_MESSAGE_RECEIVER
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.PING
import com.chih.mecm.cmyx.http.url.DNSConfig
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.notify
import okhttp3.internal.wait
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.*
import java.util.concurrent.TimeUnit


class ChatSocketService : Service() {

    private val binder = ChatSocketBinder()
    private val intent = Intent()
    private val uri = URI.create(DNSConfig.WS_URL)
    private var client: ChatSocketClient? = null

    // 4 分钟
    private var heartbeatInterval = 1000 * 60 * 4L
    private val timer = Timer()
    private val heartRunnable = object : TimerTask() {
        override fun run() {
            if (client == null) {
                createSocketClient()
            } else {
                client?.let { client ->
                    {
                        if (client.isClosed) {
                            if (AppManager.isLogin()) {
                                reconnection()
                            }
                        } else if (client.isOpen) {
                            sendMessage(PING)
                        } else {
                            connectBlocking()
                        }
                    }
                }
            }
        }
    }

    private var reconnectionInterval = 0L
    private var isLoop = false
    private val what = 10

    @SuppressLint("HandlerLeak")
    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(@NonNull msg: Message) {
            super.handleMessage(msg)
            if (msg.what == what) {
                isLoop = client != null && client!!.isOpen
            }
        }
    }

    private val thread = Thread {
        while (isLoop) {
            if (reconnectionInterval == 0L) {
                reconnectionInterval = 250L
            } else {
                Thread.sleep(reconnectionInterval)
                if (reconnectionInterval < 1000 * 64L) {
                    reconnectionInterval *= 2L
                }
            }
            val message = handler.obtainMessage()
            message.what = what
            handler.sendMessage(message)
        }
    }

    override fun onCreate() {
        super.onCreate()
        intent.action = ACTION_CHAT_MESSAGE_RECEIVER
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createSocketClient()
        // 发送心跳
        timer.schedule(heartRunnable, 1000, heartbeatInterval)
        //return super.onStartCommand(intent, flags, startId)
        // 如果此服务的进程在启动时被杀死 系统将尝试重新创建服务。
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    private fun createSocketClient() {
        client = object : ChatSocketClient(uri) {
            @SuppressLint("CheckResult")
            override fun onMessage(message: String?) {
                super.onMessage(message)
                // 发送本地广播
                // 自动应答
                Observable.timer(1000 * 30L, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        sendMessage("Hello")
                    }
                intent.putExtra(PARAM_CHAT_MESSAGE_RECEIVER, message ?: "")
                LocalBroadcastManager.getInstance(ChihApplication.appContext)
                    .sendBroadcast(intent)
            }

            override fun onOpen(handshakedata: ServerHandshake?) {
                super.onOpen(handshakedata)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                super.onClose(code, reason, remote)
            }

            override fun onError(ex: Exception?) {
                super.onError(ex)
            }
        }
        // 连接
        connectBlocking()
    }

    @SuppressLint("CheckResult")
    private fun connectBlocking() {
        Observable.just(Any())
            .subscribeOn(Schedulers.io())
            .subscribe {
                client?.connectBlocking()
            }
    }

    // 重连
    private fun reconnection() {
        // 先取消心跳包
        timer.cancel()
        thread.start()
    }

    private fun sendMessage(message: String) {
        client?.let { it ->
            if (it.isOpen) {
                it.send(message)
            } else {
                client = null
                createSocketClient()
            }
        }
    }

    private fun closeConnect() {
        client?.close()
        client = null
    }

    /**
     * https://developer.android.google.cn/guide/components/bound-services#Lifecycle
     * 如果您的服务已启动并接受绑定，那么当系统调用您的 onUnbind() 方法时，如果您想在客户端下一次绑定到服务时接收 onRebind() 调用，可以选择返回 true。
     * onRebind() 返回空值，但客户端仍会在其 onServiceConnected() 回调中接收 IBinder。
     */
    override fun onUnbind(intent: Intent?): Boolean {
        //return super.onUnbind(intent)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    inner class ChatSocketBinder : Binder() {
        fun getService(): ChatSocketService = this@ChatSocketService
    }

}