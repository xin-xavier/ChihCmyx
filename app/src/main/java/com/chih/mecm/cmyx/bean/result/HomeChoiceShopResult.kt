package com.chih.mecm.cmyx.bean.result

import com.google.gson.annotations.SerializedName

data class HomeChoiceShopListItem(@SerializedName("engage")
                                  val engage: Int = 0,
                                  @SerializedName("headImage")
                                  val headImage: String = "",
                                  @SerializedName("store_name")
                                  val storeName: String = "",
                                  @SerializedName("id")
                                  val id: Int = 0)