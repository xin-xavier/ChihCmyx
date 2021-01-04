package com.chih.mecm.cmyx.bean

import com.google.gson.annotations.SerializedName

data class BaseResult<T>(
    @SerializedName("msg")
    val msg: String = "",
    @SerializedName("result")
    var result: T?=null,
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("time")
    val time: String = ""
)
