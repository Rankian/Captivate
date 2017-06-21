package com.sanjie.captivate.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.sanjie.captivate.R
import com.sanjie.captivate.util.LogUtils
import com.sanjie.zy.adpter.ZYRecyclerViewAdapter
import com.sanjie.zy.adpter.ZYViewHolder

/**
 * Created by SanJie on 2017/5/22.
 */
class PublishPhotoAdapter(val context: Context, recyclerView: RecyclerView, dataList: List<String>, val width: Int) :
        ZYRecyclerViewAdapter<String>(recyclerView, dataList, R.layout.item_publish_photo_list) {

    var listener: OnPhotoDeleteListener? = null

    override fun bindData(holder: ZYViewHolder?, data: String?, position: Int) {
        val photoView = holder!!.getView<View>(R.id.item_publish_photo_iv) as AppCompatImageView
        val deleteBtn = holder.getView<View>(R.id.item_publish_photo_delete_btn) as AppCompatImageView

        deleteBtn.setOnClickListener {
            if(listener != null){
                listener!!.delete(position)
            }
        }

        val lp = photoView.layoutParams
        lp.width = width
        lp.height = width
        photoView.layoutParams = lp
        Glide.with(context).load(data).fitCenter().override(width, width).into(photoView)
    }

    interface OnPhotoDeleteListener{
        fun delete(position: Int)
    }

    fun setDeleteListener(deleteListener: OnPhotoDeleteListener){
        this.listener = deleteListener
    }
}