package com.chih.mecm.cmyx.base.fragment

import android.os.Bundle
import com.chih.mecm.cmyx.base.presenter.IBasePresenter

abstract class BaseWithBarFragment<P : IBasePresenter<*>> : SimpleWithBarFragment {

    var presenter: P? = null

    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        presenter?.let { lifecycle.addObserver(it) }
    }

    protected abstract fun createPresenter(): P?


}