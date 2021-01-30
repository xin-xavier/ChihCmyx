package com.chih.mecm.cmyx.main.news

import com.chih.mecm.cmyx.base.presenter.BasePresenter
import com.chih.mecm.cmyx.bean.result.NewsChatResult
import com.chih.mecm.cmyx.http.client.RetrofitHelper
import com.chih.mecm.cmyx.http.observer.BusinessHttpException
import com.chih.mecm.cmyx.http.observer.HttpDefaultObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NewsPresenter(view: NewsContract.View) : BasePresenter<NewsContract.View>(view),
    NewsContract.Presenter<NewsContract.View> {
    override fun chatList(page: Int) {
        RetrofitHelper.apiServer
            .chatList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<NewsChatResult>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: NewsChatResult) {
                    if(t.dataList.isNullOrEmpty()){
                        onError(BusinessHttpException("暂无数据",0))
                    }else{
                        view?.showChatList(t)
                    }
                }

                override fun onError(errorMessage: String) {
                    view?.showMessage(errorMessage)
                }

            })
    }
}