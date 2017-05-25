package com.sanjie.captivate.view

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.support.annotation.AttrRes
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/**
 * Created by LangSanJie on 2017/4/21.
 */
class SoftResizeFrameLayout : FrameLayout {

    private var mListener: KeyboardListener? = null
    private var navBarHeight: Int = 0

    interface KeyboardListener {
        fun onKeyboardShow(keyboardHeight: Int)
        fun onKeyboardHidden()
    }

    constructor(context: Context) : super(context) {
        navBarHeight = getNavBarHeight()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        navBarHeight = getNavBarHeight()
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        navBarHeight = getNavBarHeight()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setKeyboardListener(listener: KeyboardListener) {
        mListener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val proposedHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        val actualHeight = height

        if (actualHeight - proposedHeight > navBarHeight) {
            // 软键盘弹出（非导航栏弹出）
            val keyboardHeight = actualHeight - proposedHeight
            notifyKeyboardEvent(true, keyboardHeight)
        } else if (proposedHeight - actualHeight > navBarHeight) {
            // 软键盘隐藏（非导航栏隐藏）
            notifyKeyboardEvent(false, 0)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun notifyKeyboardEvent(show: Boolean, keyboardHeight: Int) {
        if (mListener == null) {
            return
        }
        if (show) {
            mListener!!.onKeyboardShow(keyboardHeight)
        } else {
            mListener!!.onKeyboardHidden()
        }
    }

    private fun getNavBarHeight(): Int {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return resources.getDimensionPixelOffset(resourceId)
        }
        return 0
    }
}