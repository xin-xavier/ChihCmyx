package com.chih.mecm.cmyx.utils

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageEngine {
    fun loadImage(context: Context, url: String, imageView: ImageView)

    fun loadAsBitmapImage(
        context: Context, url: String,
        imageView: ImageView, @DrawableRes placeholderId: Int
    )

    fun loadAsGifImage(context: Context, url: String, imageView: ImageView)

    fun loadAsBitmapGridImage(
        context: Context, url: String,
        imageView: ImageView, @DrawableRes placeholderId: Int
    )
}