package com.chih.mecm.cmyx.main.home.top

import com.chih.mecm.cmyx.base.presenter.BasePresenter
import com.chih.mecm.cmyx.bean.result.HomeAdResult
import com.chih.mecm.cmyx.bean.result.HomeChoiceShopListItem
import com.chih.mecm.cmyx.bean.result.SlideShowResult
import com.chih.mecm.cmyx.bean.result.SubClazzResult
import com.chih.mecm.cmyx.http.client.RetrofitHelper
import com.chih.mecm.cmyx.http.observer.HttpDefaultObserver
import com.chih.mecm.cmyx.main.home.HomeContract
import com.xavier.simple.demo.bean.result.HotResult
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
            .subscribe(object : HttpDefaultObserver<List<SlideShowResult>>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: List<SlideShowResult>) {
                    view?.showSlideShow(t)
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
            .subscribe(object : HttpDefaultObserver<List<HomeAdResult>>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: List<HomeAdResult>) {
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
                    view?.showSubClazz(t)
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
            .subscribe(object :HttpDefaultObserver<List<List<HomeChoiceShopListItem>>>(){
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: List<List<HomeChoiceShopListItem>>) {
                    view?.showHomeChoiceShop(t)
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
            .subscribe(object : HttpDefaultObserver<HotResult>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: HotResult) {
                    view?.showHot(t)
                }

                override fun onError(errorMessage: String) {
                    view?.errorHot(errorMessage)
                }

            })
    }


}