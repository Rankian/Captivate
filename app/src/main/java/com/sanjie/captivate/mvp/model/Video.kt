package com.sanjie.captivate.mvp.model

import cn.bmob.v3.BmobObject
import com.sanjie.zy.videoplayer.bean.ZYVideoInfo
import java.io.Serializable

/**
 * Created by SanJie on 2017/6/23.
 */
class Video : BmobObject , ZYVideoInfo, Serializable{

    var videoUrl: String? = null
    var title: String? = null
    var author: String? = null
    var coverPhoto: String? = null

    constructor() : super()

    constructor(videoUrl: String?, title: String?, author: String?, coverPhoto: String?) : super() {
        this.videoUrl = videoUrl
        this.title = title
        this.author = author
        this.coverPhoto = coverPhoto
    }

    override fun getVideoPath(): String {
        return videoUrl!!
    }

    override fun getVideoTitle(): String {
        return title!!
    }
}