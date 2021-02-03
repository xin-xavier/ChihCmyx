package com.chih.mecm.cmyx.bean.result

import com.google.gson.annotations.SerializedName

data class OrderGoodsItem(
    @SerializedName("goods_name")
    val goodsName: String = "",
    @SerializedName("createtime")
    val createTime: Int = 0,
    @SerializedName("active_type")
    val activeType: Int = 0,
    @SerializedName("num")
    val num: Int = 0,
    @SerializedName("goods_id")
    val goodsId: Int = 0,
    @SerializedName("realmoney")
    val realMoney: String = "",
    @SerializedName("rebate_order_msg")
    val rebateOrderMsg: String? = "",
    @SerializedName("uid")
    val uid: Int = 0,
    @SerializedName("goods_image")
    val goodsImage: String = "",
    @SerializedName("single_status")
    var singleStatus: Int = 0,
    @SerializedName("price")
    val price: String = "",
    @SerializedName("comment")
    val comment: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("sku")
    val sku: String = "",
    @SerializedName("sku_condition")
    val skuCondition: String = "",
    @SerializedName("begin_after")
    val beginAfter: Int = 0
)