package com.chih.mecm.cmyx.base.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.app.AppManager
import com.chih.mecm.cmyx.app.login.LoginActivity
import com.chih.mecm.cmyx.base.presentation.UIPresentation
import com.gyf.immersionbar.ImmersionBar
import com.jaeger.library.StatusBarUtil

abstract class SimpleActivity : LifeCycleActivity(),
    UIPresentation {

    lateinit var immersionBar: ImmersionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar = ImmersionBar.with(this)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        withImmersionBar()
        ui()
    }

    // 初始化状态栏
    open fun withImmersionBar() {
        immersionBar
            .transparentStatusBar() //透明状态栏
            .statusBarDarkFont(true) //状态栏字体是深色
            .navigationBarColor(R.color.white) //导航栏颜色
            .navigationBarDarkIcon(true) //导航栏图标是深色
            .init() //通过上面配置后初始化后方可成功调用
    }

    override fun onResume() {
        super.onResume()
        AppManager.setForeground(true)
    }

    /**
     * @method 页面跳转
     * @param clazz clazz
     */
    protected fun intent(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }

    /**
     * @method 携带bundle的页面跳转
     * @param clazz clazz
     * @param bundle bundle
     */
    protected fun intent(clazz: Class<*>, bundle: Bundle) {
        startActivity(Intent(this, clazz).apply {
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
            startActivity(Intent(this, clazz))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
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
            startActivity(Intent(this, clazz))
        } else {
            startActivity(Intent(this, clazz).apply {
                putExtras(bundle)
            })
        }
    }

}