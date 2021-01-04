package com.chih.mecm.cmyx.main.home

import com.chih.mecm.cmyx.base.presentation.ViewPresentation
import com.chih.mecm.cmyx.base.presenter.IBasePresenter
import com.chih.mecm.cmyx.bean.result.TopClazzResult

interface HomeContract {

    interface View : ViewPresentation {
        fun showTopClazz(list: List<TopClazzResult>)
        fun showDefaultSearch(keyword: String)
    }

    interface Presenter<V> : IBasePresenter<View> {
        fun topClazz()
        fun getDefaultSearch()
    }

}