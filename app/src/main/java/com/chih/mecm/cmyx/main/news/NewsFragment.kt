package com.chih.mecm.cmyx.main.news

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chih.mecm.cmyx.EMPTY
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.app.APixelActivity
import com.chih.mecm.cmyx.app.AppManager
import com.chih.mecm.cmyx.base.fragment.BaseDecorViewFragment
import com.chih.mecm.cmyx.bean.result.NewsChatDataListItem
import com.chih.mecm.cmyx.bean.result.NewsChatResult
import com.chih.mecm.cmyx.extend.toast
import com.chih.mecm.cmyx.utils.GlideEngine
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils
import com.chih.mecm.cmyx.utils.XavierViewUtils
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_multip_news.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.multip_empty_view.*

class NewsFragment : BaseDecorViewFragment<NewsContract.Presenter<NewsContract.View>>(),
    NewsContract.View {

    private var page = 1

    override fun contentLayoutRes() = R.layout.fragment_multip_news

    private var total = 0
    private val list = mutableListOf<NewsChatDataListItem>()
    private var adapter: NewsAdapter? = null

    private val onRefreshListener = OnRefreshListener {
        page = 1
        list.clear()
        presenter?.chatList(page)
    }

    override fun ui() {
        presenter?.chatList(page)
        multipleStatusLayout.showLoading()
        recyclerView.background =
            MaterialShapeDrawableUtils.getRoundedShapeDrawable(4f, R.color.white)
        refreshLayout.setOnRefreshListener(onRefreshListener)
        refreshLayout.setOnLoadMoreListener {
            if (total > list.size) {
                page++
            }
            presenter?.chatList(page)
        }
    }

    override fun showChatList(t: NewsChatResult) {
        multipleStatusLayout.showContent()
        XavierViewUtils.finishRefreshLayoutAnim(emptyRefreshLayout)
        XavierViewUtils.finishRefreshLayoutAnim(refreshLayout)
        val dataList = t.dataList
        total = t.total
        if (!dataList.isNullOrEmpty()) {
            list.addAll(dataList)
        }
        if (list.isEmpty()) {
            showMessage(EMPTY)
        }
        if (adapter == null) {
            adapter = NewsAdapter(list)
            adapter?.addHeaderView(XavierViewUtils.getDivide12View(context))
            adapter?.addFooterView(XavierViewUtils.getDivide12View(context))
            recyclerView.adapter = adapter
        } else {
            adapter?.notifyDataSetChanged()
        }

    }

    override fun showMessage(message: String?) {
        multipleStatusLayout.showEmpty()
        message.toast()
        XavierViewUtils.finishRefreshLayoutAnim(emptyRefreshLayout)
        XavierViewUtils.finishRefreshLayoutAnim(refreshLayout)
        emptyImageView.setImageResource(R.drawable.empty_news)
        emptyTextView.text = "亲~暂时还没有消息呦~"
        toLogin.visibility = if (AppManager.isLogin()) View.GONE else View.VISIBLE
        toLogin.background =
            MaterialShapeDrawableUtils.getRoundedShapeDrawable(18f, R.color.grey_900_alpha_100)
        emptyRefreshLayout.setOnRefreshListener(onRefreshListener)
    }

    override fun onPrepare(prepareView: View?) {
        prepareView?.let {
            XavierViewUtils.setGone(
                prepareView.findViewById<ImageView>(R.id.xavierReturnPager),
                prepareView.findViewById<ImageView>(R.id.xavierNavMenu)
            )
        }
        setDefaultTitle("消息")
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }

    override fun createPresenter(): NewsContract.Presenter<NewsContract.View> {
        return NewsPresenter(this)
    }

    inner class NewsAdapter(data: MutableList<NewsChatDataListItem>?) :
        BaseQuickAdapter<NewsChatDataListItem, BaseViewHolder>(R.layout.recycle_item_news, data) {
        override fun convert(holder: BaseViewHolder, item: NewsChatDataListItem) {
            val position = holder.layoutPosition
            GlideEngine.loadHeadPortrait(context, item.mAvatar, holder.getView(R.id.headPortrait))
            holder.setText(R.id.newsName, item.mName)
            val constraintLayout = holder.getView<ConstraintLayout>(R.id.constraintLayout)
            constraintLayout.setOnClickListener {
                val aPixelIntent =
                    Intent(context, APixelActivity::class.java)
                aPixelIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(aPixelIntent)
                "Called".toast()
            }
        }

    }

}