package com.chih.mecm.cmyx.base.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

open class LifeCycleActivity : AppCompatActivity() {

    /**
     * 表示 Activity 正在被创建，常用来 初始化工作，比
     * 如调用 setContentView 加载界面布局资源，初始化 Activity 所
     * 需数据等
     *
     * @param savedInstanceState
     */
    override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onCreate(savedInstanceState, persistentState)
        Timber.d("onCreate: Called")
    }

    /**
     * onRestart()：表示 Activity 正在重新启动，一般情况下，当前
     * Acitivty 从不可见重新变为可见时，OnRestart就会被调用
     */
    override fun onRestart() {
        super.onRestart()
        Timber.d("onCreate: onRestart")
    }

    /**
     * onStart()： 表示 Activity 正在被启动，此时 Activity 可见但不
     * 在前台，还处于后台，无法与用户交互
     */
    override fun onStart() {
        super.onStart()
        Timber.d("onCreate: onStart")
    }

    /**
     * onResume()： 表示 Activity 获得焦点，此时 Activity 可见且在
     * 前台并开始活动，这是与 onStart 的区别所在
     */
    override fun onResume() {
        super.onResume()
        Timber.d("onCreate: onResume")
    }

    /**
     * onPause()： 表示 Activity 正在停止，此时可做一些 存储数据、
     * 停止动画等工作，但是不能太耗时，因为这会影响到新 Activity
     * 的显示，onPause 必须先执行完，新 Activity 的 onResume 才会
     * 执行
     */
    override fun onPause() {
        super.onPause()
        Timber.d("onCreate: onPause")
    }

    /**
     * onStop()： 表示 Activity 即将停止，可以做一些稍微重量级的回
     * 收工作，比如注销广播接收器、关闭网络连接等，同样不能太耗
     * 时
     */
    override fun onStop() {
        super.onStop()
        Timber.d("onCreate: onStop")
    }

    /**
     * 表示 Activity 即将被销毁，这是 Activity 生命周
     * 期中的最后一个回调，常做 回收工作、资源释放
     */
    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onCreate: onDestroy")
    }

}