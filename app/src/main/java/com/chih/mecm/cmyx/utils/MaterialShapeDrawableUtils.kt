package com.chih.mecm.cmyx.utils

import android.graphics.Paint
import androidx.annotation.ColorRes
import com.chih.mecm.cmyx.extend.value
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

object MaterialShapeDrawableUtils {

    /**
     * Corner 生成圆角的 MaterialShapeDrawable
     */
    fun getRoundedShapeDrawable(cornerSize: Float, @ColorRes color: Int): MaterialShapeDrawable {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, cornerSize)
            .build()
        return MaterialShapeDrawable(shapePathModel)
            .apply {
                setTint(color.value())
                paintStyle = Paint.Style.FILL
            }
    }

}