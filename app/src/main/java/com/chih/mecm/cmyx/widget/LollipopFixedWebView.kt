package com.chih.mecm.cmyx.widget

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import android.webkit.WebView

/**
 * 重写 WebView
 * 解决 androidx 5.0 系统 webview 闪退的问题
 * https://blog.csdn.net/qq_34912760/article/details/101349382
 */
class LollipopFixedWebView : WebView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(getFixedContext(context), attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        getFixedContext(context), attrs, defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        getFixedContext(context), attrs, defStyleAttr, defStyleRes
    )

    /*constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        privateBrowsing: Boolean
    ) : super(
        getFixedContext(context), attrs, defStyleAttr, privateBrowsing
    )*/

    companion object {
        private fun getFixedContext(context: Context): Context {
            return if (Build.VERSION.SDK_INT <= VERSION_CODES.LOLLIPOP_MR1) context.createConfigurationContext(
                Configuration()
            ) else context
        }
    }
}