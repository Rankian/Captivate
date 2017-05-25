package com.sanjie.captivate.mvp.presenter

import com.sanjie.captivate.mvp.model.Song

/**
 * Created by LangSanJie on 2017/5/3.
 */
interface MusicPresenter {

    interface View {
        fun scannerMusicResult(list: List<Song>)
    }

    fun scannerMusic()
}