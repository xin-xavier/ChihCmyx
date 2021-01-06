package com.chih.mecm.cmyx.utils

import android.content.Context
import android.view.View
import com.chih.mecm.cmyx.R
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState

object XavierViewUtils {

    fun setVisibility(visibility: Int, vararg views: View) {
        when (visibility) {
            View.VISIBLE -> {
                setVisible(*views)
            }
            View.INVISIBLE -> {
                setInVisible(
                    *views
                )
            }
            View.GONE -> {
                setGone(*views)
            }
        }
    }

    fun setShow(isShow: Boolean, vararg views: View) {
        if (isShow) setVisible(
            *views
        ) else setInVisible(
            *views
        )
    }

    fun setHide(isHide: Boolean, vararg views: View) {
        if (isHide) setGone(
            *views
        ) else setInVisible(
            *views
        )
    }

    fun setVisible(vararg views: View) {
        views.forEach { it.visibility = View.VISIBLE }
    }

    private fun setInVisible(vararg views: View) {
        views.forEach { it.visibility = View.INVISIBLE }
    }

    fun setGone(vararg views: View) {
        views.forEach { it.visibility = View.GONE }
    }


    fun getDivide12View(context: Context?): View =
        View.inflate(context, R.layout.divide_12_view, null)

    fun getDivide24View(context: Context?): View =
        View.inflate(context, R.layout.divide_24_view, null)


    fun finishRefreshLayoutAnim(refreshLayout : SmartRefreshLayout?){
        if(refreshLayout==null){
            return
        }
        if(refreshLayout.state == RefreshState.Refreshing){
            refreshLayout.finishRefresh()
        }
        if(refreshLayout.state == RefreshState.Loading){
            refreshLayout.finishLoadMore()
        }
    }
}