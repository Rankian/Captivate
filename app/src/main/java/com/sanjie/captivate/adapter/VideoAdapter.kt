package com.sanjie.captivate.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sanjie.captivate.R
import com.sanjie.captivate.mvp.model.Video
import com.sanjie.zy.adpter.ZYRecyclerViewAdapter
import com.sanjie.zy.adpter.ZYViewHolder

/**
 * Created by SanJie on 2017/6/23.
 */
class VideoAdapter(val context: Context, recyclerView: RecyclerView, datas: ArrayList<Video>)
    : ZYRecyclerViewAdapter<Video>(recyclerView, datas, R.layout.item_video) {

    override fun bindData(holder: ZYViewHolder?, video: Video?, position: Int) {
        holder!!.setText(R.id.item_video_title, video!!.title)
        Glide.with(context).load(video.coverPhoto).into(holder.getView<AppCompatImageView>(R.id.item_video_cover_photo_iv))
    }

}