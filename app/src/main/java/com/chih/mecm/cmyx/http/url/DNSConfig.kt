package com.chih.mecm.cmyx.http.url

import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.DEBUG

object DNSConfig {

    ///////////////////////////////////////////////////////////////////////////
    // 域名
    ///////////////////////////////////////////////////////////////////////////

    val API_BASE_URL =
        if (DEBUG) ConstantUrl.DEBUG_API_BASE_URL else ConstantUrl.API_BASE_URL
    val API_SERVER_BASE_URL =
        if (DEBUG) ConstantUrl.DEBUG_API_SERVER_BASE_URL else ConstantUrl.API_SERVER_BASE_URL

    val H5_URL =
        if (DEBUG) ConstantUrl.DEBUG_H5_BASE_URL else ConstantUrl.H5_BASE_URL

    ///////////////////////////////////////////////////////////////////////////
    // URL
    ///////////////////////////////////////////////////////////////////////////

    val WS_URL = if (DEBUG) ConstantUrl.DEBUG_WS_URL else ConstantUrl.WSS_URL

    // 隐私政策政策网址
    val PRIVACY_POLICY_URL = API_SERVER_BASE_URL + ConstantUrl.PRIVACY_POLICY

}