package com.chih.mecm.cmyx.bean.result


import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("comment_list")
    val commentList: List<CommentListItem>?,
    @SerializedName("count")
    val count: Int = 0
)


data class CateAttrTextItem(
    @SerializedName("v")
    val v: String = "",
    @SerializedName("n")
    val n: String = ""
)


data class RecommendedItem(
    @SerializedName("image")
    val image: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("sales")
    val sales: Int = 0,
    @SerializedName("sales_text")
    val salesText: String = ""
)

data class CommentListItem(
    @SerializedName("oid")
    val oid: Int = 0,
    @SerializedName("video")
    val video: String = "",
    @SerializedName("append_content")
    val appendContent: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("is_reply")
    val isReply: Int = 0,
    @SerializedName("uid")
    val uid: Int = 0,
    @SerializedName("score")
    val score: Int = 0,
    @SerializedName("u_nickname")
    val uNickname: String = "",
    @SerializedName("firstFrame")
    val firstFrame: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("baby_score")
    val babyScore: Int = 0,
    @SerializedName("speed_score")
    val speedScore: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("reply")
    val reply: String = "",
    @SerializedName("sku")
    val sku: String = "",
    @SerializedName("applaud")
    val applaud: Int = 0,
    @SerializedName("order")
    val order: String = "",
    @SerializedName("goods_name")
    val goodsName: String = "",
    @SerializedName("images")
    val images: String = "",
    @SerializedName("createtime")
    val createtime: Int = 0,
    @SerializedName("logistics_score")
    val logisticsScore: Int = 0,
    @SerializedName("goods_id")
    val goodsId: Int = 0,
    @SerializedName("u_avatar")
    val uAvatar: String = "",
    @SerializedName("append_images")
    val appendImages: String = "",
    @SerializedName("shop_id")
    val shopId: Int = 0,
    @SerializedName("sku_condition")
    val skuCondition: String = "",
    @SerializedName("service_score")
    val serviceScore: Int = 0,
    @SerializedName("attributes")
    val attributes: String = "",
    @SerializedName("pageview")
    val pageview: Int = 0,
    @SerializedName("status")
    val status: Int = 0
)


data class SkuDataItem(
    @SerializedName("preview")
    val preview: String = "",
    @SerializedName("purchase_num")
    val purchaseNum: Int = 0,
    @SerializedName("coding")
    val coding: String = "",
    @SerializedName("condition")
    val condition: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("rebate")
    val rebate: Int = 0,
    @SerializedName("num")
    val num: Int = 0,
    @SerializedName("sku")
    val sku: String = "",
    @SerializedName("barcode")
    val barcode: String = ""
)


data class Attr(
    @SerializedName("skuData")
    val skuData: List<SkuDataItem>?,
    @SerializedName("dataSource")
    val dataSource: List<DataSourceItem>?
)


data class CommittedItem(
    @SerializedName("ensure_name")
    val ensureName: String = "",
    @SerializedName("ensure_introduce")
    val ensureIntroduce: String = ""
)


data class GoodsDetailsResult(
    @SerializedName("shop")
    val shop: Shop,
    @SerializedName("coupon")
    val coupon: List<CouponListItem>? = null,
    @SerializedName("comment")
    val comment: Comment,
    @SerializedName("rows")
    val rows: Rows,
    @SerializedName("recommended")
    val recommended: List<RecommendedItem>?
)

data class CouponListItem(
    @SerializedName("range_name")
    val rangeName: String = "",
    @SerializedName("subset_name")
    val subsetName: String = "",
    @SerializedName("use_range")
    val useRange: Int = 0,
    @SerializedName("discount")
    var discount: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("conditions_of_use")
    var conditionsOfUse: String = "",
    @SerializedName("alias")
    val alias: String = "",
    @SerializedName("discount_after")
    val discountAfter: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("carry")
    var carry: Int = 0,
    @SerializedName("is_many_sku")
    val isManyKku: Int = 0,
    @SerializedName("op")
    var op: Int = 0,
    @SerializedName("use_end_time")
    val useEndTime: Int = 0
)


data class Shop(
    @SerializedName("headImage")
    val headImage: String = "",
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("store_name")
    val storeName: String = "",
    @SerializedName("goods")
    val goods: List<CommodityItem>?
)

data class DataSourceItem(
    @SerializedName("value")
    val value: List<String>?,
    @SerializedName("key")
    val key: String = ""
)


data class Rows(
    @SerializedName("start_selling")
    val startSelling: Int = 0,
    @SerializedName("virtual_sku")
    val virtualSku: Int = 0,
    @SerializedName("after_service")
    val afterService: Int = 0,
    @SerializedName("num")
    val num: Int = 0,
    @SerializedName("is_astrict_num")
    val isAstrictNum: Int = 0,
    @SerializedName("video")
    val video: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("sales")
    val sales: Int = 0,
    @SerializedName("platform")
    val platform: Int = 0,
    @SerializedName("xn_cateid")
    val xnCateid: Int = 0,
    @SerializedName("video_image")
    val videoImage: String = "",
    @SerializedName("goods_brand_id")
    val goodsBrandId: Int = 0,
    @SerializedName("price")
    val price: String = "",
    @SerializedName("shipping_method")
    val shippingMethod: Int = 0,
    @SerializedName("genre")
    val genre: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("goods_type_id")
    val goodsTypeId: Int = 0,
    @SerializedName("attr")
    val attr: Attr,
    @SerializedName("purchase_type")
    val purchaseType: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("delivery")
    val delivery: String = "",
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("committed")
    val committed: List<CommittedItem>?,
    @SerializedName("createtime")
    val createtime: Int = 0,
    @SerializedName("rebate")
    val rebate: Int = 0,
    @SerializedName("purchase")
    val purchase: Int = 0,
    @SerializedName("goods_id")
    val goodsId: Int = 0,
    @SerializedName("brand_name")
    val brandName: String = "",
    @SerializedName("is_astrict")
    val isAstrict: Int = 0,
    @SerializedName("templateid")
    val templateid: Int = 0,
    @SerializedName("cate_attr_text")
    val cateAttrText: List<CateAttrTextItem>?,
    @SerializedName("praise")
    val praise: Int = 0,
    @SerializedName("sales_text")
    val salesText: String = "",
    @SerializedName("canBuy")
    val canBuy: Int = 0,
    @SerializedName("shop_id")
    val shopId: Int = 0,
    @SerializedName("is_putaway")
    val isPutaway: Int = 0,
    @SerializedName("shopname")
    val shopname: String = "",
    @SerializedName("logistics_weight")
    val logisticsWeight: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("is_multi")
    val isMulti: Int = 0
)


