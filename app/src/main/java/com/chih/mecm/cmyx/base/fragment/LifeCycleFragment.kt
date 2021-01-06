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
        Timber.d("${this.javaClass.simpleName} onAttach: Called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("${this.javaClass.simpleName} onCreate: Called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("${this.javaClass.simpleName} onCreateView: Called")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("${this.javaClass.simpleName} onViewCreated: Called")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("${this.javaClass.simpleName} onActivityCreated: Called")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("${this.javaClass.simpleName} onStart: Called")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("${this.javaClass.simpleName} onResume: Called")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("${this.javaClass.simpleName} onPause: Called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("${this.javaClass.simpleName} onDestroyView: Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("${this.javaClass.simpleName} onDestroy: Called")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("${this.javaClass.simpleName} onDetach: Called")
    }

}