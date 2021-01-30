package com.chih.mecm.cmyx.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.activity.SimpleWithBarActivity
import com.chih.mecm.cmyx.bean.entity.TabEntity
import com.chih.mecm.cmyx.order.fragment.OrderListFragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_order_list.*
import kotlinx.android.synthetic.main.xavier_toolbar_reture_search_menu_tabs.*

class OrderListActivity : SimpleWithBarActivity() {

    private val tabEntities = arrayListOf<CustomTabEntity>(
        TabEntity(tab = "全部", type = -1, subTitle = "全部订单", minor = "暂无订单"),
        TabEntity(tab = "待付款", type = 0, subTitle = "待付款", minor = "暂无待付款订单"),
        TabEntity(tab = "待发货", type = 1, subTitle = "待发货", minor = "暂无待发货订单"),
        TabEntity(tab = "待收货", type = 2, subTitle = "待收货", minor = "暂无待收货订单")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
    }

    override fun ui() {
        val pagerAdapter = OrderListPagerAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                xavierTabLayout?.currentTab = position
            }
        })
    }

    override fun toolbarLayoutRes(): Int {
        return R.layout.xavier_toolbar_reture_search_menu_tabs
    }

    override fun onPrepare(prepareView: View?) {
        super.onPrepare(prepareView)
        xavierTabLayout.setTabData(tabEntities)
        xavierTabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager?.currentItem = position
            }

            override fun onTabReselect(position: Int) {
            }
        })
    }

    inner class OrderListPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = tabEntities.size
        override fun createFragment(position: Int): Fragment =
            OrderListFragment.newInstance(tabEntities[position] as TabEntity)
    }

}