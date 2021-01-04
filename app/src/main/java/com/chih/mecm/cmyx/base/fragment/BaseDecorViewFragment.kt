package com.chih.mecm.cmyx.base.fragment

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.chih.mecm.cmyx.base.presenter.IBasePresenter

abstract class BaseDecorViewFragment<P : IBasePresenter<*>>(@LayoutRes override var contentLayoutId: Int = 0) :
    SimpleDecorViewFragment() {

    var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        presenter?.let { lifecycle.addObserver(it) }
    }

    protected abstract fun createPresenter(): P?


}