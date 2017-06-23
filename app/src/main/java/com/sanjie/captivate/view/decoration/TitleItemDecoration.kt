package com.sanjie.captivate.view.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import com.sanjie.zy.utils.ZYDisplayUtils

/**
 * Created by SanJie on 2017/6/20.
 */
class TitleItemDecoration : RecyclerView.ItemDecoration {


    private var mDatas: ArrayList<com.sanjie.captivate.mvp.model.City>? = null
    private var mPaint: Paint? = null
    private var mBounds: Rect? = null//用于存放测量文字Rect

    private var mTitleHeight: Int? = null// title的高度

    companion object {
        val COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF")
        val COLOR_TITLE_FONT = Color.parseColor("#FF000000")
        var mTitleFontSize: Int = 0
    }

    constructor(context: Context, datas: ArrayList<com.sanjie.captivate.mvp.model.City>) : super() {
        this.mDatas = datas
        this.mPaint = Paint()
        this.mBounds = Rect()
        mTitleHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30F, context.resources.displayMetrics).toInt()
        mTitleFontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16F, context.resources.displayMetrics).toInt()

        mPaint!!.textSize = mTitleFontSize.toFloat()
        mPaint!!.isAntiAlias = true

    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
        val left = parent!!.left
        val right = parent.width - parent.paddingRight
        val count = parent.childCount

        for (i in 0..count - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val position = params.viewLayoutPosition
            /*
            为了保险起见
            判断item position 是否不为-1
             */
            if (position > -1) {
                when (position) {
                //起始位置肯定有title
                    0 -> drawTitleArea(c, left, right, child, params, position)
                    else -> {
                        if (null != mDatas!![position].tag && mDatas!![position].tag!! != mDatas!![position - 1].tag) {
                            //不为空，且不跟前一个tag不一样了说明是新的分类，重新绘制title
                            drawTitleArea(c, left, right, child, params, position)
                        } else {
                            //none
                        }
                    }
                }
            }
        }
    }

    /**
     * 绘制title区域背景和文字的方法
     * 最先调用绘制在最下层
     */
    private fun drawTitleArea(c: Canvas?, left: Int, right: Int, child: View?, params: RecyclerView.LayoutParams?, position: Int) {
        //绘制背景
        mPaint!!.color = COLOR_TITLE_BG
        c!!.drawRect(left.toFloat(), child!!.top - params!!.topMargin - mTitleHeight!!.toFloat(), right.toFloat(), child.top - params.topMargin.toFloat(), mPaint)
        //绘制文字
        mPaint!!.color = COLOR_TITLE_FONT
        mPaint!!.getTextBounds(mDatas!![position].tag, 0, mDatas!![position].tag!!.length, mBounds)
        c.drawText(mDatas!![position].tag, child.paddingLeft.toFloat(), child.top - params.topMargin - (mTitleHeight!!.toFloat() / 2 - mBounds!!.height() / 2), mPaint)
    }

    /**
     * 最后调用，绘制在上层
     */
    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        val position = (parent!!.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        val tag = mDatas!![position].tag
        mPaint!!.color = COLOR_TITLE_BG
        c!!.drawRect(parent.paddingLeft.toFloat(), parent.paddingTop.toFloat(), parent.right - parent.paddingRight.toFloat(), parent.top + mTitleHeight!!.toFloat(), mPaint)
        mPaint!!.color = COLOR_TITLE_FONT
        mPaint!!.getTextBounds(tag!!, 0, tag.length, mBounds)
        c.drawText(tag, parent.paddingLeft.toFloat() + ZYDisplayUtils.dp2px(15F), parent.paddingTop + mTitleHeight!! - (mTitleHeight!! / 2 - mBounds!!.height() / 2).toFloat(), mPaint)
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = (view!!.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        /*
            为了保险起见
            判断item position 是否不为-1
             */
        if (position > -1) {
            when (position) {
            //起始位置肯定有title
                0 -> outRect!!.set(0, mTitleHeight!!, 0, 0)
                else -> {
                    if (null != mDatas!![position].tag && mDatas!![position].tag!! != mDatas!![position - 1].tag) {
                        //不为空，且不跟前一个tag不一样了说明是新的分类，重新绘制title
                        outRect!!.set(0, mTitleHeight!!, 0, 0)
                    } else {
                        //none
                    }
                }
            }
        }
    }
}