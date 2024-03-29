package com.chih.mecm.cmyx.utils.extend

import android.content.res.Resources
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.blankj.utilcode.util.ColorUtils

///////////////////////////////////////////////////////////////////////////
// Size 相关
///////////////////////////////////////////////////////////////////////////

// Value of dp to value of px.
fun Float.dp(): Float {
    val scale = Resources.getSystem().displayMetrics.density
    return this * scale + 0.5f
}

fun Int.dp(): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

// Value of px to value of sp.
fun Float.sp(): Float {
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return this * fontScale + 0.5f
}

// Value of px to value of dp.
fun Float.toDp(): Float {
    val scale = Resources.getSystem().displayMetrics.density
    return this / scale + 0.5f
}

// Value of px to value of sp.
fun Float.toSp(): Float {
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return this / fontScale + 0.5f
}

///////////////////////////////////////////////////////////////////////////
// Color 相关
///////////////////////////////////////////////////////////////////////////

@ColorRes
@ColorInt
fun Int.value(): Int {
    return ColorUtils.getColor(this)
}