package com.sanjie.captivate.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.sanjie.captivate.R
import com.sanjie.captivate.mvp.model.News
import com.sanjie.zy.adpter.ZYRecyclerViewAdapter
import com.sanjie.zy.adpter.ZYViewHolder

/**
 * Created by LangSanJie on 2017/4/18.
 */
class HomeAdapter(val context: Context, mRecyclerView: RecyclerView, dataLists: MutableList<News>?)
    : ZYRecyclerViewAdapter<News>(mRecyclerView, dataLists, R.layout.item_home_list) {

    override fun bindData(holder: ZYViewHolder?, data: News?, position: Int) {
        holder!!.itemView.tag = position

        //加载图片
        Glide.with(context).load(data!!.pictureUrl).centerCrop().into(holder.getView<View>(R.id.item_home_image_iv) as AppCompatImageView)

        //显示内容
        holder.setText(R.id.item_home_content_tv, if (TextUtils.isEmpty(data.content)) "" else "\u3000\u3000"+data.content)
                .setText(R.id.item_home_time_tv, if (TextUtils.isEmpty(data.time)) "" else data.time)
    }
}