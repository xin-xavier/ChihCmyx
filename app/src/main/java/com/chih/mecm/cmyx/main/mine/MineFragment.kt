package com.chih.mecm.cmyx.main.mine

import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.fragment.SimpleWithBarFragment
import com.chih.mecm.cmyx.main.mine.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : SimpleWithBarFragment() {

    override fun contentLayoutRes()=R.layout.fragment_mine

    override fun ui() {
        longText.setOnClickListener {
            intent(LoginActivity::class.java)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MineFragment()
    }
}