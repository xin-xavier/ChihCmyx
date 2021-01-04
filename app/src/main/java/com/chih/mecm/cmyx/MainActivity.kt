package com.chih.mecm.cmyx

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chih.mecm.cmyx.app.AppManager
import com.chih.mecm.cmyx.base.activity.SimpleActivity
import com.chih.mecm.cmyx.bean.EmptyBean
import com.chih.mecm.cmyx.bean.entity.TabEntity
import com.chih.mecm.cmyx.bean.result.NewsChatResult
import com.chih.mecm.cmyx.http.client.RetrofitHelper
import com.chih.mecm.cmyx.http.observer.HttpDefaultObserver
import com.chih.mecm.cmyx.main.home.HomeFragment
import com.chih.mecm.cmyx.main.news.NewsFragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : SimpleActivity() {

    private val tabs = listOf<String>("首页", "消息", "购物车", "我的")
    private val tabEntities = ArrayList<CustomTabEntity>()
    private val iconSelectIds: Array<Int> = arrayOf(
        R.drawable.tab_home_select,
        R.drawable.tab_news_select,
        R.drawable.tab_shopping_select,
        R.drawable.tab_mine_select
    )
    private val iconUnSelectIds: Array<Int> = arrayOf(
        R.drawable.tab_home_unselect,
        R.drawable.tab_news_unselect,
        R.drawable.tab_shopping_unselect,
        R.drawable.tab_mine_unselect
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun ui() {
        // 用户协议
        AppManager.userAgreement(this)

        // 检查版本更新
        //AppManager.checkVersionUpdates(this)

        for (tab in tabs) {
            val indexOf = tabs.indexOf(tab)
            tabEntities.add(
                TabEntity(
                    tab,
                    iconSelectIds[indexOf],
                    iconUnSelectIds[indexOf]
                )
            )
        }

        val pagerAdapter = TabsPagerAdapter(this)
        carousel.adapter = pagerAdapter
        carousel.isUserInputEnabled = false
        navigator.setTabData(tabEntities)
        navigator.setOnTabSelectListener(object : OnTabSelectListener {
            // 点击时运行
            override fun onTabSelect(position: Int) {
                carousel.currentItem = position
            }

            // 选项卡选中时运行
            override fun onTabReselect(position: Int) {}
        })
        // viewpager2
        carousel.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position in 2..3) {
                    //floatingActionButton.visibility = View.INVISIBLE
                }
                //pageItem = position
                navigator.currentTab = position
            }
        })

        // 接口测试
        interfaceTest()
    }

    private fun interfaceTest() {
        RetrofitHelper.apiServer
            .chatList(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<NewsChatResult>() {
                override fun disposable(d: Disposable) {

                }

                override fun onSuccess(t: NewsChatResult) {

                }

                override fun onError(errorMessage: String) {

                }

            })
    }

    private inner class TabsPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = tabs.size
        override fun createFragment(position: Int): Fragment =
            when (position) {
                0 -> {
                    HomeFragment.newInstance()
                }
                1 -> {
                    NewsFragment.newInstance()
                }
                2 -> {
                    TemporaryFragment.newInstance()
                }
                3 -> {
                    TemporaryFragment.newInstance()
                }
                else -> {
                    TemporaryFragment.newInstance()
                }
            }
    }

}