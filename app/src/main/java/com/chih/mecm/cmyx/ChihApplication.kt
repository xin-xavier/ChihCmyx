package com.chih.mecm.cmyx

import android.app.Application
import android.content.Context
import android.os.Build
import com.blankj.utilcode.util.SPStaticUtils
import com.chih.mecm.cmyx.app.api.ConstantTransmit.Companion.PUSH_STATUS
import com.chih.mecm.cmyx.app.notification.NotificationChannels
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class ChihApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext

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
    }

    companion object {
        lateinit var instance: ChihApplication
            private set
        lateinit var appContext: Context
            private set
    }

}