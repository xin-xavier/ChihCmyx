package com.chih.mecm.cmyx.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import timber.log.Timber

open class LifeCycleFragment : Fragment {

    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("${javaClass.simpleName} --- ${hashCode()} onAttach: Called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("${javaClass.simpleName} --- ${hashCode()} onCreate: Called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("${javaClass.simpleName} --- ${hashCode()} onCreateView: Called")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("${javaClass.simpleName} --- ${hashCode()} onViewCreated: Called")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("${javaClass.simpleName} --- ${hashCode()} onActivityCreated: Called")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("${javaClass.simpleName} --- ${hashCode()} onStart: Called")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("${javaClass.simpleName} --- ${hashCode()} onResume: Called")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("${javaClass.simpleName} --- ${hashCode()} onPause: Called")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("${javaClass.simpleName} --- ${hashCode()} onStop: Called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("${javaClass.simpleName} --- ${hashCode()} onDestroyView: Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("${javaClass.simpleName} --- ${hashCode()} onDestroy: Called")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("${javaClass.simpleName} --- ${hashCode()} onDetach: Called")
    }

}