package com.chih.mecm.cmyx.http

import com.chih.mecm.cmyx.bean.BaseResult
import com.chih.mecm.cmyx.bean.EmptyBean
import com.chih.mecm.cmyx.bean.result.*
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiServer {

    // 检查版本更新
    @POST("v3/home/release")
    @FormUrlEncoded
    fun checkVersionUpdates(@FieldMap map: Map<String, String>): Observable<BaseResult<EmptyBean>>

    // 头部分类
    @POST("v3/home/topClass")
    fun topClazz(): Observable<BaseResult<List<TopClazzResult>>>

    // 首页默认搜索
    @POST("v3/home/getDefaultSearch")
    fun getDefaultSearch(): Observable<BaseResult<String>>

    //  首页轮播图
    @POST("v5/home/slideShow")
    @FormUrlEncoded
    fun slideShow(@Field("pid") pid: Int): Observable<BaseResult<SlideShowResult>>

    // 首页广告
    @POST("v5/home/ad")
    fun homeAd(): Observable<BaseResult<HomeAdResult>>

    // 二级分类
    @POST("v3/home/subClass")
    @FormUrlEncoded
    fun subClazz(@Field("pid") pid: Int): Observable<BaseResult<List<SubClazzResult>>>

    // 精选店铺
    @POST("v3/home/choiceShop")
    fun homeChoiceShop(): Observable<BaseResult<List<List<HomeChoiceShopListItem>>>>

    // 分类商品
    @POST("v3/home/classGoods")
    @FormUrlEncoded
    fun clazzGoods(@FieldMap map: Map<String, Int>): Observable<BaseResult<ClazzGoodsResult>>

    // 热门推荐
    @POST("v3/home/hot")
    @FormUrlEncoded
    fun hot(@Field("page") page: Int): Observable<BaseResult<CommodityResult>>

    // 热门推荐
    @POST("v3/home/classGoods")
    @FormUrlEncoded
    fun subGoods(@FieldMap map: Map<String, Int>): Observable<BaseResult<CommodityResult>>

    // 聊天列表
    @POST("api/chat/list")
    @FormUrlEncoded
    fun chatList(@Field("page") page: Int): Observable<BaseResult<NewsChatResult>>

    @POST("v3/goods/details")
    @FormUrlEncoded
    fun details(@Field("gid") gid: Int): Observable<BaseResult<EmptyBean>>

    // 订单列表
    @POST("api/v4/order/orderInfo")
    @FormUrlEncoded
    fun orderList(@FieldMap map: Map<String, Int>): Observable<BaseResult<List<OrderListResult>>>

    // 为你推荐
    @POST("v3/home/recommend")
    @FormUrlEncoded
    fun recommendGoods(@Field("page") page: Int): Observable<BaseResult<CommodityResult>>

    // 注册
    @FormUrlEncoded
    @POST("v3/login/register")
    fun register(@FieldMap map: Map<String, Any>): Observable<BaseResult<LoginResult>>

    // 发送验证码
    @FormUrlEncoded
    @POST("v3/login/sendSms")
    fun sendSms(@FieldMap map: Map<String, Any>): Observable<BaseResult<EmptyBean>>

    // 手机号登录
    @POST("v3/login/phoneLogin")
    @FormUrlEncoded
    fun phoneLogin(@FieldMap map: Map<String, Any>): Observable<BaseResult<LoginResult>>

    // 验证码登录
    @POST("v3/login/phoneLogin")
    @FormUrlEncoded
    fun phoneVerificationCodeLogin(@FieldMap map: Map<String, Any>): Observable<BaseResult<LoginResult>>

    // 第三方登陆
    @POST("v3/login/authLogin")
    @FormUrlEncoded
    fun authLogin(@FieldMap map: Map<String, Any>): Observable<BaseResult<LoginResult>>

    // 第三方登陆
    @POST("v3/login/bind")
    @FormUrlEncoded
    fun authLoginBindPhone(@FieldMap map: Map<String, Any>): Observable<BaseResult<LoginResult>>


}