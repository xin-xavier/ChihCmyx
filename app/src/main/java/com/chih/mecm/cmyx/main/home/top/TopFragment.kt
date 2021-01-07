package com.chih.mecm.cmyx.main.home.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.adapter.banner.BannerImageAdapter
import com.chih.mecm.cmyx.bean.entity.HomeAdImageEntity
import com.chih.mecm.cmyx.bean.result.HomeAdResult
import com.chih.mecm.cmyx.bean.result.HomeChoiceShopListItem
import com.chih.mecm.cmyx.bean.result.SlideShowResult
import com.chih.mecm.cmyx.bean.result.SubClazzResult
import com.chih.mecm.cmyx.extend.toast
import com.chih.mecm.cmyx.utils.GlideEngine
import com.xavier.simple.demo.bean.result.HotResult
import kotlinx.android.synthetic.main.fragment_top.*
import kotlinx.android.synthetic.main.home_ad_layout.*

private const val TYPE = "type"
private const val PID = "pid"

class TopFragment : TopContainerFragment() {

    private val adListContainer = MutableList<HomeAdResult>(5) { HomeAdResult() }
    private val homeAdImageList = mutableListOf<HomeAdImageEntity>()

    override fun createPresenter(): TopContract.Presenter<TopContract.View> {
        return TopPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun showSlideShow(slideShowResult: List<SlideShowResult>) {
        val bannerAdapter = BannerImageAdapter(slideShowResult)
        banner.adapter = bannerAdapter
        banner.setBannerGalleryMZ(20)
    }

    override fun errorSlideShow(message: String) {
        banner.visibility = View.GONE
    }

    override fun showHomeAd(adList: List<HomeAdResult>) {
        "showHomeAd".toast()
        adListContainer.clear()
        adListContainer.addAll(adList)
        val size = adListContainer.size
        if (size == 0) {
            adConstraintLayout.visibility = View.VISIBLE
            return
        }
        for (index in adListContainer.indices) {
            val homeAdResult = adListContainer[index]
            when (index) {
                0 -> {
                    homeAdImageList.add(HomeAdImageEntity(imageView1, homeAdResult))
                }
                1 -> {
                    homeAdImageList.add(HomeAdImageEntity(imageView2, homeAdResult))
                }
                2 -> {
                    homeAdImageList.add(HomeAdImageEntity(imageView3, homeAdResult))
                }
                3 -> {
                    homeAdImageList.add(HomeAdImageEntity(imageView4, homeAdResult))
                }
                4 -> {
                    homeAdImageList.add(HomeAdImageEntity(imageView5, homeAdResult))
                }
                else -> {
                }
            }
        }
        for (homeAdImageEntity in homeAdImageList) {
            val homeAdResult = homeAdImageEntity.homeAdResult
            val imageView = homeAdImageEntity.imageView
            val image = homeAdResult.image
            if(image.isEmpty()){
                imageView.visibility=View.GONE
            }else{
                imageView.visibility=View.VISIBLE
                GlideEngine.assignResIdCircleCorner(context,homeAdResult.image,imageView)
                imageView.setOnClickListener {
                    homeAdResult.url.toast()
                }
            }
        }
    }

    override fun errorHomeAd(message: String) {

    }

    override fun showSubClazz(subclassList: List<SubClazzResult>) {

    }

    override fun errorSubClazz(message: String) {

    }

    override fun showHomeChoiceShop(choiceLists: List<List<HomeChoiceShopListItem>>) {

    }

    override fun errorHomeChoiceShop(message: String) {

    }

    override fun showHot(hotResult: HotResult) {

    }

    override fun errorHot(errorMsg: String) {

    }

    override fun showMessage(message: String?) {

    }


}