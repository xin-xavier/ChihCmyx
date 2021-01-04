package com.chih.mecm.cmyx.utils

import android.view.View

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


}