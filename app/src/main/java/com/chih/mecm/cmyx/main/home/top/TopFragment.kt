package com.chih.mecm.cmyx.main.home.top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.fragment.SimpleFragment

private const val TYPE = "type"
private const val CLAZZ_ID = "clazz_id"

class TopFragment : SimpleFragment() {

    private var type: String = ""
    private var page: Int = 0

    override fun getParameter() {
        super.getParameter()
        type = arguments!!.getString(TYPE,"")
        page = arguments!!.getInt(CLAZZ_ID,0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun ui() {

    }

    companion object {
        @JvmStatic
        fun newInstance(type: String, clazzId: Int) =
            TopFragment().apply {
                arguments = Bundle().apply {
                    putString(TYPE, type)
                    putInt(CLAZZ_ID,clazzId)
                }
            }
    }
}