package com.chih.mecm.cmyx.main.home.top

import android.os.Bundle
import com.chih.mecm.cmyx.base.fragment.BaseFragment

private const val TYPE = "type"
private const val PID = "pid"
private const val PAGE_POSItION = "page_position"

abstract class TopContainerFragment :
    BaseFragment<TopContract.Presenter<TopContract.View>>(), TopContract.View {

    var type: String = ""
    var pid: Int = 0
    var pageItem: Int = 0

    var page: Int = 1

    override fun getParameter() {
        super.getParameter()
        type = arguments!!.getString(TYPE, "")
        pid = arguments!!.getInt(PID, 0)
    }

    override fun ui() {
        presenter?.let {
            it.slideShow(pid)
            if (pageItem == 0) {
                it.homeAd()
                it.homeChoiceShop()
            } else {
                it.subClazz(pid)
            }
            it.hot(page)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
            type: String,
            pid: Int,
            position: Int
        ) =
            TopFragment().apply {
                arguments = Bundle().apply {
                    putString(TYPE, type)
                    putInt(PID, pid)
                    putInt(PAGE_POSItION, position)
                }
            }
    }

}