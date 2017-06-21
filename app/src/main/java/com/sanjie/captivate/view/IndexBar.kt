package com.sanjie.captivate.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import com.sanjie.captivate.R
import com.sanjie.captivate.mvp.model.City
import com.sanjie.captivate.util.LogUtils
import com.sanjie.captivate.view.bean.BaseIndexPinYinBean
import io.reactivex.rxkotlin.toObservable
import java.util.*
import kotlin.collections.ArrayList

/**
 * 右侧索引栏
 * Created by SanJie on 2017/6/20.
 */
class IndexBar : View {

    companion object {
        val TAG = "IndexBar"

        val INDEX_TAG = arrayListOf("A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", "#")
    }

    private var mIndexDatas: ArrayList<String>? = null
    /*
    是否需要根据实际数据生成索引数据源
    例如 只有A B C
     */
    private var isNeedRealIndex: Boolean = false

    //view的宽高
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    //每个index区域的高度
    private var mGapHeight: Int = 0

    private var mPaint: Paint? = null

    private var mPressedBackground: Int = 0//手指按下时的颜色

    //以下变量外部set
    private var mPressedShowTextView: AppCompatTextView? = null//用于特写显示正在被触摸的index值
    private var mSourceDatas: ArrayList<City>? = null//Adapter的数据源
    private var mLayoutManager: LinearLayoutManager? = null

    private var indexPressedListener: OnIndexPressedListener? = null

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) {
        var textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16F, resources.displayMetrics)//默认textSize
        var textColor = Color.BLACK
        mPressedBackground = Color.BLACK//默认按下黑色
        val ta = context!!.theme.obtainStyledAttributes(attrs, R.styleable.IndexBar, defStyleAttr, 0)
        val count = ta.indexCount
        (0..count - 1)
                .map { ta.getIndex(it) }
                .forEach {
                    when (it) {
                        R.styleable.IndexBar_textSize -> textSize = ta.getDimensionPixelSize(it, textSize.toInt()).toFloat()
                        R.styleable.IndexBar_textColor -> textColor = ta.getColor(it, textColor)
                        R.styleable.IndexBar_pressBackground -> mPressedBackground = ta.getColor(it, mPressedBackground)
                    }
                }
        ta.recycle()

        if (!isNeedRealIndex) {
            mIndexDatas = INDEX_TAG
        }

        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.textSize = textSize
        mPaint!!.color = textColor

        //设置index触摸监听器
        setOnIndexPressedListener(object : OnIndexPressedListener {
            override fun onIndexPressed(index: Int, text: String) {
                if (mPressedShowTextView != null) {
                    //显示hintTextView
                    mPressedShowTextView!!.visibility = View.VISIBLE
                    mPressedShowTextView!!.text = text
                }
                //滑动列表
                if (mLayoutManager != null) {
                    val position = getPositionByTag(text)
                    if (position != -1) {
                        mLayoutManager!!.scrollToPositionWithOffset(position, 0)
                    }
                }
            }

            override fun onMotionEventEnd() {
                if (mPressedShowTextView != null) {
                    //隐藏hintTextView
                    mPressedShowTextView!!.visibility = View.GONE
                }
            }
        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        val indexBounds = Rect()
        var index = ""
        for (i in 0..mIndexDatas!!.size - 1) {
            index = mIndexDatas!![i]
            mPaint!!.getTextBounds(index, 0, index.length, indexBounds)//测量计算文字所在矩形，可以得到宽高
            val fontMetrics = mPaint!!.fontMetrics//获得画笔的FontMetrics，用来计算baseLine。因为drawText的y坐标，代表的是绘制的文字的baseLine的位置
            val baseLine = (mGapHeight - fontMetrics.bottom - fontMetrics.top) / 2//计算出在每格index区域，竖直居中的baseLine值
            canvas!!.drawText(index, mWidth.toFloat() / 2 - indexBounds.width() / 2, paddingTop + mGapHeight * i + baseLine, mPaint)//调用drawText，居中显示绘制index
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN -> setBackgroundColor(mPressedBackground)
            MotionEvent.ACTION_MOVE -> {
                val y = event.y
                //通过计算判断点的区域
                var pressI = (y - paddingTop) / mGapHeight
                //边界处理
                if(pressI < 0){
                    pressI = 0F
                }else if(pressI >= mIndexDatas!!.size){
                    pressI = (mIndexDatas!!.size - 1).toFloat()
                }
                //回调监听
                if(null != indexPressedListener){
                    indexPressedListener!!.onIndexPressed(pressI.toInt(), mIndexDatas!![pressI.toInt()])
                }
            }
            else -> {
                setBackgroundColor(Color.TRANSPARENT)//手指抬起时回复透明
                //回调监听
                if(null != indexPressedListener){
                    indexPressedListener!!.onMotionEventEnd()
                }
            }
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        mGapHeight = (mHeight - paddingTop - paddingBottom) / mIndexDatas!!.size
    }

    /**
     * 当前被按下的index监听器
     */
    interface OnIndexPressedListener {
        fun onIndexPressed(index: Int, text: String)
        fun onMotionEventEnd()//当触摸事件结束（UP CANCEL）
    }

    fun setOnIndexPressedListener(listener: OnIndexPressedListener) {
        this.indexPressedListener = listener
    }

    fun getOnIndexPressedListener(): OnIndexPressedListener = this.indexPressedListener!!

    /**
     * 显示当前被按下的index的TextView
     */
    fun setPressedShowTextView(textView: AppCompatTextView): IndexBar {
        this.mPressedShowTextView = textView
        return this
    }

    fun setLayoutManager(layoutManager: LinearLayoutManager): IndexBar {
        this.mLayoutManager = layoutManager
        return this
    }

    /**
     * 一定哟在设置数据源{@link #setSourceDatas(list)}之前调用
     */
    fun setNeedRealIndex(needRealIndex: Boolean): IndexBar {
        this.isNeedRealIndex = needRealIndex
        if (mIndexDatas != null) {
            mIndexDatas = ArrayList()
        }
        return this
    }

    fun setSourceDatas(sourceData: ArrayList<City>): IndexBar {
        this.mSourceDatas = sourceData
        initSourceDatas()//初始化数据源
        return this
    }

    /**
     * 初始化数据源
     */
    private fun initSourceDatas() {
        val size = mSourceDatas!!.size
        for (i in 0..size - 1) {
            val indexPinYinBean = mSourceDatas!![i]
            val pySb = StringBuilder()
            val target = indexPinYinBean.getTarget()//取出需要被拼音化的字段
//            for (k in 0..target.length - 1){
//                //利用TinyPinyin将char转成拼音
//                //查看源码，方法内 如果char为汉字，则返回大写拼音
//                //如果c不是汉字，则返回String.valueOf(c)
//                pySb.append(PinyinHelper.convertToPinyinString(target[k].toString(), "", PinyinFormat.WITHOUT_TONE))
//            }
            indexPinYinBean.pyCity = PinyinHelper.convertToPinyinString(target, "", PinyinFormat.WITHOUT_TONE)//设置城市全拼音

            /*
            设置城市拼音首字母
             */
            val tagStr = indexPinYinBean.pyCity!!.substring(0, 1).toUpperCase()
            if (tagStr.matches("[A-Z]".toRegex())) {//如果是A-Z字母开头
                indexPinYinBean.tag = tagStr
                if (isNeedRealIndex) {//如果需要真实的索引数据源
                    if (!mIndexDatas!!.contains(tagStr)) {//则判断是否已经将这个索引添加进去，若没有则添加
                        mIndexDatas!!.add(tagStr)
                    }
                }
            } else {//特殊字母这里统一用#处理
                indexPinYinBean.tag = "#"
                if (isNeedRealIndex) {//如果需要真实的索引数据源
                    if (!mIndexDatas!!.contains("#")) {
                        mIndexDatas!!.add("#")
                    }
                }
            }
        }
        sortData()
    }

    /**
     * 对数据源进行排序
     */
    private fun sortData() {
        //对右侧栏进行排序 将#丢在最后
        Collections.sort(mIndexDatas) { lhs, rhs ->
            if (lhs == "#") {
                1
            } else if (rhs == "#") {
                -1
            } else {
                lhs.compareTo(rhs)
            }
        }

        //对数据源进行排序
        Collections.sort(mSourceDatas) { lhs, rhs ->
            if (lhs.tag == "#") {
                1
            } else if (rhs.tag == "#") {
                -1
            } else {
                lhs.pyCity!!.compareTo(rhs.pyCity!!)
            }
        }
    }

    private fun getPositionByTag(tag: String): Int {
        if (tag.isEmpty()) {
            return -1
        }
        return (0..mSourceDatas!!.size - 1)
                .firstOrNull { tag == mSourceDatas!![it].tag } ?: -1
    }
}

