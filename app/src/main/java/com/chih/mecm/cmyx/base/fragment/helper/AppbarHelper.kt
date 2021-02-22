package com.chih.mecm.cmyx.base.fragment.helper

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.fragment.SimpleFragment
import com.chih.mecm.cmyx.base.presentation.OnPrepareListener
import com.chih.mecm.cmyx.popup.NavPopup

class AppbarHelper : SimpleFragment {

    private var onPrepareListener: OnPrepareListener? = null
    var rootHeight: Int = 0

    // 必须有一个默认的构造函数
    // 因为 Fragment 通过反则重建的
    // 如果没有这个函数，则重建时会找不到构造方法
    constructor() : super()
    constructor(
        contentLayoutId: Int,
        onPrepareListener: OnPrepareListener
    ) : super(contentLayoutId) {
        this.onPrepareListener = onPrepareListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView?.let {
            rootHeight = it.layoutParams.height
        }
        if (contentLayoutId != 0) {
            when (contentLayoutId) {
                R.layout.xavier_toolbar_default_view -> {
                    val returnPager = view.findViewById<ImageView>(R.id.xavierReturnPager)
                    val navMenu = view.findViewById<ImageView>(R.id.xavierNavMenu)
                    returnPager.setOnClickListener { activity?.finish() }
                    navMenu.setOnClickListener { v ->
                        val navPopup = NavPopup(context)
                        navPopup.showPopupWindow(v)
                    }
                }
                R.layout.xavier_toolbar_reture_search_menu_tabs -> {
                    val returnPager = view.findViewById<ImageView>(R.id.xavierReturnPager)
                    val navMenu = view.findViewById<ImageView>(R.id.xavierNavMenu)
                    returnPager.setOnClickListener { activity?.finish() }
                    navMenu.setOnClickListener { v ->
                        val navPopup = NavPopup(context)
                        navPopup.showPopupWindow(v)
                    }
                }
                else ->{

                }
            }
        }
        /**
         * 在默认的判断和监听后面回调,如果相应的 activity 设置监听
         * 将以 activity 里面设置的监听为准
         */
        onPrepareListener?.onPrepare(view)
    }

    override fun ui() {}

}