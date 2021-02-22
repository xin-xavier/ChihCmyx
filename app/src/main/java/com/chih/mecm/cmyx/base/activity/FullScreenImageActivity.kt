package com.chih.mecm.cmyx.base.activity

import com.jaeger.library.StatusBarUtil

/**
 * 已全屏图片为背景的 Activity
 */
abstract class FullScreenImageActivity : SimpleActivity() {

    override fun withImmersionBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null)
    }

}