package com.sanjie.captivate.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.sanjie.captivate.R
import com.sanjie.zy.adpter.ZYRecyclerViewAdapter
import com.sanjie.zy.adpter.ZYViewHolder
import com.sanjie.zy.picture.bean.ImageItem
import com.sanjie.zy.picture.ui.ZYPreviewActivity
import io.reactivex.rxkotlin.toObservable

/**
 * Created by SanJie on 2017/5/22.
 */
class DynamicPhotosAdapter(val context: Context, recyclerView: RecyclerView, dataList: List<String>, val width: Int) :
        ZYRecyclerViewAdapter<String>(recyclerView, dataList, R.layout.item_dynamic_photo_list) {

    var imageItems: ArrayList<ImageItem>? = null

    init {
        imageItems = ArrayList()
        dataList.toObservable()
                .subscribe {
                    val imageItem = ImageItem()
                    imageItem.path = it
                    imageItems!!.add(imageItem)
                }
    }

    override fun bindData(holder: ZYViewHolder?, data: String?, position: Int) {
        val photoView = holder!!.getView<View>(R.id.item_dynamic_photo_iv) as AppCompatImageView

        photoView.setOnClickListener {
            ZYPreviewActivity.start(context, imageItems)
        }

        val lp = photoView.layoutParams
        lp.width = width
        lp.height = width
        photoView.layoutParams = lp
        Glide.with(context).load(data).fitCenter().override(width, width).placeholder(R.mipmap.image_loading).into(photoView)
    }
}