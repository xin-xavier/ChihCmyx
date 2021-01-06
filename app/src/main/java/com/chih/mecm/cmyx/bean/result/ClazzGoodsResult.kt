package com.xavier.simple.demo.bean.result

import com.google.gson.annotations.SerializedName

data class ClazzGoodsResult(@SerializedName("image")
                            val image: String = "",
                            @SerializedName("shop_id")
                            val shopId: Int = 0,
                            @SerializedName("price")
                            val price: String = "",
                            @SerializedName("shipping_method")
                            val shippingMethod: Int = 0,
                            @SerializedName("num")
                            val num: Int = 0,
                            @SerializedName("name")
                            val name: String = "",
                            @SerializedName("id")
                            val id: Int = 0,
                            @SerializedName("type")
                            val type: Int = 0,
                            @SerializedName("sales")
                            val sales: Int = 0,
                            @SerializedName("sales_text")
                            val salesText: String = "")