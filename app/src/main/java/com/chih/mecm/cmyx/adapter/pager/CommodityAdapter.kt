package com.chih.mecm.cmyx.adapter.pager

import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.SpanUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.bean.result.CommodityItem
import com.chih.mecm.cmyx.utils.GlideEngine
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils

class CommodityAdapter(data: MutableList<CommodityItem>?) :
    BaseQuickAdapter<CommodityItem, BaseViewHolder>(R.layout.recycle_item_commodity, data) {
    override fun convert(holder: BaseViewHolder, item: CommodityItem) {
        holder.getView<ConstraintLayout>(R.id.commodityConstraintLayout).background =
            MaterialShapeDrawableUtils.getShapeDrawable(4f, R.color.white)
        GlideEngine.assignResIdCircleCorner(
            context,
            item.image,
            holder.getView(R.id.commodityImage),
            R.drawable.default_commodity_no_image
        )
        holder.setText(R.id.commodityName, item.name)
            .setText(R.id.paymentsNumber, "${item.sales}人付款")
        SpanUtils
            .with(holder.getView(R.id.commodityPrice))
            .append("￥").setFontSize(10, true)
            .append(item.price).setFontSize(17, true)
            .create()
    }
}