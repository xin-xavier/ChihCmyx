package com.chih.mecm.cmyx.main.news.chat

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.NetworkUtils
import com.chih.mecm.cmyx.ChihApplication
import com.chih.mecm.cmyx.MainActivity
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.app.APixelActivity
import com.chih.mecm.cmyx.app.AppManager
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.ACTION_CHAT_MESSAGE_RECEIVER
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.PARAM_CHAT_MESSAGE_RECEIVER
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.PING
import com.chih.mecm.cmyx.app.broadcast.ScreenLockReceiver
import com.chih.mecm.cmyx.app.broadcast.TachycardiaReceiver
import com.chih.mecm.cmyx.app.notification.NotificationChannels.DEFAULT
import com.chih.mecm.cmyx.http.url.DNSConfig
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.java_websocket.handshake.ServerHandshake
import timber.log.Timber
import java.net.URI
import java.util.*
import java.util.concurrent.TimeUnit

class ChatSocketService : Service() {

    private val binder = ChatSocketBinder()
    private val intent = Intent()
    private val uri = URI.create(DNSConfig.WS_URL)

    @Volatile
    private var client: ChatSocketClient? = null

    @Volatile
    private var reconnectionInterval = 0L

    // 4 分钟
    private var heartbeatInterval = 1000 * 60 * 4L

    private var disposable: Disposable? = null

    private val screenLockReceiver = object : ScreenLockReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                Timber.w(it.action)
                when (it.action) {
                    Intent.ACTION_SCREEN_ON -> {
                        val topActivity = ActivityUtils.getTopActivity()
                        if (topActivity is APixelActivity) {
                            topActivity.finish()
                        }
                    }
                    Intent.ACTION_SCREEN_OFF -> {
                        val aPixelIntent =
                            Intent(this@ChatSocketService, APixelActivity::class.java)
                        aPixelIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(aPixelIntent)
                    }
                }
                sendMessage(PING)
            }
        }
    }

    private val networkStatusChangedReceiver =
        object : NetworkUtils.OnNetworkStatusChangedListener {
            override fun onDisconnected() {
                sendMessage(PING)
            }

            override fun onConnected(networkType: NetworkUtils.NetworkType?) {
                sendMessage(PING)
            }
        }

    private val tachycardiaReceiver = object : TachycardiaReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            sendMessage(PING)
        }
    }

    override fun onCreate() {
        super.onCreate()
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> { // 8.0 及以上
                val notification: Notification = Notification.Builder(this, DEFAULT)
                    .setContentTitle(getText(R.string.notification_title))
                    .setContentText(getText(R.string.notification_message))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setTicker(getText(R.string.ticker_text))
                    .build()
                // Notification ID cannot be 0.
                startForeground(1, notification)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> { // 7.0 及以上
                //Android7.0以上app启动后通知栏会出现一条"正在运行"的通知
                startForeground(1, Notification())
            }
            else -> { // 7.0 以下
                startForeground(1, Notification())
            }
        }
        intent.action = ACTION_CHAT_MESSAGE_RECEIVER
        screenLockReceiver.registerReceiver(this)
        NetworkUtils.registerNetworkStatusChangedListener(networkStatusChangedReceiver)
        tachycardiaReceiver.registerReceiver(this)
    }

    @SuppressLint("CheckResult")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createSocketClient()
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
                /*Observable.timer(1000 * 30L, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        sendMessage("Hello")
                    }*/
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
                //client = null
                //createSocketClient()
            }
        }
        // 连接
        connectBlocking()
    }

    private var connectBlockingCount = 0
    private var connectBlockingWhileCount = 0

    private fun connectBlocking(connect: Boolean = true) {
        if (client == null || NetworkUtils.isConnected()) {
            return
        }
        ChihApplication.easyFixedExecutor.execute {
            Timber.d("connectBlockingCount 计数 ${connectBlockingCount++}")
            try {
                reconnectionInterval = 0L
                while (client != null && NetworkUtils.isAvailable()) {
                    Timber.d("connectBlockingWhileCount 计数 ${connectBlockingWhileCount++}")
                    if (!isLoop()) {
                        break
                    }
                    // 先取消心跳包
                    disposable?.dispose()
                    val connected = if (connect) {
                        client!!.connectBlocking()
                    } else {
                        client!!.reconnectBlocking()
                    }
                    // 连接成功
                    if (connected) {
                        sendHeartbeat()
                        break
                    } else {
                        upDataReconnectionInterval()
                        Thread.sleep(reconnectionInterval)
                    }
                }
            } catch (e: InterruptedException) {
                Timber.e(e)
            }
        }
    }

    private var heartbeatCount = 0
    private var pingCount = 0

    private fun sendHeartbeat() {
        if (disposable == null || disposable!!.isDisposed) {
            Timber.d("sendHeartbeat -- heartbeatCount: ${heartbeatCount++}")
            disposable =
                Observable.interval(1000 * 3L, heartbeatInterval, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        Timber.d("sendHeartbeat -- pingCount: ${pingCount++}")
                        sendMessage(PING)
                    }
        }
    }

    @Synchronized
    private fun isLoop(): Boolean {
        if (client == null) {
            createSocketClient()
            return false
        }
        return !client!!.isOpen
    }

    @Synchronized
    private fun upDataReconnectionInterval() {
        if (reconnectionInterval == 0L) {
            reconnectionInterval = 250L
        } else if (reconnectionInterval < 1000 * 64L) {
            reconnectionInterval *= 2L
        }
    }

    private fun sendMessage(message: String) {
        if (!NetworkUtils.isConnected()) {
            Timber.w("sendMessage: 网络未连接")
            return
        }
        if (client == null) {
            createSocketClient()
        } else {
            client?.let {
                if (it.isOpen) {
                    Timber.i(message)
                    it.send(message)
                } else {
                    // 重连
                    connectBlocking(false)
                }
            }
        }
    }

    private fun closeConnect() {
        client?.close()
        client = null
        disposable?.dispose()
        disposable = null
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