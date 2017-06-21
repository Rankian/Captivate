package com.sanjie.captivate.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sanjie.captivate.R
import com.sanjie.zy.adpter.ZYRecyclerViewAdapter
import com.sanjie.zy.adpter.ZYViewHolder

/**
 * Created by SanJie on 2017/6/9.
 */
class AppScreenshotAdapter(val context: Context, recyclerView: RecyclerView, screenshots: ArrayList<String>):
        ZYRecyclerViewAdapter<String>(recyclerView, screenshots, R.layout.item_app_details_screenshot){

    override fun bindData(holder: ZYViewHolder?, screenshot: String?, position: Int) {
        Glide.with(context).load(screenshot).into(holder!!.getView<AppCompatImageView>(R.id.item_app_screenshot_iv))
    }
}