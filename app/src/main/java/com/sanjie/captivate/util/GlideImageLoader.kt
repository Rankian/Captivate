package com.sanjie.captivate.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sanjie.zy.widget.banner.loader.ImageLoader

/**
 * Created by LangSanJie on 2017/5/5.
 */
class GlideImageLoader: ImageLoader() {
    override fun displayImage(context: Context?, o: Any?, imageView: ImageView?) {
        Glide.with(context).load(o).into(imageView)
    }
}