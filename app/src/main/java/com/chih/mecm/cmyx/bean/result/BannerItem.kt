package com.chih.mecm.cmyx.bean.result

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BannerItem(
    @SerializedName("image")
    val image: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("goods_id")
    val goodsId: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("position_id")
    val positionId: Int = 0,
    @SerializedName("user")
    var user: User?
): Parcelable