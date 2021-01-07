package com.chih.mecm.cmyx.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.IMAGE_SIZE_MULTIPLIER


object GlideEngine : ImageEngine {

    ///////////////////////////////////////////////////////////////////////////
    //
    // XAVIER
    //
    ///////////////////////////////////////////////////////////////////////////

    // 加载指定占位图 默认 4dp 圆角的图片
    fun assignResIdCircleCorner(context: Context?, url: String?, imageView: ImageView) {
        context?.let {
            val options = RequestOptions()
            options.centerCrop()
            Glide.with(it)
                .asBitmap()
                //.centerCrop()
                //.sizeMultiplier(IMAGE_SIZE_MULTIPLIER)
                .placeholder(R.drawable.default_banner) //图片加载出来前显示的图片
                .fallback(R.drawable.default_banner) // url 为空的时候显示的图片
                .error(R.drawable.default_banner) // 图片加载失败后显示的图片
                .load(url)
                .apply(options)
                .into(imageView)
            /*.into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    //super.setResource(resource)
                    val circularBitmapDrawable: RoundedBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.cornerRadius = IMAGE_CORNER_RADIUS.dp()
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })*/
        } ?: setImageResource(R.drawable.default_banner, imageView)
    }

    // 加载 BannerImage
    fun loadBannerImage(context: Context?, url: String?, imageView: ImageView) {
        context?.let {
            val options = RequestOptions()
            options.centerCrop()
            Glide.with(it)
                .asBitmap()
                //.centerCrop()
                //.sizeMultiplier(IMAGE_SIZE_MULTIPLIER)
                .placeholder(R.drawable.default_banner) //图片加载出来前显示的图片
                .fallback(R.drawable.default_banner) // url 为空的时候显示的图片
                .error(R.drawable.default_banner) // 图片加载失败后显示的图片
                .load(url)
                .apply(options)
                .into(imageView)
                /*.into(object : BitmapImageViewTarget(imageView) {
                    override fun setResource(resource: Bitmap?) {
                        //super.setResource(resource)
                        val circularBitmapDrawable: RoundedBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.resources, resource)
                        circularBitmapDrawable.cornerRadius = IMAGE_CORNER_RADIUS.dp()
                        imageView.setImageDrawable(circularBitmapDrawable)
                    }
                })*/
        } ?: setImageResource(R.drawable.default_banner, imageView)
    }

    // 加载头像
    fun loadHeadPortrait(context: Context?, url: String?, imageView: ImageView) {
        context?.let {
            Glide.with(context)
                .asBitmap() // 装换为 Bitmap
                .centerCrop() // 中心作物
                .sizeMultiplier(IMAGE_SIZE_MULTIPLIER)
                .placeholder(R.drawable.default_avatar) //图片加载出来前显示的图片
                .fallback(R.drawable.default_avatar) // url 为空的时候显示的图片
                .error(R.drawable.default_avatar) // 图片加载失败后显示的图片
                .load(url)
                .into(imageView)
        } ?: setImageResource(R.drawable.default_avatar, imageView)
    }

    private fun setImageResource(@DrawableRes resId: Int, imageView: ImageView) {
        try {
            imageView.setImageResource(resId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    // XAVIER
    //
    ///////////////////////////////////////////////////////////////////////////

    override fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).asBitmap().load(url).into(imageView)
    }

    override fun loadAsBitmapImage(
        context: Context, url: String,
        imageView: ImageView, placeholderId: Int
    ) {
        Glide.with(context)
            .asBitmap()
            .override(180, 180)
            .centerCrop()
            .sizeMultiplier(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholderId)
            .load(url)
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.cornerRadius = 8f
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
    }

    override fun loadAsGifImage(
        context: Context, url: String,
        imageView: ImageView
    ) {
        Glide.with(context)
            .asGif()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .priority(Priority.HIGH)
            .load(url)
            .into(imageView)
    }

    override fun loadAsBitmapGridImage(
        context: Context,
        url: String,
        imageView: ImageView,
        placeholderId: Int
    ) {
        Glide.with(context)
            .asBitmap()
            .override(200, 200)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholderId)
            .load(url)
            .into(imageView)
    }


}