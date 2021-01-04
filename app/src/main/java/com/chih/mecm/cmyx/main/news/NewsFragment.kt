package com.chih.mecm.cmyx.main.news

import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.ToastUtils
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.fragment.BaseDecorViewFragment
import com.chih.mecm.cmyx.base.fragment.SimpleDecorViewFragment
import com.chih.mecm.cmyx.bean.result.NewsChatResult
import com.chih.mecm.cmyx.utils.XavierViewUtils

class NewsFragment : BaseDecorViewFragment<NewsContract.Presenter<NewsContract.View>>(),
    NewsContract.View {

    private var page=1

    override fun contentLayoutRes() = R.layout.fragment_news

    override fun showChatList(t: NewsChatResult) {

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
}