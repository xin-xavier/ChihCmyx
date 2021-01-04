package com.chih.mecm.cmyx.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ToastUtils
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.BIG_SCALE
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.LITTLE_SCALE
import com.chih.mecm.cmyx.base.fragment.BaseWithBarFragment
import com.chih.mecm.cmyx.bean.result.TopClazzResult
import com.chih.mecm.cmyx.main.home.top.TopFragment
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.home_searchbox_btn.*
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import timber.log.Timber

class HomeFragment : BaseWithBarFragment<HomeContract.Presenter<HomeContract.View>>(),
    HomeContract.View {

    override fun ui() {
        presenter?.topClazz()
    }

    override fun toolbarLayoutRes(): Int {
        return R.layout.home_searchbox_btn
    }

    override fun contentLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun createPresenter(): HomeContract.Presenter<HomeContract.View> {
        return HomePresenter(this)
    }

    override fun onPrepare(prepareView: View?) {
        super.onPrepare(prepareView)
        presenter?.getDefaultSearch()
        rootView?.findViewById<ImageView>(R.id.xavierAppbarBg)?.setImageResource(R.color.initial)
        searchBoxView.background =
            MaterialShapeDrawableUtils.getRoundedShapeDrawable(2f, R.color.white)
    }

    override fun showTopClazz(list: List<TopClazzResult>) {
        Timber.i(list.toString())
        val pagerAdapter = TopPagerAdapter(this, list)
        viewPager.adapter = pagerAdapter
        val commonNavigator = CommonNavigator(context)
        commonNavigator.isAdjustMode = false
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            @SuppressLint("InflateParams")
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val commonPagerTitleView = CommonPagerTitleView(context)
                val customLayout: View =
                    layoutInflater.inflate(R.layout.navigator_home_top_item, null)
                val title = customLayout.findViewById<TextView>(R.id.title)
                val indicator = customLayout.findViewById<ImageView>(R.id.indicator)
                title.text = list[index].name
                commonPagerTitleView.setContentView(customLayout)
                commonPagerTitleView.onPagerTitleChangeListener =
                    object : CommonPagerTitleView.OnPagerTitleChangeListener {

                        override fun onSelected(index: Int, totalCount: Int) {
                            title.setTextAppearance(
                                context,
                                R.style.SkinCompatTextAppearance_titleSelectColor
                            )
                            indicator.visibility = View.VISIBLE
                        }

                        override fun onDeselected(index: Int, totalCount: Int) {
                            title.setTextAppearance(
                                context,
                                R.style.SkinCompatTextAppearance_titleUnSelectColor
                            )
                            indicator.visibility = View.GONE
                        }

                        override fun onLeave(
                            index: Int,
                            totalCount: Int,
                            leavePercent: Float,
                            leftToRight: Boolean
                        ) {
                            title.scaleX = BIG_SCALE + (LITTLE_SCALE - BIG_SCALE) * leavePercent
                            title.scaleY = BIG_SCALE + (LITTLE_SCALE - BIG_SCALE) * leavePercent
                        }

                        override fun onEnter(
                            index: Int,
                            totalCount: Int,
                            enterPercent: Float,
                            leftToRight: Boolean
                        ) {
                            title.scaleX = LITTLE_SCALE + (BIG_SCALE - LITTLE_SCALE) * enterPercent
                            title.scaleY = LITTLE_SCALE + (BIG_SCALE - LITTLE_SCALE) * enterPercent
                        }
                    }

                commonPagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }

                return commonPagerTitleView
            }

            override fun getCount(): Int {
                return list.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator? {
                return null
            }

        }
        magicIndicator.navigator = commonNavigator
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                magicIndicator.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                magicIndicator.onPageSelected(position)
            }
        })
    }

    override fun showDefaultSearch(keyword: String) {
        defaultSearch.hint=keyword
    }

    override fun showMessage(message: String?) {
        ToastUtils.showShort(message)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    private inner class TopPagerAdapter(
        fragmentActivity: HomeFragment,
        var list: List<TopClazzResult>
    ) : FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): TopFragment =
            TopFragment.newInstance(list.get(position).id.toString(), position.toString())

        override fun getItemCount(): Int = list.size
    }

}