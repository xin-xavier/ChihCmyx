package com.chih.mecm.cmyx.http.client

import com.chih.mecm.cmyx.ChihApplication
import com.chih.mecm.cmyx.http.Api
import com.chih.mecm.cmyx.http.ApiServer
import com.chih.mecm.cmyx.http.url.DNSConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    val apiServer: ApiServer
        get() = Retrofit.Builder()
            .baseUrl(DNSConfig.API_SERVER_BASE_URL)
            .client(XavierHttpClient.create(ChihApplication.appContext))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiServer::class.java)

    val api: Api
        get() = Retrofit.Builder()
            .baseUrl(DNSConfig.API_BASE_URL)
            .client(XavierHttpClient.create(ChihApplication.appContext))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(Api::class.java)

}