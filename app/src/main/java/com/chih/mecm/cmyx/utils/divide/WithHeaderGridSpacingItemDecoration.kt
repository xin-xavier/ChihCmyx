package com.chih.mecm.cmyx.utils.divide

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chih.mecm.cmyx.utils.extend.dp

class WithHeaderGridSpacingItemDecoration(
    private val spanCount: Int = 2, // 列数
    divide: Float = 12f, // 间隔
    private val includeEdge: Boolean = true, // 是否包含边缘
    private val headerCount: Int = 0
) : RecyclerView.ItemDecoration() {

    private val spacing: Int = divide.dp().toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // 这里是关键，需要根据你有几列来判断
        val position = parent.getChildAdapterPosition(view) // item position
        if (position < headerCount) return
        val column = position % spanCount // item column
        if (includeEdge) {
            outRect.left =
                spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right =
                (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        } else {
            outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right =
                spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }

}