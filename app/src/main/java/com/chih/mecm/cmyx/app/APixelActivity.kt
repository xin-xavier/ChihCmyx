package com.chih.mecm.cmyx.app

import android.os.Bundle
import android.view.Gravity
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.activity.LifeCycleActivity
import com.jaeger.library.StatusBarUtil

class APixelActivity : LifeCycleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_pixel)
    }
}