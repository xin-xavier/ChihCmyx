package com.chih.mecm.cmyx.bean.result

import com.google.gson.annotations.SerializedName

data class SubClazzResult(@SerializedName("image")
                          val image: String = "",
                          @SerializedName("name")
                          val name: String = "",
                          @SerializedName("id")
                          val id: Int = 0)