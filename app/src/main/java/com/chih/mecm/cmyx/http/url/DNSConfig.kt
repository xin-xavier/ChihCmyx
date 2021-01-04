package com.chih.mecm.cmyx.http.url

object DNSConfig {

    ///////////////////////////////////////////////////////////////////////////
    // 域名
    ///////////////////////////////////////////////////////////////////////////

    val API_BASE_URL =
        if (ConstantUrl.DEBUG) ConstantUrl.DEBUG_API_BASE_URL else ConstantUrl.API_BASE_URL
    val API_SERVER_BASE_URL =
        if (ConstantUrl.DEBUG) ConstantUrl.DEBUG_API_SERVER_BASE_URL else ConstantUrl.API_SERVER_BASE_URL

    val H5_URL =
        if (ConstantUrl.DEBUG) ConstantUrl.DEBUG_H5_BASE_URL else ConstantUrl.H5_BASE_URL

    ///////////////////////////////////////////////////////////////////////////
    // URL
    ///////////////////////////////////////////////////////////////////////////

    val WS_URL = ConstantUrl.WSS_URL

    // 隐私政策政策网址
    val PRIVACY_POLICY_URL = API_SERVER_BASE_URL + ConstantUrl.PRIVACY_POLICY

}