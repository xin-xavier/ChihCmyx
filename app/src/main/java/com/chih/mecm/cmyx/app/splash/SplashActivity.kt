package com.chih.mecm.cmyx.app.splash

import android.os.Bundle
import com.blankj.utilcode.util.SPStaticUtils
import com.chih.mecm.cmyx.MainActivity
import com.chih.mecm.cmyx.TemporaryActivity
import com.chih.mecm.cmyx.app.api.ConstantTransmit.Companion.SPLASH
import com.chih.mecm.cmyx.base.activity.SimpleActivity

class SplashActivity : SimpleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash)
        if (SPStaticUtils.getBoolean(SPLASH)) {
            intent(MainActivity::class.java)
            //intent(TemporaryActivity::class.java)
        } else {
            SPStaticUtils.put(SPLASH, true)
            intent(GuideActivity::class.java)
        }
        // 去掉 Android Activity 自带的原生进入进出动画
        // https://blog.csdn.net/wolfking0608/article/details/78967608
        overridePendingTransition(0, 0)
        finish()
    }

    override fun ui() {}
}