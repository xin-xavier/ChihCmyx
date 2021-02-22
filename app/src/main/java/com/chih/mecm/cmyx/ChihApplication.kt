package com.chih.mecm.cmyx

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import com.blankj.utilcode.util.SPStaticUtils
import com.chih.mecm.cmyx.app.AppManager
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.EASY_CACHED_EXECUTOR_BUILDER_NAME
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.EASY_FIXED_EXECUTOR_BUILDER_NAME
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.EASY_FIXED_EXECUTOR_BUILDER_SIZE
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.RX_CACHE_DIR
import com.chih.mecm.cmyx.app.api.ConstantTransmit.Companion.PUSH_STATUS
import com.chih.mecm.cmyx.app.notification.NotificationChannels
import com.chih.mecm.cmyx.utils.easy.EasyExecutor
import com.lei.lib.java.rxcache.RxCache
import com.lei.lib.java.rxcache.converter.GsonConverter
import com.lei.lib.java.rxcache.mode.CacheMode
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import timber.log.Timber

class ChihApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext

        Timber.plant(Timber.DebugTree())

        // 创建一个可重用固定个数的线程池
        easyFixedExecutor = EasyExecutor.newBuilder(EASY_FIXED_EXECUTOR_BUILDER_SIZE)
            .setName(EASY_FIXED_EXECUTOR_BUILDER_NAME)
            .setPriority(Thread.NORM_PRIORITY)
            .build()
        // 创建一个可缓存线程池
        easyCachedExecutor = EasyExecutor.newBuilder(0)
            .setName(EASY_CACHED_EXECUTOR_BUILDER_NAME)
            .setPriority(Thread.NORM_PRIORITY)
            .build()

        RxCache.init(this)
        RxCache.Builder()
            .setDebug(BuildConfig.DEBUG)   //开启debug，开启后会打印缓存相关日志，默认为true
            .setConverter(GsonConverter<Any>())  //设置转换方式，默认为Gson转换
            .setCacheMode(CacheMode.BOTH)   //设置缓存模式，默认为二级缓存
            .setMemoryCacheSizeByMB(50)   //设置内存缓存的大小，单位是MB
            .setDiskCacheSizeByMB(100)    //设置磁盘缓存的大小，单位是MB
            .setDiskDirName(RX_CACHE_DIR)    //设置磁盘缓存的文件夹名称
            .build()


        if (!SPStaticUtils.contains(PUSH_STATUS)) {
            SPStaticUtils.put(PUSH_STATUS, true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannels.createAllNotificationChannels(instance)
        }

        // 设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
            layout.setPrimaryColorsId(R.color.initial, R.color.black) //全局设置主题颜色
        }
        // 设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            ClassicsHeader(context)
        }
        // 设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context)
        }

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Timber.d("onActivityCreated: Called")
            }

            override fun onActivityStarted(activity: Activity) {
                Timber.d("onActivityStarted: Called")
            }

            override fun onActivityResumed(activity: Activity) {
                Timber.d("onActivityResumed: Called")
            }

            override fun onActivityPaused(activity: Activity) {
                Timber.d("onActivityPaused: Called")
            }

            override fun onActivityStopped(activity: Activity) {
                Timber.d("onActivityStopped: Called")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Timber.d("onActivitySaveInstanceState: Called")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Timber.d("onActivityDestroyed: Called")
            }

        })

        registerComponentCallbacks(object : ComponentCallbacks2 {
            override fun onConfigurationChanged(newConfig: Configuration) {
                // 作用：监听 应用程序 配置信息的改变，如屏幕旋转等
                Timber.i("onConfigurationChanged: Called")
            }

            override fun onLowMemory() {
            }

            override fun onTrimMemory(level: Int) {
                // OnTrimMemory() 是 OnLowMemory() Android 4.0后的替代 API
                // 4.0 以后直接使用 OnTrimMemory() 即可
                // 作用：通知 应用程序 当前内存使用情况（以内存级别进行识别）
                if (level == TRIM_MEMORY_UI_HIDDEN) {
                    Timber.i( "onTrimMemory: 应用程序中的所有UI组件不可见了")
                    AppManager.setForeground(false)
                }
                if (level >= TRIM_MEMORY_MODERATE) {
                    Timber.i("onTrimMemory: 应该释放内存了")
                }
                Timber.i("onTrimMemory: 内存级别 \$level = $level")
            }

        })

    }

    companion object {
        lateinit var instance: ChihApplication
            private set
        lateinit var appContext: Context
            private set
        lateinit var easyFixedExecutor: EasyExecutor
            private set
        lateinit var easyCachedExecutor: EasyExecutor
            private set
    }

}