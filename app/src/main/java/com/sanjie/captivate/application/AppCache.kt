package com.sanjie.captivate.application

import android.content.Context
import com.sanjie.captivate.mvp.model.Song
import com.sanjie.captivate.service.PlayService

/**
 * Created by SanJie on 2017/5/9.
 */
class AppCache {

    private var mContext: Context? = null
    private var mPlayService: PlayService? = null

    private var mMusicList: ArrayList<Song> = ArrayList()

    constructor()

    fun init(context: Context) {
        mContext = context.applicationContext
    }

    companion object {

        private fun getInstance(): AppCache = AppCache()

        fun getContext(): Context = getInstance().mContext!!

        fun getPlayService(): PlayService = getInstance().mPlayService!!

        fun setPlayService(service: PlayService) {
            getInstance().mPlayService = service
        }

        fun getMusicList(): ArrayList<Song> = getInstance().mMusicList
    }

}