package com.chih.mecm.cmyx.bean.entity

import android.os.Parcelable
import com.chih.mecm.cmyx.R
import com.flyco.tablayout.listener.CustomTabEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TabEntity(
    var tab: String = "",
    var selectedIcon: Int = R.mipmap.ic_launcher_round,
    var unSelectedIcon: Int = R.mipmap.ic_launcher_round,
    var type: Int = 0,
    var subTitle: String = "",
    var directions: String = "",
    var minor: String = ""
) :
    CustomTabEntity, Parcelable {

    override fun getTabTitle(): String {
        return tab
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }
}
