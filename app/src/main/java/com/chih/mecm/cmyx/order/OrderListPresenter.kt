package com.chih.mecm.cmyx.order

import com.chih.mecm.cmyx.base.presenter.BasePresenter
import com.chih.mecm.cmyx.bean.result.CommodityResult
import com.chih.mecm.cmyx.bean.result.OrderListResult
import com.chih.mecm.cmyx.http.client.RetrofitHelper
import com.chih.mecm.cmyx.http.observer.BusinessHttpException
import com.chih.mecm.cmyx.http.observer.HttpDefaultObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class OrderListPresenter(view: OrderListContract.View) :
    BasePresenter<OrderListContract.View>(view),
    OrderListContract.Presenter<OrderListContract.View> {

    override fun orderList(map: Map<String, Int>) {
        RetrofitHelper.apiServer
            .orderList(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<List<OrderListResult>>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: List<OrderListResult>) {
                    if(t.isNullOrEmpty()){
                        onError(BusinessHttpException("暂无数据", 0))
                    }else{
                        view?.showOrderList(t)
                    }
                }


                override fun onError(errorMessage: String) {
                    view?.errorOrderList(errorMessage)
                }

            })
    }

    override fun recommendGoods(page: Int) {
        RetrofitHelper.apiServer
            .recommendGoods(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<CommodityResult>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: CommodityResult) {
                    view?.showRecommendGoods(t)
                }

                override fun onError(errorMessage: String) {
                    view?.errorRecommendGoods(errorMessage)
                }

            })
    }
}