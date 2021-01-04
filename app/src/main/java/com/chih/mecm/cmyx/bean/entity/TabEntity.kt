package com.chih.mecm.cmyx.bean.entity

import com.chih.mecm.cmyx.R
import com.flyco.tablayout.listener.CustomTabEntity

data class TabEntity(
    var title: String = "",
    var selectedIcon: Int = R.mipmap.ic_launcher_round,
    var unSelectedIcon: Int = R.mipmap.ic_launcher_round
) :
    CustomTabEntity {

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }
}
