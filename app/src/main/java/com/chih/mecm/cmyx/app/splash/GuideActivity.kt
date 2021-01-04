package com.chih.mecm.cmyx.app.splash

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chih.mecm.cmyx.MainActivity
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.app.AppManager
import com.chih.mecm.cmyx.base.activity.SimpleActivity
import com.chih.mecm.cmyx.bean.entity.GuideEntity
import com.chih.mecm.cmyx.extend.dp
import com.chih.mecm.cmyx.extend.value
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : SimpleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
    }

    override fun ui() {
        // 用户协议
        AppManager.userAgreement(this)

        val guides = mutableListOf<GuideEntity>(
            GuideEntity(
                R.drawable.guide_p0_top,
                R.drawable.guide_p0_center,
                R.drawable.guide_p0_bottom
            ),
            GuideEntity(
                R.drawable.guide_p1_top,
                R.drawable.guide_p1_center,
                R.drawable.guide_p1_bottom
            ),
            GuideEntity(
                R.drawable.guide_p2_top,
                R.drawable.guide_p2_center,
                R.drawable.guide_p2_bottom
            )
        )

        val pagerAdapter = GuidePagerAdapter(guides)
        viewPager.adapter = pagerAdapter

        pagerAdapter.setOnItemChildClickListener { _, _, _ ->
            intent(MainActivity::class.java)
            finish()
        }
    }

    class GuidePagerAdapter(data: MutableList<GuideEntity>) :
        BaseQuickAdapter<GuideEntity, BaseViewHolder>(R.layout.page_item_guide, data) {
        init {
            addChildClickViewIds(R.id.experienceNow)
        }

        override fun convert(holder: BaseViewHolder, item: GuideEntity) {
            val layoutPosition = holder.layoutPosition
            holder.setBackgroundResource(R.id.topView, item.topImage)
                .setBackgroundResource(R.id.centerView, item.centerImage)
                .setBackgroundResource(R.id.bottomView, item.bottomImage)
            val experienceNow = holder.getView<Button>(R.id.experienceNow)
            experienceNow.background =
                MaterialShapeDrawableUtils.getRoundedShapeDrawable(4f, R.color.grey_900_alpha_100)
            experienceNow.visibility =
                if (layoutPosition == data.lastIndex) View.VISIBLE else View.GONE
        }
    }
}

