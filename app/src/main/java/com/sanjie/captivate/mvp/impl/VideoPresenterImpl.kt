package com.sanjie.captivate.mvp.impl

import cn.bmob.v3.BmobQuery
import com.sanjie.captivate.mvp.model.Video
import com.sanjie.captivate.mvp.presenter.VideoPresenter

/**
 * Created by SanJie on 2017/6/23.
 */
class VideoPresenterImpl(val videoView: VideoPresenter.View) : VideoPresenter {

    override fun loadVideo() {
        val query = BmobQuery<Video>()
        query.order("-createdAt")
        query.findObjectsObservable(Video::class.java)
                .subscribe {
                    videoView.onVideoResult(it)
                }
    }
}