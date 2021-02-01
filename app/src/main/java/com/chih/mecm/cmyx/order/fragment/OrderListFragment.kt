package com.chih.mecm.cmyx.order.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.chih.mecm.cmyx.extend.dp
import com.chih.mecm.cmyx.extend.toast
import com.chih.mecm.cmyx.extend.value
import com.chih.mecm.cmyx.utils.GlideEngine
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils
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
        //return inflater.inflate(R.layout.fragment_order_list, container, false)
        return inflater.inflate(R.layout.fragment_multip_order_list, container, false)
    }

    override fun createPresenter(): OrderListContract.Presenter<OrderListContract.View> {
        return OrderListPresenter(this)
    }

    override fun ui() {
        multipleStatusLayout.showLoading()

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
        multipleStatusLayout.showContent()
        for (orderListResult in t) {
            sectionList.add(OrderListResultSectionEntity(orderListResult, null, true))
            val goods = orderListResult.goods
            if (!goods.isNullOrEmpty()) {
                for (goodItem in goods) {
                    sectionList.add(OrderListResultSectionEntity(null, goodItem, false))
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
                    .setText(R.id.status, getOrderStatus(it.status))
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
                    .setText(R.id.commodityStatus, getCommodityStatus(singleStatus))
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
                    val button = getButton("待付款")
                    actionLayout.addView(button)
                    setMarginStart(button)
                } else {
                    holder.setGone(R.id.commodityTotal, true)
                        .setGone(R.id.actionLayout, true)
                }
            }
        }

        private fun setOrderActionButton(){

        }

        private fun setMarginStart(button: Button) {
            val params = button.layoutParams as ViewGroup.MarginLayoutParams
            params.marginStart = 12f.dp().toInt()
        }

        private fun getButton(text: String): Button {
            val button = Button(context)
            button.width = 65f.dp().toInt()
            button.height = 25f.dp().toInt()
            button.gravity = Gravity.CENTER
            button.text = text
            button.setPadding(0, 0, 0, 0)
            return button
        }

        private fun getCommodityStatus(status: Int): String {
            return when (status) {
                0 -> "待付款"
                1 -> "待发货"
                2 -> "待收货"
                3 -> "待评价"
                4 -> "申请换货"
                5 -> "已完成"
                6 -> "取消订单"
                7 -> "申请退款"
                8 -> "已查看卡密"
                9 -> "同意退款"
                10 -> "拒绝退款"
                11 -> "申请退款退货"
                12 -> "退款中"
                13 -> "退款成功"
                14 -> "拒绝退款"
                15 -> "撤销退款"
                16 -> "换货中"
                17 -> "换货成功"
                18 -> "拒绝换货"
                19 -> "撤销换货"
                20 -> "退货退款中"
                21 -> "退货退款成功"
                22 -> "拒绝退货退款"
                23 -> "撤销退货退款"
                24 -> "交易关闭"
                else -> ""
            }
        }

        private fun getOrderStatus(status: Int): String {
            return when (status) {
                0 -> "买家未付款"
                1 -> "买家已付款"
                2, 8 -> "卖家已发货"
                3, 5 -> "交易完成"
                6 -> "取消订单"
                24 -> "交易关闭"
                else -> ""
            }
        }
    }

}