package com.chih.mecm.cmyx.order.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.fragment.BaseFragment
import com.chih.mecm.cmyx.bean.entity.TabEntity
import com.chih.mecm.cmyx.bean.result.CommodityResult
import com.chih.mecm.cmyx.bean.result.OrderListResult
import com.chih.mecm.cmyx.bean.result.OrderListResultSectionEntity
import com.chih.mecm.cmyx.extend.toast
import com.chih.mecm.cmyx.utils.GlideEngine
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils
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
        override fun convert(holder: BaseViewHolder, item: OrderListResultSectionEntity) {
            holder.itemView.background =
                MaterialShapeDrawableUtils.topShapeDrawable(4f, R.color.white)
            item.goods?.let {
                GlideEngine.assignResIdCircleCorner(
                    context,
                    it.goodsImage,
                    holder.getView(R.id.commodityImage)
                )
                holder.setText(R.id.commodityPrice, it.price)
                    .setText(R.id.commodityName, it.goodsName)
                    .setText(R.id.commodityNumber, "X${it.num}")
                    .setText(R.id.commoditySku, it.skuCondition)
            }
        }

        override fun convertHeader(helper: BaseViewHolder, item: OrderListResultSectionEntity) {
            helper.itemView.background =
                MaterialShapeDrawableUtils.bottomShapeDrawable(4f, R.color.white)
            item.shop?.let {
                helper.setText(R.id.shopName, it.shopName)
                    .setText(R.id.status, "status")
            }
        }
    }

}