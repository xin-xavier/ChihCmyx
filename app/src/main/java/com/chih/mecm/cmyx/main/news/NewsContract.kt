package com.chih.mecm.cmyx.main.news

import com.chih.mecm.cmyx.base.presentation.ViewPresentation
import com.chih.mecm.cmyx.base.presenter.IBasePresenter
import com.chih.mecm.cmyx.bean.result.NewsChatResult

interface NewsContract {

    interface View : ViewPresentation {
        fun showChatList(t: NewsChatResult)
    }

    interface Presenter<V> : IBasePresenter<View> {
        fun chatList(page: Int)
    }

}