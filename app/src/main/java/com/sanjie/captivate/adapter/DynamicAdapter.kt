package com.sanjie.captivate.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.sanjie.captivate.R
import com.sanjie.captivate.mvp.model.Dynamic
import com.sanjie.zy.adpter.ZYRecyclerViewAdapter
import com.sanjie.zy.adpter.ZYViewHolder
import com.sanjie.zy.utils.ZYDateUtils
import com.sanjie.zy.utils.ZYDensityUtils
import com.sanjie.zy.utils.ZYFormatTimeUtils
import com.sanjie.zy.widget.view.ZYCircleImageView

/**
 * Created by SanJie on 2017/5/22.
 */
class DynamicAdapter(val context: Context, recyclerView: RecyclerView, dataList: ArrayList<Dynamic>) :
        ZYRecyclerViewAdapter<Dynamic>(recyclerView, dataList, R.layout.item_dynamic_list) {

    override fun bindData(holder: ZYViewHolder?, data: Dynamic?, position: Int) {
        /*
        photos RecyclerView
         */
        val photosRecyclerView = holder!!.getView<View>(R.id.item_dynamic_photos_recycler_view) as RecyclerView
        val photos = data!!.photos

        if (photos!!.size > 0) {
            photosRecyclerView.visibility = View.VISIBLE
        } else {
            photosRecyclerView.visibility = View.GONE
        }

        photosRecyclerView.adapter = DynamicPhotosAdapter(context, photosRecyclerView, photos, ZYDensityUtils.getScreenWidth() / 3)
        val gridManager = GridLayoutManager(context, 3)
        gridManager.isAutoMeasureEnabled = true
        photosRecyclerView.layoutManager = gridManager

        //头像
        Glide.with(context).load(data.authorAvatar).into(holder.getView<View>(R.id.item_dynamic_author_avatar_iv) as ZYCircleImageView)
        //内容
        holder.setText(R.id.item_dynamic_author_name_tv, data.authorName)
                .setText(R.id.item_dynamic_source_tv, "${ZYFormatTimeUtils.getTimeSpanByNow1(ZYDateUtils.string2Millis(data.createdAt))} 来自 ${data.source}")
                .setText(R.id.item_dynamic_content_tv, data.content)
                .setText(R.id.item_dynamic_comment_tv, data.comment.toString())
                .setText(R.id.item_dynamic_like_tv, data.like.toString())
    }
}