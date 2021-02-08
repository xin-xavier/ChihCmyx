package com.chih.mecm.cmyx.utils.extend

import com.blankj.utilcode.util.ToastUtils

fun String?.toast() {
    if (this.isNullOrEmpty()) {
        return
    }
    ToastUtils.showShort(this)
}