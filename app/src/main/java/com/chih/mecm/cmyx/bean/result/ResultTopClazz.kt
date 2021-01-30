package com.chih.mecm.cmyx.bean.result

import com.chih.mecm.cmyx.bean.result.TopClazzResult
import com.google.gson.annotations.SerializedName

data class ResultTopClazz(@SerializedName("msg")
                          val msg: String = "",
                          @SerializedName("result")
                          val result: List<TopClazzResult>?,
                          @SerializedName("code")
                          val code: Int = 0,
                          @SerializedName("time")
                          val time: String = "")