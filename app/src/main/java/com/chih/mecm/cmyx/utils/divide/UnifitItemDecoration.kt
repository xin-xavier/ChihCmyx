package com.chih.mecm.cmyx.utils.divide

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chih.mecm.cmyx.extend.dp

class UnifitItemDecoration(
    divide: Float = 12f,
    @RecyclerView.Orientation val orientation: Int = RecyclerView.VERTICAL
) : RecyclerView.ItemDecoration() {
    private val spacing: Int = divide.dp().toInt()
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        //super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view) // item position
        val adapter = parent.adapter
        adapter ?: return
        val lastIndex = adapter.itemCount - 1
        if (orientation == RecyclerView.VERTICAL) {
            if (position == 0) {
                outRect.top = spacing
                outRect.bottom = spacing / 2
            }
            if (position in 1 until lastIndex) {
                outRect.top = spacing / 2
                outRect.bottom = spacing / 2
            }
            if (position == lastIndex) {
                outRect.top = spacing / 2
                outRect.bottom = spacing
            }
        }
    }

}