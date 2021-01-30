package com.chih.mecm.cmyx.order.fragment

import com.chih.mecm.cmyx.base.presentation.ViewPresentation
import com.chih.mecm.cmyx.base.presenter.IBasePresenter
import com.chih.mecm.cmyx.bean.result.CommodityResult
import com.chih.mecm.cmyx.bean.result.OrderListResult

interface OrderListContract {

    interface View : ViewPresentation {
        fun showOrderList(t: List<OrderListResult>)
        fun errorOrderList(message: String)
        fun showRecommendGoods(commodityResult: CommodityResult)
        fun errorRecommendGoods(message: String)
    }

    interface Presenter<V> : IBasePresenter<View> {
        fun orderList(map: Map<String, Int>)
        fun recommendGoods(page: Int)
    }

}