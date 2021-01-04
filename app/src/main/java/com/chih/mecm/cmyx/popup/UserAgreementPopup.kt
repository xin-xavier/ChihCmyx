package com.chih.mecm.cmyx.popup

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.Gravity
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.SPStaticUtils
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.app.api.ConstantTransmit
import com.chih.mecm.cmyx.extend.dp
import com.chih.mecm.cmyx.extend.value
import com.chih.mecm.cmyx.http.url.ConstantUrl
import com.chih.mecm.cmyx.http.url.DNSConfig
import com.chih.mecm.cmyx.widget.LollipopFixedWebView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import razerdp.basepopup.BasePopupWindow

class UserAgreementPopup(context: Context):BasePopupWindow(context) {
    override fun onCreateContentView(): View {
        return createPopupById(R.layout.popup_user_agreement)
    }

    init {
        popupGravity = Gravity.CENTER
        setOutSideDismiss(false)
        setBackPressEnable(false)

        val webView: LollipopFixedWebView = findViewById(R.id.webView)
        val disagree: TextView = findViewById(R.id.disagree)
        val agree: TextView = findViewById(R.id.agree)
        disagree.setOnClickListener {
            dismiss()
        }
        agree.setOnClickListener{
            SPStaticUtils.put(ConstantTransmit.USER_AGREEMENT, true)
            dismiss()
        }
        webView.loadUrl(
            DNSConfig.PRIVACY_POLICY_URL
        )
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
    }

}