package com.chih.mecm.cmyx.base.fragment.helper

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.blankj.utilcode.util.ToastUtils
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.fragment.SimpleFragment
import com.chih.mecm.cmyx.base.presentation.OnPrepareListener

class AppbarHelper(@LayoutRes override var contentLayoutId: Int = 0, onPrepare: OnPrepareListener) :
    SimpleFragment(contentLayoutId) {

    private val onPrepareListener: OnPrepareListener = onPrepare
    var rootHeight: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView?.let {
            rootHeight = it.layoutParams.height
        }
        if (contentLayoutId != 0) {
            when (contentLayoutId) {
                R.layout.xavier_default_toobar_view -> {
                    val returnPager = view.findViewById<ImageView>(R.id.xavierReturnPager)
                    val navMenu = view.findViewById<ImageView>(R.id.xavierNavMenu)
                    returnPager.setOnClickListener { activity?.finish() }
                    navMenu.setOnClickListener { ToastUtils.showShort("Chih诚美优选") }
                }
            }
        }
        /**
         * 在默认的判断和监听后面回调,如果相应的 activity 设置监听
         * 将以 activity 里面设置的监听为准
         */
        onPrepareListener.onPrepare(view)
    }

    override fun ui() {}

}