package com.chih.mecm.cmyx.http.server

import com.chih.mecm.cmyx.bean.BaseResult
import com.chih.mecm.cmyx.bean.EmptyBean
import com.chih.mecm.cmyx.bean.result.NewsChatResult
import com.chih.mecm.cmyx.bean.result.TopClazzResult
import io.reactivex.Observable
import okhttp3.RequestBody
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
    fun slideShow(@Field("pid") pid: Int): Observable<BaseResult<List<EmptyBean>>>

    // 聊天列表
    @POST("api/chat/list")
    @FormUrlEncoded
    fun chatList(@Field("page") page: Int): Observable<BaseResult<NewsChatResult>>

    @POST("v3/goods/details")
    @FormUrlEncoded
    fun details(@Field("gid") gid: Int): Observable<BaseResult<EmptyBean>>

}