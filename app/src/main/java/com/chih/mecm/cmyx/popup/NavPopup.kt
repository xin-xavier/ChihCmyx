package com.chih.mecm.cmyx.popup

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils
import com.google.android.material.shape.*
import razerdp.basepopup.BasePopupWindow

class NavPopup(context: Context?) : BasePopupWindow(context) {
    override fun onCreateContentView(): View {
        return createPopupById(R.layout.popup_nav)
    }

    init {
        setAlignBackground(false)
        setBackgroundColor(Color.TRANSPARENT)
        popupGravity = Gravity.BOTTOM

        val navConstraintLayout = findViewById<ConstraintLayout>(R.id.navConstraintLayout)
        navConstraintLayout.background =
            MaterialShapeDrawableUtils.getShapeDrawable(4f, R.color.white)
    }
}