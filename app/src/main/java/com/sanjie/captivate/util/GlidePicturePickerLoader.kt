package com.sanjie.captivate.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sanjie.zy.utils.ZYPickerImageLoader

/**
 * Created by LangSanJie on 2017/4/25.
 */
class GlidePicturePickerLoader : ZYPickerImageLoader {

    override fun display(imageView: ImageView?, path: String?, width: Int, height: Int) {
        Glide.with(imageView!!.context)
                .load(path)
                .error(com.sanjie.zy.R.drawable.ic_preview_image)
                .centerCrop()
                .override(width,height)
                .into(imageView)
    }
}