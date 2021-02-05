package com.chih.mecm.cmyx.order.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SpanUtils
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.adapter.pager.CommodityAdapter
import com.chih.mecm.cmyx.base.fragment.BaseFragment
import com.chih.mecm.cmyx.bean.entity.TabEntity
import com.chih.mecm.cmyx.bean.result.CommodityItem
import com.chih.mecm.cmyx.bean.result.CommodityResult
import com.chih.mecm.cmyx.bean.result.OrderListResult
import com.chih.mecm.cmyx.bean.result.OrderListResultSectionEntity
import com.chih.mecm.cmyx.extend.dp
import com.chih.mecm.cmyx.extend.toast
import com.chih.mecm.cmyx.extend.value
import com.chih.mecm.cmyx.utils.GlideEngine
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils
import com.chih.mecm.cmyx.utils.XavierOrderUtils
import com.chih.mecm.cmyx.utils.XavierViewUtils
import kotlinx.android.synthetic.main.fragment_order_list.*
import kotlinx.android.synthetic.main.multip_horizontal_12dp_space_recycler_view.*

private const val ARG_PARAM = "param"

class OrderListFragment : BaseFragment<OrderListContract.Presenter<OrderListContract.View>>(),
    OrderListContract.View {

    private lateinit var param: TabEntity

    private var limit = 8
    private var orderPage = 1

    val map = mutableMapOf<String, Int>("limit" to limit, "page" to orderPage)

    private val sectionList = mutableListOf<OrderListResultSectionEntity>()
    private var orderAdapter: OrderListAdapter? = null
    private var orderTotal: Int = 0

    private var recommendRecycle: RecyclerView? = null
    private val rowsList = mutableListOf<CommodityItem>()
    private var rowsTotal = 0
    private var commodityAdapter: CommodityAdapter? = null

    private var rowsPage = 1

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
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }

    override fun ui() {
        refreshLayout.setOnRefreshListener {
            clearPage()
            httpOrderList()
        }
        refreshLayout.setOnLoadMoreListener {
            if (sectionList.size >= orderTotal) {
                orderPage++
                httpOrderList()
            } else {
                rowsPage++
                presenter?.recommendGoods(rowsPage)
            }
        }
    }

    override fun createPresenter(): OrderListContract.Presenter<OrderListContract.View> {
        return OrderListPresenter(this)
    }

    override fun onStart() {
        super.onStart()

        multipleStatusLayout.showLoading()

        if (orderAdapter == null) {
            orderAdapter = OrderListAdapter(sectionList)
            universalRecycle.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            universalRecycle.adapter = orderAdapter
            //orderAdapter?.addFooterView(XavierViewUtils.getDivide12View(context))
        }

        httpOrderList()
    }

    private fun httpOrderList() {
        map["page"] = orderPage
        presenter?.orderList(map)
    }

    override fun showOrderList(t: List<OrderListResult>) {
        XavierViewUtils.finishRefreshLayoutAnim(refreshLayout)
        enableRefreshLayout()

        universalRecycle.postDelayed({
            multipleStatusLayout.showContent()
        }, 1500)

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

        if (sectionList.isNotEmpty()) {
            orderTotal = sectionList[0].shop.orderTotal
        }
        if (sectionList.size >= orderTotal) {
            presenter?.recommendGoods(rowsPage)
        }
    }

    override fun errorOrderList(message: String) {
        XavierViewUtils.finishRefreshLayoutAnim(refreshLayout)
        enableRefreshLayout()

        if (sectionList.isEmpty()) {
            multipleStatusLayout.showEmpty()
        }
        presenter?.recommendGoods(rowsPage)
    }

    override fun showRecommendGoods(commodityResult: CommodityResult) {
        XavierViewUtils.finishRefreshLayoutAnim(refreshLayout)

        rowsTotal = commodityResult.total
        val rows = commodityResult.rows
        if (!rows.isNullOrEmpty()) {
            rowsList.addAll(rows)
        }
        if (rowsList.isNotEmpty()) {
            addFooterView()
        }
        recommendRecycle?.let { recycle ->
            if (commodityAdapter == null) {
                recycle.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
                commodityAdapter = CommodityAdapter(rowsList)
                recycle.adapter = commodityAdapter
                commodityAdapter?.addHeaderView(getLabelView())
            } else {
                commodityAdapter?.notifyDataSetChanged()
            }
        }
    }

    private fun addFooterView() {
        orderAdapter?.let {
            if (!it.hasFooterLayout()) {
                orderAdapter?.addFooterView(getRecommendRecycleView())
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun getRecommendRecycleView(): View {
        val inflate = layoutInflater.inflate(R.layout.multip_universal_recycler_view, null)
        recommendRecycle = inflate.findViewById<RecyclerView>(R.id.universalRecycle)
        return inflate
    }

    @SuppressLint("InflateParams")
    private fun getLabelView(): View {
        val inflate = layoutInflater.inflate(R.layout.include_label_start_view_end_text, null)
        val labelConstraintLayout =
            inflate.findViewById<ConstraintLayout>(R.id.labelConstraintLayout)
        val verticalPadding = 12f.dp().toInt()
        labelConstraintLayout.setPadding(0, verticalPadding, 0, verticalPadding)
        return inflate
    }

    override fun errorRecommendGoods(message: String) {
        XavierViewUtils.finishRefreshLayoutAnim(refreshLayout)

        if (rowsList.isEmpty()) {
            message.toast()
        }
    }

    override fun showMessage(message: String?) {
        message.toast()
    }

    override fun onStop() {
        super.onStop()
        clearPage()
    }

    private fun clearPage() {
        enableRefreshLayout(false)

        orderPage = 1
        sectionList.clear()
        orderAdapter?.removeAllFooterView()

        rowsList.clear()
    }

    private fun enableRefreshLayout(enable: Boolean = true) {
        refreshLayout.setEnableRefresh(enable)
        refreshLayout.setEnableLoadMore(enable)
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
            item.shop.let {
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