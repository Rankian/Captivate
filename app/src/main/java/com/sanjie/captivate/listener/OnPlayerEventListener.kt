package com.sanjie.captivate.listener

import com.sanjie.captivate.mvp.model.Song

/**
 * Created by SanJie on 2017/5/9.
 */
interface OnPlayerEventListener {

    /**
     * 更新进度
     */
    fun onProgress(progress: Int)

    /**
     * 切换歌曲
     */
    fun onChange(song: Song)

    /**
     * 暂停播放
     */
    fun onPlayerPause()

    /**
     * 继续播放
     */
    fun onPlayerResume()

    /**
     * 更新定制停止播放时间
     */
    fun onTimer(remain: Long)

    /**
     * 歌曲列表更新
     */
    fun onMusicListUpdate()
}