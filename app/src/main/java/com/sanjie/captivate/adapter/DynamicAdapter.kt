package com.sanjie.captivate.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.sanjie.captivate.R
import com.sanjie.captivate.mvp.model.Dynamic
import com.sanjie.zy.adpter.ZYRecyclerViewAdapter
import com.sanjie.zy.adpter.ZYViewHolder
import com.sanjie.zy.utils.ZYDateUtils
import com.sanjie.zy.utils.ZYDensityUtils
import com.sanjie.zy.utils.ZYFormatTimeUtils
import com.sanjie.zy.widget.view.ZYCircleImageView
import java.util.concurrent.TimeUnit

/**
 * Created by SanJie on 2017/5/22.
 */
class DynamicAdapter(val context: Context, recyclerView: RecyclerView, dataList: ArrayList<Dynamic>) :
        ZYRecyclerViewAdapter<Dynamic>(recyclerView, dataList, R.layout.item_dynamic_list) {

    var listener: OnOperateListener? = null

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

        /*
        转发、评论、点赞
         */
        val forwardingBtn = holder.getView<LinearLayout>(R.id.item_dynamic_forwarding_btn)
        val commentBtn = holder.getView<LinearLayout>(R.id.item_dynamic_comment_btn)
        val likeBtn = holder.getView<LinearLayout>(R.id.item_dynamic_like_btn)

        RxView.clicks(forwardingBtn)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe {
                    if (listener != null) {
                        listener!!.onForwarding(position)
                    }
                }
        RxView.clicks(commentBtn)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe {
                    if (listener != null) {
                        listener!!.onComment(position)
                    }
                }
        RxView.clicks(likeBtn)
                .subscribe {
                    if (listener != null) {
                        listener!!.onLike(position)
                        holder.setText(R.id.item_dynamic_like_tv, data.like!!.toString())
                    }
                }

        //头像
        Glide.with(context).load(data.authorAvatar).into(holder.getView<View>(R.id.item_dynamic_author_avatar_iv) as ZYCircleImageView)
        var publishDate = ZYFormatTimeUtils.getTimeSpanByNow1(ZYDateUtils.string2Millis(data.createdAt))
        if (publishDate.contains("天") or publishDate.contains("周") or publishDate.contains("月") or publishDate.contains("年")) {
            publishDate = ZYDateUtils.date2String(ZYDateUtils.string2Date(data.createdAt), "MM-dd HH:mm")
        }
        //内容
        holder.setText(R.id.item_dynamic_author_name_tv, data.authorName)
                .setText(R.id.item_dynamic_source_tv, "$publishDate 来自 ${data.source}")
                .setText(R.id.item_dynamic_content_tv, data.content)
                .setText(R.id.item_dynamic_comment_tv, data.comment.toString())
                .setText(R.id.item_dynamic_like_tv, data.like.toString())
    }

    interface OnOperateListener {
        fun onForwarding(position: Int)
        fun onComment(position: Int)
        fun onLike(position: Int)
    }

    fun setOperateListener(operateListener: OnOperateListener) {
        this.listener = operateListener
    }
}