package com.chih.mecm.cmyx.app.api

interface ConstantPool {
    companion object {
        // OkHttp 缓存路径
        const val OK_CACHE_DIR = "cacheDir"

        const val MODEL = "model"
        const val VERSION = "version"

        const val XAVIER_ACTION_BAR_SIZE = 40f

        // tab
        const val LITTLE_SCALE = 1.1f
        const val BIG_SCALE = 1.4f

        // broadcast
        const val ACTION_CHAT_MESSAGE_RECEIVER =
            "com.mecm.cmyx.broadcast.chat.message.receiver.action"
        const val PARAM_CHAT_MESSAGE_RECEIVER = "param_chat_message_receiver"

        // 心跳
        const val PING = "PING"
    }
}