package com.chih.mecm.cmyx.bean.result

import com.google.gson.annotations.SerializedName

data class SlideShowResult(@SerializedName("banner")
                           val banner: MutableList<BannerItem>?,
                           @SerializedName("user")
                           val user: User?)