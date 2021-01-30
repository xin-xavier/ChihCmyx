package com.chih.mecm.cmyx.bean.result

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("commission_customers")
    val commissionCustomers: Int = 0,
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("avatar")
    val avatar: String = ""
) : Parcelable