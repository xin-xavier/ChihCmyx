package com.chih.mecm.cmyx.adapter.banner

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils
import com.chih.mecm.cmyx.bean.result.BannerItem
import com.chih.mecm.cmyx.utils.GlideEngine
import com.youth.banner.adapter.BannerAdapter

class BannerImageAdapter(slideList: List<BannerItem>?) :
    BannerAdapter<BannerItem, BannerImageAdapter.BannerViewHolder>(
        slideList
    ) {

    private var context: Context? = null

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        context = parent?.context
        val imageView = ImageView(context)
        // 注意，必须设置为 MATCH_PARENT，这个是 ViewPager2 强制要求的
        val layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(
            SizeUtils.dp2px(4.toFloat()), 0, SizeUtils.dp2px(
                4.toFloat()
            ), 0
        )
        imageView.layoutParams = layoutParams
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: BannerItem?,
        position: Int,
        size: Int
    ) {
        GlideEngine.loadBannerImage(context, data?.image, holder!!.imageView)
    }


    inner class BannerViewHolder(@NonNull view: ImageView) :
        RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view
    }

}