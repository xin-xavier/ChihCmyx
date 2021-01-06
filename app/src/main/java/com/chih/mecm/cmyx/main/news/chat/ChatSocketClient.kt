package com.chih.mecm.cmyx.main.news.chat

import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import timber.log.Timber
import java.lang.Exception
import java.net.URI

open class ChatSocketClient(serverUri: URI?) : WebSocketClient(serverUri, Draft_6455()) {
    override fun onOpen(handshakedata: ServerHandshake?) {
        Timber.d("onOpen: Called")
    }

    override fun onMessage(message: String?) {
        Timber.d("onMessage: Called $message")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Timber.w("onClose: Called reason = $reason --- remote = $remote")
    }

    override fun onError(ex: Exception?) {
        Timber.e(ex, "onError: Called")
    }
}