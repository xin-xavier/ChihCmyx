package com.chih.mecm.cmyx.bean.result

import com.google.gson.annotations.SerializedName

data class SlideShowResult(@SerializedName("image")
                               val image: String = "",
                           @SerializedName("shipping_method")
                               val shippingMethod: Int = 0,
                           @SerializedName("name")
                               val name: String = "",
                           @SerializedName("goods_id")
                               val goodsId: Int = 0,
                           @SerializedName("mid")
                               val mid: Int = 0,
                           @SerializedName("id")
                               val id: Int = 0,
                           @SerializedName("url")
                               val url: String = "",
                           @SerializedName("position_id")
                               val positionId: Int = 0)