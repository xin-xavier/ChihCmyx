package com.chih.mecm.cmyx.main.news.chat

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.NetworkUtils
import com.chih.mecm.cmyx.ChihApplication
import com.chih.mecm.cmyx.app.APixelActivity
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.ACTION_CHAT_MESSAGE_RECEIVER
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.PARAM_CHAT_MESSAGE_RECEIVER
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.PING
import com.chih.mecm.cmyx.app.broadcast.ScreenLockReceiver
import com.chih.mecm.cmyx.app.broadcast.TachycardiaReceiver
import com.chih.mecm.cmyx.http.url.DNSConfig
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.*
import java.util.concurrent.TimeUnit

class ChatSocketService : Service() {

    private val binder = ChatSocketBinder()
    private val intent = Intent()
    private val uri = URI.create(DNSConfig.WS_URL)
    private var client: ChatSocketClient? = null

    @Volatile
    private var reconnectionInterval = 0L

    // 4 分钟
    private var heartbeatInterval = 1000 * 60 * 4L

    private var disposable: Disposable? = null

    private val screenLockReceiver = object : ScreenLockReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                when (it.action) {
                    Intent.ACTION_SCREEN_ON -> {
                        val topActivity = ActivityUtils.getTopActivity()
                        if (topActivity is APixelActivity) {
                            topActivity.finishAffinity()
                        }
                    }
                    Intent.ACTION_SCREEN_OFF -> {
                        val aPixelIntent =
                            Intent(this@ChatSocketService, APixelActivity::class.java)
                        aPixelIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(aPixelIntent)
                    }
                    else -> {
                    }
                }
                sendMessage(PING)
            }
        }
    }

    private val networkStatusChangedReceiver = object : NetworkUtils.OnNetworkStatusChangedListener {
        override fun onDisconnected() {
            sendMessage(PING)
        }

        override fun onConnected(networkType: NetworkUtils.NetworkType?) {
            sendMessage(PING)
        }
    }

    private val tachycardiaReceiver=object : TachycardiaReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            sendMessage(PING)
        }
    }

    override fun onCreate() {
        super.onCreate()
        intent.action = ACTION_CHAT_MESSAGE_RECEIVER
        screenLockReceiver.registerReceiver(this)
        NetworkUtils.registerNetworkStatusChangedListener(networkStatusChangedReceiver)
        tachycardiaReceiver.registerReceiver(this)
    }

    @SuppressLint("CheckResult")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createSocketClient()
        // 发送心跳
        //timer.schedule(heartRunnable, 1000, heartbeatInterval)
        disposable = Observable.interval(1000 * 3L, heartbeatInterval, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe {
                sendMessage(PING)
            }
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
    private fun connectBlocking(connect: Boolean = true) {
        ChihApplication.easyCachedExecutor.execute {
            client?.connectBlocking()
        }
        ChihApplication.easyCachedExecutor.async(
            {
                if (connect) {
                    client?.connectBlocking()
                } else {
                    client?.reconnectBlocking()
                }
            },
            { boolean ->
                if (boolean == true) {
                    if (disposable == null || disposable!!.isDisposed) {
                        disposable =
                            Observable.interval(1000 * 3L, heartbeatInterval, TimeUnit.MILLISECONDS)
                                .subscribeOn(Schedulers.io())
                                .subscribe {
                                    sendMessage(PING)
                                }
                    }
                } else {
                    //Thread.sleep(reconnectionInterval)
                    upDataReconnectionInterval()
                    ChihApplication.easyCachedExecutor.setDelay(reconnectionInterval)
                        .execute {
                            connectBlocking()
                        }
                }
            }
        )
    }

    @Synchronized
    private fun upDataReconnectionInterval() {
        if (reconnectionInterval == 0L) {
            reconnectionInterval = 250L
        } else if (reconnectionInterval < 1000 * 64L) {
            reconnectionInterval *= 2L
        }
    }

    // 重连
    private fun reconnection() {
        // 先取消心跳包
        disposable?.dispose()
        connectBlocking(false)
    }

    private fun sendMessage(message: String) {
        if (client == null) {
            createSocketClient()
        } else {
            client?.let {
                if (it.isOpen) {
                    it.send(message)
                } else {
                    reconnection()
                }
            }
        }
    }

    private fun closeConnect() {
        client?.close()
        client = null
        disposable?.dispose()
        screenLockReceiver.unRegisterReceiver(this)
        NetworkUtils.unregisterNetworkStatusChangedListener(networkStatusChangedReceiver)
        tachycardiaReceiver.unregisterReceiver(this)
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
        closeConnect()
    }

    inner class ChatSocketBinder : Binder() {
        fun getService(): ChatSocketService = this@ChatSocketService
    }

}