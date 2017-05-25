package com.sanjie.captivate.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

/**
 * Created by LangSanJie on 2017/4/27.
 */
class Circle(context: Context?) : View(context) {

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        val paint = Paint()
        canvas!!.drawCircle(10f, 10f, 10f, paint)
    }
}