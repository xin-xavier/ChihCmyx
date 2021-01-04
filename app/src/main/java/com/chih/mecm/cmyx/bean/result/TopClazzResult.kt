package com.chih.mecm.cmyx.bean.result

import com.google.gson.annotations.SerializedName

data class TopClazzResult(@SerializedName("name")
                    val name: String = "",
                          @SerializedName("id")
                    val id: Int = 0)