package com.chih.mecm.cmyx

import android.content.*
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chih.mecm.cmyx.app.AppManager
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.ACTION_CHAT_MESSAGE_RECEIVER
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.PARAM_CHAT_MESSAGE_RECEIVER
import com.chih.mecm.cmyx.app.broadcast.ChatMessageReceiver
import com.chih.mecm.cmyx.base.activity.SimpleActivity
import com.chih.mecm.cmyx.bean.entity.TabEntity
import com.chih.mecm.cmyx.bean.result.NewsChatResult
import com.chih.mecm.cmyx.http.client.RetrofitHelper
import com.chih.mecm.cmyx.http.observer.HttpDefaultObserver
import com.chih.mecm.cmyx.main.home.HomeFragment
import com.chih.mecm.cmyx.main.mine.MineFragment
import com.chih.mecm.cmyx.main.news.NewsFragment
import com.chih.mecm.cmyx.main.news.chat.ChatSocketService
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.*


class MainActivity : SimpleActivity() {

    private lateinit var tabs: List<String>
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

    private lateinit var service: ChatSocketService
    private var bound: Boolean = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, serviceBinder: IBinder?) {
            val binder = serviceBinder as ChatSocketService.ChatSocketBinder
            service = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }
    }

    private val receiver = object : ChatMessageReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val stringExtra = it.getStringExtra(PARAM_CHAT_MESSAGE_RECEIVER)
                Timber.i("MainActivity#ChatMessageReceiver#onReceive stringExtra = $stringExtra")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun ui() {
        // 用户协议
        AppManager.userAgreement(this)

        // 检查版本更新
        //AppManager.checkVersionUpdates(this)

        tabs = listOf<String>(
            getString(R.string.home_page),
            getString(R.string.news),
            getString(R.string.shopping),
            getString(R.string.mine)
        )
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
                navigator.currentTab = position
            }
        })

        // 启动服务
        val intent = Intent(this, ChatSocketService::class.java)
        startService(intent)
    }

    override fun onStart() {
        super.onStart()
        // 绑定服务
        Intent(this, ChatSocketService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

        // 绑定广播
        receiver.registerReceiver(this)
    }

    override fun onStop() {
        super.onStop()
        // 解绑服务
        unbindService(connection)
        bound = false

        // 解绑广播
        receiver.unregisterReceiver(this)
    }

    private inner class TabsPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
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
                    MineFragment.newInstance()
                }
                else -> {
                    TemporaryFragment.newInstance()
                }
            }
    }

}