package com.chih.mecm.cmyx.base.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.chih.mecm.cmyx.app.AppManager
import com.chih.mecm.cmyx.app.login.LoginActivity
import com.chih.mecm.cmyx.base.presentation.UIPresentation
import com.jaeger.library.StatusBarUtil

abstract class SimpleFragment : LifeCycleFragment, UIPresentation {

    open var contentLayoutId: Int = 0
    var rootView: View? = null

    // 视图是否准备好了
    var isPrepare = false

    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId) {
        this.contentLayoutId = contentLayoutId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            getParameter()
        }
    }

    // 获取参数
    protected open fun getParameter() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = rootView ?: view
        isPrepare = true
        if (immersionBarEnabled()) {
            immersionBar()
        }
        ui()
    }

    // 初始化状态栏
    protected open fun immersionBar() {
        activity?.let {
            StatusBarUtil.setTransparentForImageViewInFragment(it, null)
            if (BarUtils.isStatusBarLightMode(it)) {
                BarUtils.setStatusBarLightMode(it, false)
            } else {
                BarUtils.setStatusBarLightMode(it, true)
            }
        }
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    open fun immersionBarEnabled(): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rootView = null
        isPrepare = false
    }

    /**
     * @method 页面跳转
     * @param clazz clazz
     */
    protected fun intent(clazz: Class<*>) {
        startActivity(Intent(context, clazz))
    }

    /**
     * @method 携带bundle的页面跳转
     * @param clazz clazz
     * @param bundle bundle
     */
    protected fun intent(clazz: Class<*>, bundle: Bundle) {
        startActivity(Intent(context, clazz).apply {
            putExtras(bundle)
        })
    }

    /**
     * @method 跳转到需要登录的页面
     * @param clazz clazz
     */
    protected fun intentWhenLoggedin(clazz: Class<*>) {
        //需要登录&&未登录
        if (AppManager.isLogin()) {
            startActivity(Intent(context, clazz))
        } else {
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    /**
     * @method 携带bundle跳转到需要登录的页面
     * @param clazz clazz
     * @param bundle bundle
     */
    protected fun intentWhenLoggedin(clazz: Class<*>, bundle: Bundle) {
        //需要登录&&未登录
        if (AppManager.isLogin()) {
            startActivity(Intent(context, clazz))
        } else {
            startActivity(Intent(context, clazz).apply {
                putExtras(bundle)
            })
        }
    }


}