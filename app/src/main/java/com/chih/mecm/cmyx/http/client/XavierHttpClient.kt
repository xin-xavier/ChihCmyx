package com.chih.mecm.cmyx.http.client

import android.content.Context
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.NetworkUtils
import com.chih.mecm.cmyx.BuildConfig
import com.chih.mecm.cmyx.app.AppManager
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.MODEL
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.OK_CACHE_DIR
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.VERSION
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

class XavierHttpClient private constructor() {

    companion object HttpClientFactory {
        fun create(context: Context): OkHttpClient =
            createOkHttpClient(context.applicationContext)

        private fun createOkHttpClient(appContext: Context): OkHttpClient {
            val builder = OkHttpClient.Builder()

            val cacheControlInterceptor = Interceptor { chain ->
                var request = chain.request()
                if (!NetworkUtils.isConnected()) {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
                    Timber.i("no network")
                }

                val headerBuilder: Request.Builder = request
                    .newBuilder()
                    .header(VERSION, AppUtils.getAppVersionName())
                    .header(MODEL, DeviceUtils.getManufacturer())
                if (AppManager.isLogin()) {
                    headerBuilder.header("token", AppManager.getToken())
                }
                headerBuilder.header("xdebug-a1-b2-c3-d4","3")

                request = headerBuilder.method(
                    request.method, request.body
                ).build()

                val originalResponse = chain.proceed(request)
                val maxAge = 60 * 60
                if (NetworkUtils.isConnected()) {
                    originalResponse
                        .newBuilder()
                        .header("Cache-Control", "public ,max-age=$maxAge")
                        .removeHeader("Pragma")
                        .build()
                } else {
                    originalResponse
                        .newBuilder()
                        .header("Cache-Control", "public, only-if-cached,${maxAge * 24 * 2}")
                        .removeHeader("Pragma")
                        .build()
                }
            }

            if (BuildConfig.DEBUG) { // 判断是否为debug
                // 如果为 debug 模式，则添加日志拦截器
                val loggingInterceptor =
                    HttpLoggingInterceptor { message ->
                        //日志过滤器
                        Timber.v(message)
                    }
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                builder.addInterceptor(loggingInterceptor)
            }

            // 因为 BaseUrl 不同所以这里 Retrofit 不为静态，但是 OkHttpClient 配置是一样的,静态创建一次即可
            val cacheFile = File(appContext.cacheDir, OK_CACHE_DIR) // 指定缓存路径
            val cache = Cache(cacheFile, 1024 * 1024 * 100) // 指定缓存大小100Mb
            return builder.cache(cache)
                .addNetworkInterceptor(cacheControlInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS) //设置连接超时
                .readTimeout(20, TimeUnit.SECONDS) //设置读取超时
                .writeTimeout(20, TimeUnit.SECONDS) // 设置写入超时
                // 默认重试5次取消http请求
                .retryOnConnectionFailure(true) // 在网络请求失败时重试
                .build()
        }

    }

}