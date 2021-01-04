package com.chih.mecm.cmyx.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.blankj.utilcode.util.BarUtils
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.fragment.helper.AppbarHelper
import com.chih.mecm.cmyx.base.presentation.OnPrepareListener

abstract class SimpleWithBarFragment(@LayoutRes override var contentLayoutId: Int = 0) :
    SimpleFragment(contentLayoutId), OnPrepareListener {

    lateinit var inflater: LayoutInflater
    lateinit var toolbarHelper: AppbarHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.inflater = inflater
        rootView = beforeCreateView(container)
        return rootView
    }

    override fun immersionBarEnabled(): Boolean {
        return true
    }

    override fun onPrepare(prepareView: View?) {
        rootView?.let {
            it.findViewById<FrameLayout>(R.id.xavierToolbarLayoutContent).layoutParams.height =
                toolbarHelper.rootHeight
            val appbarHeight: Int = BarUtils.getStatusBarHeight() + toolbarHelper.rootHeight
            it.findViewById<FrameLayout>(R.id.xavierAppbarLayout).layoutParams.height = appbarHeight
        }
    }

    private fun beforeCreateView(container: ViewGroup?): View {
        val view = inflater.inflate(layoutRes(), container, false)
        setToolbar()
        return setContentView(view, contentLayoutRes(), container)
    }

    open fun layoutRes(): Int = R.layout.xavier_decor_view

    open fun setToolbar() {
        val beginTransaction = childFragmentManager.beginTransaction()
        toolbarHelper =
            AppbarHelper(
                toolbarLayoutRes(),
                this
            )
        beginTransaction.replace(R.id.xavierToolbarLayoutContent, toolbarHelper)
        beginTransaction.commit()
    }

    open fun toolbarLayoutRes() = R.layout.xavier_default_toobar_view

    private fun setContentView(
        view: View,
        @LayoutRes contentLayoutResID: Int,
        container: ViewGroup?
    ): View {
        //  added the sub-activity layout id in parentLinearLayout
        val decorView: LinearLayout = view.findViewById<LinearLayout>(R.id.xavierDecorView)
        decorView.addView(inflater.inflate(contentLayoutResID, container, false))
        return view
    }

    abstract fun contentLayoutRes(): Int

}