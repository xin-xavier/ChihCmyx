package com.chih.mecm.cmyx.bean.result

import android.widget.ImageView
import com.google.gson.annotations.SerializedName

data class HomeAdResult(
    @SerializedName("res")
    val res: List<HomeAdResItem>?,
    @SerializedName("end_res")
    val endRes: HomeAdResItem?
)

data class HomeAdResItem(
    @SerializedName("image")
    val image: String = "",
    @SerializedName("gid")
    val gid: Int = 0,
    @SerializedName("shipping_method")
    val shippingMethod: Int = 0,
    @SerializedName("mid")
    val mid: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("position")
    val position: Int = 0,
    @SerializedName("url")
    val url: String = ""
)

data class HomeAdImageEntity(val imageView: ImageView, val homeAdResult: HomeAdResItem)