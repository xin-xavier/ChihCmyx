package com.chih.mecm.cmyx.utils

import android.graphics.Paint
import androidx.annotation.ColorRes
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.extend.dp
import com.chih.mecm.cmyx.extend.value
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

/**
 * https://mp.weixin.qq.com/s/XGPFPFEr0Rn3GkwqoMaTqA
 */
object MaterialShapeDrawableUtils {

    /**
     * Corner 生成圆角的 MaterialShapeDrawable
     */
    fun getShapeDrawable(cornerSize: Float, @ColorRes color: Int): MaterialShapeDrawable {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, cornerSize.dp())
            .build()
        return MaterialShapeDrawable(shapePathModel)
            .apply {
                paintStyle = Paint.Style.FILL
                setTint(color.value())
            }
    }

    fun topShapeDrawable(cornerSize: Float, @ColorRes color: Int): MaterialShapeDrawable {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(CornerFamily.ROUNDED, cornerSize.dp())
            .setTopRightCorner(CornerFamily.ROUNDED, cornerSize.dp())
            .build()
        return MaterialShapeDrawable(shapePathModel)
            .apply {
                paintStyle = Paint.Style.FILL
                setTint(color.value())
            }
    }

    fun bottomShapeDrawable(cornerSize: Float, @ColorRes color: Int): MaterialShapeDrawable {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setBottomLeftCorner(CornerFamily.ROUNDED, cornerSize.dp())
            .setBottomRightCorner(CornerFamily.ROUNDED, cornerSize.dp())
            .build()
        return MaterialShapeDrawable(shapePathModel)
            .apply {
                paintStyle = Paint.Style.FILL
                setTint(color.value())
            }
    }


    fun strokeShapeDrawable(
        cornerSize: Float,
        @ColorRes strokeColor: Int,
        strokeWidth: Float = 1f,
        @ColorRes color: Int = R.color.transparent
    ): MaterialShapeDrawable {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, cornerSize.dp())
            .build()
        return MaterialShapeDrawable(shapePathModel).apply {
            paintStyle = Paint.Style.STROKE
            setStroke(strokeWidth, strokeColor.value())
            setTint(color.value())
        }
    }

}