package com.chih.mecm.cmyx.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SizeUtils
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.XAVIER_ACTION_BAR_SIZE
import com.chih.mecm.cmyx.base.fragment.helper.AppbarHelper
import com.chih.mecm.cmyx.base.presentation.OnPrepareListener
import kotlinx.android.synthetic.main.xavier_action_bar_view.*

abstract class SimpleDecorViewActivity : SimpleActivity(), OnPrepareListener {

    private lateinit var inflater: LayoutInflater
    private lateinit var parentLinearLayout: LinearLayout
    private lateinit var appbarHelper: AppbarHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeContentView()
    }

    private fun beforeContentView() {
        // 清空布局
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        viewGroup.removeAllViews()

        // 添加布局
        parentLinearLayout = LinearLayout(this)
        parentLinearLayout.orientation = LinearLayout.VERTICAL
        viewGroup.addView(parentLinearLayout)
        inflater = LayoutInflater.from(this)
        inflater.inflate(beforeLayoutRes(), parentLinearLayout, true)

        // 计算 Appbar Toolbar StatusBar 的高度
        val toolbarLayoutHeight = SizeUtils.dp2px(XAVIER_ACTION_BAR_SIZE)
        xavierToolbarLayoutContent.layoutParams.height = toolbarLayoutHeight
        val statusBarHeight = BarUtils.getStatusBarHeight()
        xavierAppbarLayout.layoutParams.height = statusBarHeight + toolbarLayoutHeight

        setToolbar()
    }

    open fun setToolbar() {
        val beginTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        appbarHelper =
            AppbarHelper(
                toolbarLayoutRes(),
                this
            )
        beginTransaction.replace(R.id.xavierToolbarLayoutContent, appbarHelper)
        beginTransaction.commit()
    }

    open fun beforeLayoutRes(): Int = R.layout.xavier_decor_view

    open fun toolbarLayoutRes() = R.layout.xavier_toolbar_default_view

    // 判断使用的是否是默认的 toolbarRes
    open fun isDefaultToolbar() = toolbarLayoutRes() == R.layout.xavier_toolbar_default_view

    // 当页面使用的是否是默认的 toolbarRes 时，设置 title
    open fun setDefaultTitle(title: String) {
        if (isDefaultToolbar()) {
            appbarHelper.rootView?.let {
                val toolbarTitle = it.findViewById<TextView>(R.id.xavierToolbarTitle)
                toolbarTitle.text = title
            }
        }
    }
}