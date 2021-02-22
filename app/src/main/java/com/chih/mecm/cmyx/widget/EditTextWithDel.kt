package com.chih.mecm.cmyx.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import com.blankj.utilcode.util.ResourceUtils
import com.chih.mecm.cmyx.R
import timber.log.Timber

class EditTextWithDel : AppCompatEditText {

    private lateinit var imgInable: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        imgInable = ResourceUtils.getDrawable(R.mipmap.ic_cross)
        addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                setDrawable()
            }
        })
        setDrawable()
    }

    // 设置删除图片
    private fun setDrawable() {
        if (length() < 1) setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            null,
            null
        ) else setCompoundDrawablesWithIntrinsicBounds(null, null, imgInable, null)
    }

    // 处理删除事件
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            val eventX = event.rawX.toInt()
            val eventY = event.rawY.toInt()
            Timber.e("eventX = $eventX; eventY = $eventY")
            val rect = Rect()
            getGlobalVisibleRect(rect)
            rect.left = rect.right - 100
            if (rect.contains(eventX, eventY)) setText("")
        }
        return super.onTouchEvent(event)
    }

}