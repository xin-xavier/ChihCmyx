package com.chih.mecm.cmyx.main.home.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.adapter.banner.BannerImageAdapter
import com.chih.mecm.cmyx.adapter.pager.CommodityAdapter
import com.chih.mecm.cmyx.bean.result.*
import com.chih.mecm.cmyx.extend.setGone
import com.chih.mecm.cmyx.extend.setVisible
import com.chih.mecm.cmyx.extend.toast
import com.chih.mecm.cmyx.order.OrderListActivity
import com.chih.mecm.cmyx.utils.GlideEngine
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils
import com.chih.mecm.cmyx.utils.XavierViewUtils
import com.chih.mecm.cmyx.utils.divide.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_top.*
import timber.log.Timber

class TopFragment : TopContainerFragment() {

    private val adListContainer = MutableList<HomeAdResItem>(4) { HomeAdResItem() }
    private val homeAdImageList = mutableListOf<HomeAdImageEntity>()

    private val rowsList = mutableListOf<CommodityItem>()
    private var total = 0
    private var hotAdapter: CommodityAdapter? = null

    override fun createPresenter(): TopContract.Presenter<TopContract.View> {
        return TopPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Timber.i("param = $type --- $pid --- $pagePosition")
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun ui() {
        super.ui()
        banner.setBannerGalleryMZ(20)
    }

    override fun showSlideShow(slideShowResult: MutableList<BannerItem>) {
        banner.setVisible()
        val bannerAdapter = BannerImageAdapter(slideShowResult)
        banner.adapter = bannerAdapter
    }

    override fun errorSlideShow(message: String) {
        banner.setGone()
    }

    override fun showHomeAd(adResult: HomeAdResult) {
        val res = adResult.res
        if (res.isNullOrEmpty()) {
            homeAdConstraintLayout.setGone()
        } else {
            homeAdConstraintLayout.setVisible()
            adListContainer.clear()
            adListContainer.addAll(res)
        }
        for (index in adListContainer.indices) {
            val homeAdResItem = adListContainer[index]
            when (index) {
                0 -> {
                    homeAdImageList.add(HomeAdImageEntity(imageViewOne, homeAdResItem))
                }
                1 -> {
                    homeAdImageList.add(HomeAdImageEntity(imageViewTwo, homeAdResItem))
                }
                2 -> {
                    homeAdImageList.add(HomeAdImageEntity(imageViewThree, homeAdResItem))
                }
                3 -> {
                    homeAdImageList.add(HomeAdImageEntity(imageViewFour, homeAdResItem))
                }
                else -> {
                }
            }
        }
        for (index in homeAdImageList.lastIndex downTo 0) {
            val homeAdImageEntity = homeAdImageList[index]
            setAdImage(homeAdImageEntity)
        }
        val endRes = adResult.endRes
        if (endRes == null) {
            imageViewFive.setGone()
        } else {
            setAdImage(HomeAdImageEntity(imageViewFive, endRes))
        }
    }

    private fun setAdImage(homeAdImageEntity: HomeAdImageEntity) {
        val homeAdResult = homeAdImageEntity.homeAdResult
        val imageView = homeAdImageEntity.imageView
        val image = homeAdResult.image
        if (image.isEmpty()) {
            imageView.setGone()
        } else {
            imageView.setVisible()
            GlideEngine.loadWithCircleCorner(context, image, imageView)
            imageView.setOnClickListener {
                homeAdResult.url.toast()
            }
        }
    }

    override fun errorHomeAd(message: String) {
        homeAdConstraintLayout.setGone()
    }

    override fun showSubClazz(subClazzList: List<SubClazzResult>) {
        subClazzRecycle.setVisible()
        val roundedShapeDrawable =
            MaterialShapeDrawableUtils.getShapeDrawable(4f, R.color.white)
        subClazzRecycle.background = roundedShapeDrawable
        subClazzRecycle.adapter = SubClazzAdapter(subClazzList.toMutableList())
    }

    override fun errorSubClazz(message: String) {
        subClazzRecycle.setGone()
    }

    override fun showHomeChoiceShop(choiceLists: List<List<HomeChoiceShopListItem>>) {
        homeChoiceShopRecycle.setVisible()
        homeChoiceShopRecycle.adapter = HomeChoiceShopAdapter(choiceLists.toMutableList())
    }

    override fun errorHomeChoiceShop(message: String) {
        homeChoiceShopRecycle.setGone()
    }

    override fun showHot(hotResult: CommodityResult) {
        XavierViewUtils.finishRefreshLayoutAnim(topRefreshLayout)
        labelViewInclude.setVisible()
        hotRecycle.setVisible()
        topRefreshLayout.setOnLoadMoreListener {
            if (total > rowsList.size) {
                page++
                if (pagePosition == 0) {
                    presenter?.hot(page)
                } else {
                    map["page"] = page
                    presenter?.subGoods(map)
                }
            } else {
                refreshLayout.finishLoadMore(1500, true, true)
            }
        }
        total = hotResult.total
        rowsList.addAll(hotResult.rows!!)
        if (hotAdapter == null) {
            labelViewInclude.findViewById<View>(R.id.labelView).background =
                MaterialShapeDrawableUtils.getShapeDrawable(2f, R.color.main_orange)
            labelViewInclude.findViewById<TextView>(R.id.labelText).text =
                if (pagePosition == 0) "热门推荐" else "猜你喜欢"
            // 解决 NestedScrollView 嵌套 RecyclerView 滑动卡顿
            hotRecycle.isNestedScrollingEnabled = false
            if (hotRecycle.itemDecorationCount == 0) {
                hotRecycle.addItemDecoration(GridSpacingItemDecoration())
            }
            hotAdapter = CommodityAdapter(rowsList)
            hotRecycle.adapter = hotAdapter
            hotAdapter?.setOnItemClickListener { _, _, _ ->
                intent(OrderListActivity::class.java)
            }
        } else {
            hotAdapter?.notifyDataSetChanged()
        }

    }

    override fun errorHot(errorMsg: String) {
        XavierViewUtils.finishRefreshLayoutAnim(topRefreshLayout)
        if (rowsList.isEmpty()) {
            labelViewInclude.setGone()
            hotRecycle.setGone()
            errorMsg.toast()
        }
    }

    override fun showMessage(message: String?) {}

    class HomeChoiceShopAdapter(data: MutableList<List<HomeChoiceShopListItem>>) :
        BaseQuickAdapter<List<HomeChoiceShopListItem>, BaseViewHolder>(
            R.layout.recycle_item_home_choice_shop,
            data
        ) {
        override fun convert(holder: BaseViewHolder, item: List<HomeChoiceShopListItem>) {
            holder.getView<View>(R.id.labelView).background =
                MaterialShapeDrawableUtils.getShapeDrawable(2f, R.color.main_orange)
            holder.setText(R.id.labelText, "精选好店")
            val recyclerView = holder.getView<RecyclerView>(R.id.recyclerView)
            recyclerView.adapter = HomeChoiceShopListItemAdapter(item.toMutableList())
        }
    }

    class HomeChoiceShopListItemAdapter(
        data: MutableList<HomeChoiceShopListItem>?
    ) : BaseQuickAdapter<HomeChoiceShopListItem, BaseViewHolder>(
        R.layout.recycle_item_home_choice_shop_list_item,
        data
    ) {
        override fun convert(holder: BaseViewHolder, item: HomeChoiceShopListItem) {
            GlideEngine.assignResIdCircleCorner(
                context,
                item.headImage,
                holder.getView(R.id.imageView),
                R.drawable.default_no_image
            )
            holder.setText(R.id.shopName, item.storeName)
        }
    }

    class SubClazzAdapter(data: MutableList<SubClazzResult>?) :
        BaseQuickAdapter<SubClazzResult, BaseViewHolder>(R.layout.recycle_item_sub_clazz, data) {
        override fun convert(holder: BaseViewHolder, item: SubClazzResult) {
            GlideEngine.assignResIdCircleCorner(
                context,
                item.image,
                holder.getView(R.id.imageView),
                R.drawable.default_no_image
            )
            holder.setText(R.id.textView, item.name)
        }
    }

}