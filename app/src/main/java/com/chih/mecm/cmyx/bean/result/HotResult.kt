package com.xavier.simple.demo.bean.result


import com.google.gson.annotations.SerializedName

data class RowsItem(
    @SerializedName("image")
    val image: String = "",
    @SerializedName("rebate")
    val rebate: Int = 0,
    @SerializedName("num")
    val num: Int = 0,
    @SerializedName("search_2")
    val search2: Int = 0,
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("search_1")
    val search1: Int = 0,
    @SerializedName("sales")
    val sales: Int = 0,
    @SerializedName("sales_text")
    val salesText: String = "",
    @SerializedName("search_0")
    val search0: Int = 0,
    @SerializedName("shop_id")
    val shopId: Int = 0,
    @SerializedName("price")
    val price: String = "",
    @SerializedName("shipping_method")
    val shippingMethod: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id")
    val id: Int = 0
)


data class HotResult(
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("rows")
    val rows: List<RowsItem>?
)


