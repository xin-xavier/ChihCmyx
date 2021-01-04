package com.chih.mecm.cmyx.main.home

import com.chih.mecm.cmyx.base.presenter.BasePresenter
import com.chih.mecm.cmyx.bean.result.TopClazzResult
import com.chih.mecm.cmyx.http.client.RetrofitHelper
import com.chih.mecm.cmyx.http.observer.HttpDefaultObserver
import com.chih.mecm.cmyx.http.observer.HttpSuccessObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class HomePresenter(view: HomeContract.View) : BasePresenter<HomeContract.View>(view),
    HomeContract.Presenter<HomeContract.View> {
    override fun topClazz() {
        RetrofitHelper.apiServer
            .topClazz()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<List<TopClazzResult>>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: List<TopClazzResult>) {
                    Timber.i(t.toString())
                    view?.showTopClazz(t)
                }

                override fun onError(errorMessage: String) {
                    view?.showMessage(errorMessage)
                }


            })
    }

    override fun getDefaultSearch() {
        RetrofitHelper.apiServer
            .getDefaultSearch()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpSuccessObserver<String>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }
                override fun onSuccess(t: String) {
                    view?.showDefaultSearch(t)
                }
            })
    }


}