package com.chih.mecm.cmyx.http.url

interface ConstantUrl {
    companion object {
        const val DEBUG_API_BASE_URL = "https://api.chengmeiyoupin.com/"
        const val API_BASE_URL = "https://api.chengmeiyouxuan.com/"

        const val DEBUG_API_SERVER_BASE_URL = "https://api.server.chengmeiyoupin.com/"
        const val API_SERVER_BASE_URL = "https://api.server.chengmeiyouxuan.com/"

        const val DEBUG_H5_BASE_URL = "https://h5.chengmeiyoupin.com/"
        const val H5_BASE_URL = "https://h5.chengmeiyouxuan.com/"

        const val DEBUG_WS_URL = "ws://connector.chengmeiyoupin.com:9503"
        const val WSS_URL = "wss://chat.chengmeiyouxuan.com:"

        // 隐私政策
        var PRIVACY_POLICY = "v3/various/privacy"
    }
}