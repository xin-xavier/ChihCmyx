package com.chih.mecm.cmyx.main.mine.login

import com.chih.mecm.cmyx.base.presentation.ViewPresentation
import com.chih.mecm.cmyx.base.presenter.IBasePresenter
import com.chih.mecm.cmyx.bean.result.LoginResult

interface RegistrationContract {

    interface View : ViewPresentation {
        fun showRegister(t: LoginResult)
    }

    interface Presenter<V> : IBasePresenter<View> {
        fun register(map: Map<String, Any>)
    }

}