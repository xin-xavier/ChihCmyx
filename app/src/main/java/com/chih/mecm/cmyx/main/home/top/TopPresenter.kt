package com.chih.mecm.cmyx.main.home.top

import com.chih.mecm.cmyx.base.presenter.BasePresenter
import com.chih.mecm.cmyx.bean.result.*
import com.chih.mecm.cmyx.http.client.RetrofitHelper
import com.chih.mecm.cmyx.http.observer.BusinessHttpException
import com.chih.mecm.cmyx.http.observer.HttpDefaultObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TopPresenter(view: TopContract.View) : BasePresenter<TopContract.View>(view),
    TopContract.Presenter<TopContract.View> {

    override fun slideShow(pid: Int) {
        RetrofitHelper.apiServer
            .slideShow(pid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<SlideShowResult>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: SlideShowResult) {
                    val banner = t.banner
                    if (banner.isNullOrEmpty()) {
                        onError(BusinessHttpException("暂无数据", 0))
                    } else {
                        val user = t.user
                        for (bannerItem in banner) {
                            bannerItem.user = user
                        }
                        view?.showSlideShow(banner)
                    }
                }

                override fun onError(errorMessage: String) {
                    view?.errorSlideShow(errorMessage)
                }

            })
    }

    override fun homeAd() {
        RetrofitHelper.apiServer
            .homeAd()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<HomeAdResult>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: HomeAdResult) {
                    view?.showHomeAd(t)
                }

                override fun onError(errorMessage: String) {
                    view?.errorHomeAd(errorMessage)
                }


            })
    }

    override fun subClazz(pid: Int) {
        RetrofitHelper.apiServer
            .subClazz(pid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<List<SubClazzResult>>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: List<SubClazzResult>) {
                    if (t.isEmpty()) {
                        onError(BusinessHttpException("暂无数据", 0))
                    } else {
                        view?.showSubClazz(t)
                    }
                }

                override fun onError(errorMessage: String) {
                    view?.errorSubClazz(errorMessage)
                }

            })

    }

    override fun homeChoiceShop() {
        RetrofitHelper.apiServer
            .homeChoiceShop()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<List<List<HomeChoiceShopListItem>>>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: List<List<HomeChoiceShopListItem>>) {
                    if (t.isEmpty()) {
                        onError(BusinessHttpException("暂无数据", 0))
                    } else {
                        view?.showHomeChoiceShop(t)
                    }
                }

                override fun onError(errorMessage: String) {
                    view?.errorHomeChoiceShop(errorMessage)
                }

            })
    }

    override fun hot(page: Int) {
        RetrofitHelper.apiServer
            .hot(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<CommodityResult>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: CommodityResult) {
                    if (t.rows.isNullOrEmpty()) {
                        onError(BusinessHttpException("暂无数据", 0))
                    } else {
                        view?.showHot(t)
                    }
                }

                override fun onError(errorMessage: String) {
                    view?.errorHot(errorMessage)
                }

            })
    }

    override fun subGoods(map: Map<String, Int>) {
        RetrofitHelper.apiServer
            .subGoods(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<CommodityResult>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: CommodityResult) {
                    if (t.rows.isNullOrEmpty()) {
                        onError(BusinessHttpException("暂无数据", 0))
                    } else {
                        view?.showHot(t)
                    }
                }

                override fun onError(errorMessage: String) {
                    view?.errorHot(errorMessage)
                }

            })
    }


}