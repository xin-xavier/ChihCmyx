package com.chih.mecm.cmyx.http.server

import com.chih.mecm.cmyx.bean.BaseResult
import com.chih.mecm.cmyx.bean.EmptyBean
import com.chih.mecm.cmyx.bean.result.*
import com.xavier.simple.demo.bean.result.ClazzGoodsResult
import com.xavier.simple.demo.bean.result.HotResult
import io.reactivex.Observable
import retrofit2.http.*


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
    @POST("v3/home/slideShow")
    @FormUrlEncoded
    fun slideShow(@Field("pid") pid: Int): Observable<BaseResult<List<SlideShowResult>>>

    // 首页广告
    @POST("v3/home/ad")
    fun homeAd(): Observable<BaseResult<List<HomeAdResult>>>

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
    fun hot(@Field("page") page: Int): Observable<BaseResult<HotResult>>

    // 聊天列表
    @POST("api/chat/list")
    @FormUrlEncoded
    fun chatList(@Field("page") page: Int): Observable<BaseResult<NewsChatResult>>

    @POST("v3/goods/details")
    @FormUrlEncoded
    fun details(@Field("gid") gid: Int): Observable<BaseResult<EmptyBean>>

}