package com.chih.mecm.cmyx.main.news

import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.fragment.BaseDecorViewFragment
import com.chih.mecm.cmyx.base.fragment.SimpleDecorViewFragment
import com.chih.mecm.cmyx.bean.result.NewsChatDataListItem
import com.chih.mecm.cmyx.bean.result.NewsChatResult
import com.chih.mecm.cmyx.utils.XavierViewUtils
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : BaseDecorViewFragment<NewsContract.Presenter<NewsContract.View>>(),
    NewsContract.View {

    private var page = 1

    override fun contentLayoutRes() = R.layout.fragment_multip_news

    override fun showChatList(t: NewsChatResult) {
        val dataList = t.dataList
        recyclerView.adapter=NewsAdapter(dataList?.toMutableList())
    }

    override fun showMessage(message: String?) {
        ToastUtils.showShort(message)
    }

    override fun ui() {
        presenter?.chatList(page)
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

    override fun createPresenter(): NewsContract.Presenter<NewsContract.View>? {
        return NewsPresenter(this)
    }

    inner class NewsAdapter(data: MutableList<NewsChatDataListItem>?) :
        BaseQuickAdapter<NewsChatDataListItem, BaseViewHolder>(R.layout.recycle_item_news, data) {
        override fun convert(holder: BaseViewHolder, item: NewsChatDataListItem) {
            holder.setText(R.id.newsName, item.mname)
        }

    }

}