package com.chih.mecm.cmyx.main.news

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.app.APixelActivity
import com.chih.mecm.cmyx.app.AppManager
import com.chih.mecm.cmyx.base.fragment.BaseDecorViewFragment
import com.chih.mecm.cmyx.bean.result.NewsChatDataListItem
import com.chih.mecm.cmyx.bean.result.NewsChatResult
import com.chih.mecm.cmyx.extend.toast
import com.chih.mecm.cmyx.utils.GlideEngine
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils
import com.chih.mecm.cmyx.utils.XavierTimeUtils
import com.chih.mecm.cmyx.utils.XavierViewUtils
import com.classic.common.MultipleStatusView.STATUS_CONTENT
import com.classic.common.MultipleStatusView.STATUS_EMPTY
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_multip_news.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.multip_with_refresh_default_empty_view.*

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
        multipleStatusLayout.showLoading()
        presenter?.chatList(page)
        recyclerView.background =
            MaterialShapeDrawableUtils.getShapeDrawable(4f, R.color.white)
        refreshLayout.setOnRefreshListener(onRefreshListener)
        refreshLayout.setOnLoadMoreListener {
            if (total > list.size) {
                page++
                presenter?.chatList(page)
            } else {
                refreshLayout.finishLoadMore(1500, true, true)
            }
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
        if (list.isEmpty() && multipleStatusLayout.viewStatus != STATUS_EMPTY) {
            multipleStatusLayout.showEmpty()
        } else if (list.isNotEmpty() && multipleStatusLayout.viewStatus != STATUS_CONTENT) {
            multipleStatusLayout.showContent()
        }
        multipleStatusLayout.viewStatus
        multipleStatusLayout.showEmpty()
        XavierViewUtils.finishRefreshLayoutAnim(emptyRefreshLayout)
        XavierViewUtils.finishRefreshLayoutAnim(refreshLayout)
        message.toast()
        emptyImageView.setImageResource(R.drawable.empty_news)
        emptyTextView.text = "亲~暂时还没有消息呦~"
        toLogin.visibility = if (AppManager.isLogin()) View.GONE else View.VISIBLE
        toLogin.background =
            MaterialShapeDrawableUtils.getShapeDrawable(18f, R.color.grey_900_alpha_100)
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
            val type = item.type
            val official = type != 1
            val unread = item.unread
            GlideEngine.loadHeadPortrait(context, item.mAvatar, holder.getView(R.id.headPortrait))
            holder.setText(R.id.newsName, item.mName)
                .setText(R.id.lastTime, XavierTimeUtils.getTimeSegmentation(item.sendTime))
                .setVisible(R.id.officialLabel, official)
                .setVisible(R.id.unreadNumber, unread > 0)
                .setText(R.id.lastNews, item.lastMessage)
                .setText(R.id.unreadNumber, unread.toString())
            if(official){
                val officialLabel = holder.getView<TextView>(R.id.officialLabel)
                officialLabel.background =
                    MaterialShapeDrawableUtils.strokeShapeDrawable(4f, R.color.main_orange)
            }
            val constraintLayout = holder.getView<ConstraintLayout>(R.id.constraintLayout)
            constraintLayout.setOnClickListener {
                val aPixelIntent =
                    Intent(context, APixelActivity::class.java)
                aPixelIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(aPixelIntent)
                item.mName.toast()
            }
        }
    }

}