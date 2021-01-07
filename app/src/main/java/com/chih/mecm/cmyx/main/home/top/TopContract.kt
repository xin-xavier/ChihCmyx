package com.chih.mecm.cmyx.main.home.top

import com.chih.mecm.cmyx.base.presentation.ViewPresentation
import com.chih.mecm.cmyx.base.presenter.IBasePresenter
import com.chih.mecm.cmyx.bean.result.HomeAdResult
import com.chih.mecm.cmyx.bean.result.HomeChoiceShopListItem
import com.chih.mecm.cmyx.bean.result.SlideShowResult
import com.chih.mecm.cmyx.bean.result.SubClazzResult
import com.xavier.simple.demo.bean.result.HotResult

interface TopContract {

    interface View : ViewPresentation {
        fun showSlideShow(slideShowResult: List<SlideShowResult>)
        fun errorSlideShow(message: String)
        fun showHomeAd(adList: List<HomeAdResult>)
        fun errorHomeAd(message: String)
        fun showSubClazz(subclassList: List<SubClazzResult>)
        fun errorSubClazz(message: String)
        fun showHomeChoiceShop(choiceLists: List<List<HomeChoiceShopListItem>>)
        fun errorHomeChoiceShop(message: String)
        fun showHot(hotResult: HotResult)
        fun errorHot(errorMsg: String)
    }

    interface Presenter<V> : IBasePresenter<View> {
        fun slideShow(pid: Int)
        fun homeAd()
        fun subClazz(pid: Int)
        fun homeChoiceShop()
        fun hot(page: Int)
    }

}