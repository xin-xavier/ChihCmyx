package com.chih.mecm.cmyx.main.home.top

import com.chih.mecm.cmyx.base.presentation.ViewPresentation
import com.chih.mecm.cmyx.base.presenter.IBasePresenter
import com.chih.mecm.cmyx.bean.result.SlideShowResult

interface TopContract {

    interface View : ViewPresentation {
        fun showSlideShow(t : SlideShowResult)
        fun errorSlideShow()
        fun showHomeAd()
    }

    interface Presenter<V> : IBasePresenter<View> {
        fun slideShow(pid: Int)
        fun homeAd()
        fun subClass(pid: Int)
        fun homeChoiceShop()
        fun hot(page: Int)
    }

}