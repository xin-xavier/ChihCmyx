package com.chih.mecm.cmyx.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import timber.log.Timber

open class LifeCycleFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("onAttach: Called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate: Called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView: Called")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated: Called")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onActivityCreated: Called")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart: Called")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume: Called")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause: Called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView: Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy: Called")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("onDetach: Called")
    }

}