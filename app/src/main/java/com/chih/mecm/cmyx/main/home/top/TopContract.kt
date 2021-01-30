package com.chih.mecm.cmyx.main.home.top

import com.chih.mecm.cmyx.base.presentation.ViewPresentation
import com.chih.mecm.cmyx.base.presenter.IBasePresenter
import com.chih.mecm.cmyx.bean.result.*

interface TopContract {

    interface View : ViewPresentation {
        fun showSlideShow(slideShowResult: MutableList<BannerItem>)
        fun errorSlideShow(message: String)
        fun showHomeAd(adResult: HomeAdResult)
        fun errorHomeAd(message: String)
        fun showSubClazz(subClazzList: List<SubClazzResult>)
        fun errorSubClazz(message: String)
        fun showHomeChoiceShop(choiceLists: List<List<HomeChoiceShopListItem>>)
        fun errorHomeChoiceShop(message: String)
        fun showHot(hotResult: CommodityResult)
        fun errorHot(errorMsg: String)
    }

    interface Presenter<V> : IBasePresenter<View> {
        fun slideShow(pid: Int)
        fun homeAd()
        fun subClazz(pid: Int)
        fun homeChoiceShop()
        fun hot(page: Int)
        fun subGoods(map: Map<String,Int>)
    }

}