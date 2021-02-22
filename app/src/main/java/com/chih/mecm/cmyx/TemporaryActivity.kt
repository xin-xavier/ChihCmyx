package com.chih.mecm.cmyx

import android.os.Bundle
import android.view.View
import com.chih.mecm.cmyx.base.activity.SimpleDecorViewActivity

class TemporaryActivity : SimpleDecorViewActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temporary)

    }

    override fun ui() {

    }

    override fun onPrepare(prepareView: View?) {
    }


}