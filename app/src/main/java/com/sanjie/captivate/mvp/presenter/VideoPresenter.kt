package com.sanjie.captivate.mvp.presenter

import com.sanjie.captivate.mvp.model.Video

/**
 * Created by SanJie on 2017/6/23.
 */
interface VideoPresenter {

    interface View{
        fun onVideoResult(videos: List<Video>)
    }

    fun loadVideo()
}