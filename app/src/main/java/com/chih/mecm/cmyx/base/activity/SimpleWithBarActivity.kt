package com.chih.mecm.cmyx.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentTransaction
import com.blankj.utilcode.util.BarUtils
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.fragment.helper.AppbarHelper
import com.chih.mecm.cmyx.base.presentation.OnPrepareListener
import kotlinx.android.synthetic.main.xavier_action_bar_view.*

abstract class SimpleWithBarActivity : SimpleActivity(), OnPrepareListener {

    private lateinit var inflater: LayoutInflater
    private lateinit var parentLinearLayout: LinearLayout
    private lateinit var appbarHelper: AppbarHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeContentView()
    }

    private fun beforeContentView() {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        viewGroup.removeAllViews()

        parentLinearLayout = LinearLayout(this)
        parentLinearLayout.orientation = LinearLayout.VERTICAL
        //  add parentLinearLayout in viewGroup
        viewGroup.addView(parentLinearLayout)
        //  add the layout of BaseDecorContentActivity in parentLinearLayout
        inflater = LayoutInflater.from(this)
        inflater.inflate(beforeLayoutRes(), parentLinearLayout, true)

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

    override fun setContentView(@LayoutRes layoutResID: Int) {
        //  added the sub-activity layout id in parentLinearLayout
        inflater.inflate(layoutResID, parentLinearLayout, true)
        withImmersionBar()
        ui()
    }

    override fun onPrepare(prepareView: View?) {
        xavierToolbarLayoutContent.layoutParams.height = appbarHelper.rootHeight
        xavierAppbarLayout.layoutParams.height =
            BarUtils.getStatusBarHeight() + appbarHelper.rootHeight
    }

}