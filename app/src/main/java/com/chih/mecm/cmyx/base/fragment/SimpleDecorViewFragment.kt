package com.chih.mecm.cmyx.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.BarUtils
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.app.api.ConstantPool
import com.chih.mecm.cmyx.base.fragment.helper.AppbarHelper
import com.chih.mecm.cmyx.base.presentation.OnPrepareListener
import com.chih.mecm.cmyx.utils.extend.dp

abstract class SimpleDecorViewFragment : SimpleFragment, OnPrepareListener {

    lateinit var inflater: LayoutInflater
    lateinit var toolbarHelper: AppbarHelper

    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.inflater = inflater
        rootView = beforeCreateView(container)
        return rootView
    }

    override fun immersionBarEnabled() = true

    open fun layoutRes() = R.layout.xavier_decor_view

    private fun beforeCreateView(container: ViewGroup?): View {
        val view = inflater.inflate(layoutRes(), container, false)
        val toolbarLayoutHeight = ConstantPool.XAVIER_ACTION_BAR_SIZE.dp().toInt()
        view.findViewById<FrameLayout>(R.id.xavierToolbarLayoutContent).layoutParams.height =
            toolbarLayoutHeight
        val statusBarHeight = BarUtils.getStatusBarHeight()
        view.findViewById<FrameLayout>(R.id.xavierAppbarLayout).layoutParams.height =
            statusBarHeight + toolbarLayoutHeight
        setToolbar()
        return setContentView(view, container)
    }

    open fun toolbarLayoutRes() = R.layout.xavier_toolbar_default_view

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

    abstract fun contentLayoutRes(): Int

    private fun setContentView(
        view: View,
        container: ViewGroup?
    ): View {
        val decorView: LinearLayout = view.findViewById<LinearLayout>(R.id.xavierDecorView)
        decorView.addView(inflater.inflate(contentLayoutRes(), container, false))
        return view
    }


    // 判断使用的是否是默认的 toolbarRes
    open fun isDefaultToolbar() = toolbarLayoutRes() == R.layout.xavier_toolbar_default_view

    // 当页面使用的是否是默认的 toolbarRes 时，方便设置 title
    open fun setDefaultTitle(title: String) {
        if (isDefaultToolbar()) {
            toolbarHelper.rootView?.let {
                val toolbarTitle = it.findViewById<TextView>(R.id.xavierToolbarTitle)
                toolbarTitle.text = title
            }
        }
    }

}