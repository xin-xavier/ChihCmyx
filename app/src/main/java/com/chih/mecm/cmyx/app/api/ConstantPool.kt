package com.chih.mecm.cmyx.app.api

interface ConstantPool {
    companion object {
        // OkHttp 缓存路径
        const val OK_CACHE_DIR = "cache_dir"

        // RxCache 缓存路径
        const val RX_CACHE_DIR = "rx_cache_dir"

        // RxCache 缓存 TOP_CLAZZ 字符串缓存
        const val RX_CACHE_TOP_CLAZZ_DIR = "rx_cache_top_clazz_dir"


        const val MODEL = "model"
        const val VERSION = "version"

        const val XAVIER_ACTION_BAR_SIZE = 40f

        // tab
        const val LITTLE_SCALE = 1.0f
        const val BIG_SCALE = 1.3f

        // Glide 图片默认压缩系数
        const val IMAGE_SIZE_MULTIPLIER = 0.6f
        const val IMAGE_CORNER_RADIUS = 4f

        // broadcast
        const val ACTION_CHAT_MESSAGE_RECEIVER =
            "com.mecm.cmyx.broadcast.chat.message.receiver.action"
        const val PARAM_CHAT_MESSAGE_RECEIVER = "param_chat_message_receiver"
        const val ACTION_TACHYCARDIA_RECEIVER =
            "com.mecm.cmyx.broadcast.tachycardia.receiver.action"

        // 心跳
        const val PING = "PING"

        const val EASY_FIXED_EXECUTOR_BUILDER_SIZE = 4
        const val EASY_FIXED_EXECUTOR_BUILDER_NAME = "easy_fixed_executor_builder_name"
        const val EASY_CACHED_EXECUTOR_BUILDER_NAME = "easy_cached_executor_builder_name"

        // 当应用程序中的所有UI组件不可见是为 true
        const val APP_UI_HIDDEN = "APP_UI_HIDDEN"
    }
}