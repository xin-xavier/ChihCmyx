package com.chih.mecm.cmyx.base.presenter

import com.chih.mecm.cmyx.base.presentation.ViewPresentation
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<V : ViewPresentation>(view: V) :
    IBasePresenter<V> {

    protected var view: V? = view
    private var compositeDisposable: CompositeDisposable? = null

    init {
        compositeDisposable = CompositeDisposable()
    }

    override fun onCreate() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onDestroy() {
        compositeDisposable?.clear()
    }

    protected fun addSubscribe(disposable: Disposable) {
        compositeDisposable?.add(disposable)
    }

}
