package com.sanjie.captivate.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.sanjie.captivate.R
import com.sanjie.captivate.mvp.model.Travel
import com.sanjie.zy.adpter.ZYRecyclerViewAdapter
import com.sanjie.zy.adpter.ZYViewHolder

/**
 * Created by SanJie on 2017/5/11.
 */
class TravelAdapter(val context: Context, recyclerView: RecyclerView, dadaList: ArrayList<Travel>)
    : ZYRecyclerViewAdapter<Travel>(recyclerView, dadaList, R.layout.item_travel_list) {

    override fun bindData(holder: ZYViewHolder?, data: Travel?, position: Int) {
        holder!!.setText(R.id.item_travel_name_tv,data!!.photoName)

        Glide.with(context).load(data.photoUrl).centerCrop().into(holder.getView<View>(R.id.item_travel_photo_iv) as AppCompatImageView)
    }
}