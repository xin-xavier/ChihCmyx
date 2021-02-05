package com.chih.mecm.cmyx.bean.result

import com.chad.library.adapter.base.entity.SectionEntity
import com.google.gson.annotations.SerializedName

data class OrderListResult(
    @SerializedName("total_price")
    val totalPrice: String = "",
    @SerializedName("active_type")
    val activeType: Int = 0,
    @SerializedName("mid")
    val mid: Int = 0,
    @SerializedName("goods")
    val goods: List<OrderGoodsItem>?,
    @SerializedName("discount")
    val discount: String = "",
    @SerializedName("remark")
    val remark: String = "",
    @SerializedName("shop_name")
    val shopName: String = "",
    @SerializedName("platform")
    val platform: Int = 0,
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("pay_order")
    val payOrder: String = "",
    @SerializedName("shipping_method")
    val shippingMethod: Int = 0,
    @SerializedName("has_goods")
    val hasGoods: Int = 0,
    @SerializedName("coupon_money")
    val couponMoney: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("order_type")
    val orderType: Int = 0,
    @SerializedName("order_sn")
    val orderSn: String = "",
    @SerializedName("has_refund")
    val hasRefund: Int = 0,
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("order_total")
    val orderTotal: Int = 0,
)

data class OrderListResultSectionEntity(
    val shop: OrderListResult,
    val goods: OrderGoodsItem?, override val isHeader: Boolean
) : SectionEntity