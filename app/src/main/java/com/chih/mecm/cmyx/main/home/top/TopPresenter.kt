package com.chih.mecm.cmyx.main.home.top

import com.chih.mecm.cmyx.base.presenter.BasePresenter
import com.chih.mecm.cmyx.http.client.RetrofitHelper
import com.chih.mecm.cmyx.main.home.HomeContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TopPresenter(view: TopContract.View) : BasePresenter<TopContract.View>(view),
    TopContract.Presenter<TopContract.View> {

    override fun slideShow(pid: Int) {
        RetrofitHelper.apiServer
            .slideShow(pid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun homeAd() {
        TODO("Not yet implemented")
    }

    override fun subClass(pid: Int) {
        TODO("Not yet implemented")
    }

    override fun homeChoiceShop() {
        TODO("Not yet implemented")
    }

    override fun hot(page: Int) {
        TODO("Not yet implemented")
    }


}