package com.chih.mecm.cmyx.http.observer

import java.io.IOException

// 用来封装业务错误信息
class BusinessHttpException (val businessMessage: String, val businessCode: Int) :
    IOException()