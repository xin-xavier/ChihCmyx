package com.chih.mecm.cmyx

import com.chih.mecm.cmyx.base.fragment.SimpleWithBarFragment

class TemporaryFragment : SimpleWithBarFragment() {

    override fun contentLayoutRes(): Int {
        return R.layout.fragment_temporary
    }

    override fun ui() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = TemporaryFragment()
    }
}