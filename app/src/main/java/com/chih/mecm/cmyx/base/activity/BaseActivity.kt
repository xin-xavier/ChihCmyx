package com.chih.mecm.cmyx.base.activity

import com.chih.mecm.cmyx.base.presenter.IBasePresenter

abstract class BaseActivity<P: IBasePresenter<*>>  : SimpleActivity(){

    var presenter:P? = null

    override fun setContentView(layoutResID: Int) {
        presenter = createPresenter()
        presenter?.let {
            lifecycle.addObserver(it)
        }
        super.setContentView(layoutResID)
    }

    protected abstract fun createPresenter(): P?

}