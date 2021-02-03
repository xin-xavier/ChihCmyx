package com.chih.mecm.cmyx.order.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.SpanUtils
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.fragment.BaseFragment
import com.chih.mecm.cmyx.bean.entity.TabEntity
import com.chih.mecm.cmyx.bean.result.CommodityResult
import com.chih.mecm.cmyx.bean.result.OrderListResult
import com.chih.mecm.cmyx.bean.result.OrderListResultSectionEntity
import com.chih.mecm.cmyx.extend.toast
import com.chih.mecm.cmyx.extend.value
import com.chih.mecm.cmyx.utils.GlideEngine
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils
import com.chih.mecm.cmyx.utils.XavierOrderUtils
import com.chih.mecm.cmyx.utils.XavierViewUtils
import kotlinx.android.synthetic.main.fragment_multip_order_list.*
import kotlinx.android.synthetic.main.fragment_order_list.*

private const val ARG_PARAM = "param"

class OrderListFragment : BaseFragment<OrderListContract.Presenter<OrderListContract.View>>(),
    OrderListContract.View {

    private lateinit var param: TabEntity

    private var limit = 8
    private var orderPage = 1

    private var rowsPage = 1

    val map = mutableMapOf<String, Int>("limit" to limit, "page" to orderPage)

    private val sectionList = mutableListOf<OrderListResultSectionEntity>()
    private var orderAdapter: OrderListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getParcelable<TabEntity>(ARG_PARAM) ?: TabEntity()
            map["op"] = param.type
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false)
        //return inflater.inflate(R.layout.fragment_multip_order_list, container, false)
    }

    override fun createPresenter(): OrderListContract.Presenter<OrderListContract.View> {
        return OrderListPresenter(this)
    }

    override fun ui() {
        //multipleStatusLayout.showLoading()
        if (orderAdapter == null) {
            orderAdapter = OrderListAdapter(sectionList)
            orderRecycle.adapter = orderAdapter
            //orderRecycle.addItemDecoration(UnifitItemDecoration(12f))
            orderAdapter?.addFooterView(XavierViewUtils.getDivide12View(context))
        }

        httpOrderList()
    }

    private fun httpOrderList() {
        map["page"] = orderPage
        presenter?.orderList(map)
    }

    override fun showOrderList(t: List<OrderListResult>) {
        //multipleStatusLayout.showContent()
        for (orderListResult in t) {
            sectionList.add(OrderListResultSectionEntity(orderListResult, null, true))
            val goods = orderListResult.goods
            if (!goods.isNullOrEmpty()) {
                for (goodItem in goods) {
                    sectionList.add(OrderListResultSectionEntity(orderListResult, goodItem, false))
                }
            }
        }
        orderAdapter?.notifyDataSetChanged()
    }

    override fun errorOrderList(message: String) {
        message.toast()
    }

    override fun showRecommendGoods(commodityResult: CommodityResult) {
    }

    override fun errorRecommendGoods(message: String) {
        message.toast()
    }

    override fun showMessage(message: String?) {
        message.toast()
    }

    companion object {
        @JvmStatic
        fun newInstance(param: TabEntity) =
            OrderListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM, param)
                }
            }
    }

    class OrderListAdapter(
        data: MutableList<OrderListResultSectionEntity>?
    ) : BaseSectionQuickAdapter<OrderListResultSectionEntity, BaseViewHolder>(
        R.layout.recycle_item_header_shop_list,
        R.layout.recycle_item_order_list,
        data
    ) {

        override fun convertHeader(helper: BaseViewHolder, item: OrderListResultSectionEntity) {
            helper.getView<View>(R.id.storeView).background =
                MaterialShapeDrawableUtils.topShapeDrawable(4f, R.color.white)
            item.shop?.let {
                helper.setText(R.id.storeName, it.shopName)
                    .setText(R.id.status, XavierOrderUtils.pointOrderStatus(it.status))
            }
        }

        override fun convert(holder: BaseViewHolder, item: OrderListResultSectionEntity) {
            holder.itemView.background =
                MaterialShapeDrawableUtils.bottomShapeDrawable(4f, R.color.white)
            val layoutPosition = holder.layoutPosition
            val footer = layoutPosition == data.lastIndex || data[layoutPosition + 1].isHeader
            item.goods?.let {
                val rebateOrderMsg = it.rebateOrderMsg
                val singleStatus = it.singleStatus
                val num = it.num
                GlideEngine.assignResIdCircleCorner(
                    context,
                    it.goodsImage,
                    holder.getView(R.id.commodityImage)
                )
                holder.setText(R.id.commodityPrice, it.price)
                    .setText(R.id.commodityName, it.goodsName)
                    .setText(R.id.commodityNumber, "X${num}")
                    .setText(R.id.commoditySku, it.skuCondition)
                    .setVisible(R.id.commodityCommission, !rebateOrderMsg.isNullOrEmpty())
                    .setText(R.id.commodityCommission, rebateOrderMsg ?: "")
                    .setText(
                        R.id.commodityStatus,
                        XavierOrderUtils.pointCommodityStatus(singleStatus)
                    )
                if (footer) {
                    holder.setVisible(R.id.commodityTotal, true)
                        .setVisible(R.id.actionLayout, true)
                    val total = holder.getView<TextView>(R.id.commodityTotal)
                    val actionLayout = holder.getView<LinearLayout>(R.id.actionLayout)
                    SpanUtils.with(total)
                        .append("共计${num}件商品 合计：")
                        .append("￥${it.realMoney}")
                        .setFontSize(14, true)
                        .setForegroundColor(R.color.textColor.value())
                        .create()
                    XavierOrderUtils.pointOrderAction(
                        singleStatus,
                        item.shop?.shippingMethod ?: 0,
                        actionLayout
                    )
                } else {
                    holder.setGone(R.id.commodityTotal, true)
                        .setGone(R.id.actionLayout, true)
                }
            }
        }


    }

}