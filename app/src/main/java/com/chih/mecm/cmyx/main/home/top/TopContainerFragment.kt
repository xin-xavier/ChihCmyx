package com.chih.mecm.cmyx.main.home.top

import android.os.Bundle
import com.chih.mecm.cmyx.base.fragment.BaseFragment

private const val TYPE = "type"
private const val PID = "pid"
private const val PAGE_POSItION = "page_position"

abstract class TopContainerFragment :
    BaseFragment<TopContract.Presenter<TopContract.View>>(), TopContract.View {

    open var type: String = ""
    open var pid: Int = 0
    open var pagePosition: Int = 0

    var page: Int = 1
    val map = mutableMapOf<String, Int>()

    override fun getParameter() {
        super.getParameter()
        type = arguments!!.getString(TYPE, "")
        pid = arguments!!.getInt(PID, 0)
        pagePosition = arguments!!.getInt(PAGE_POSItION, 0)
    }

    override fun ui() {
        presenter?.let {
            it.slideShow(pid)
            if (pagePosition == 0) {
                it.homeAd()
                it.homeChoiceShop()
                it.hot(page)
            } else {
                it.subClazz(pid)
                map["pid"] = pid
                map["page"] = page
                it.subGoods(map)
            }
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