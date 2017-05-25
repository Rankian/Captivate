package com.sanjie.captivate.util

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.sanjie.captivate.common.AppConfig
import com.sanjie.captivate.mvp.model.Song
import com.sanjie.zy.utils.ZYEmptyUtils
import io.reactivex.Observable
import io.reactivex.Observer

/**
 * Created by LangSanJie on 2017/5/3.
 */
class ScanMusicUtil(private var context: Context) : Observable<MutableList<Song>>() {

    override fun subscribeActual(observer: Observer<in MutableList<Song>>?) {
        observer!!.onNext(scanMusic())
    }

    /**
     * 扫描音乐
     */
    private fun scanMusic(): MutableList<Song>? {
        val musicList: MutableList<Song> = ArrayList()
        val cursor = context.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        ) ?: return null
        while (cursor.moveToNext()) {
            //是否为音乐
            val isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC))
            if (isMusic == 0) {
                continue
            }
            val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
            // 标题
            val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            // 艺术家
            val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            // 专辑
            val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
            // 持续时间
            val duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            // 音乐uri
            val uri = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            // 专辑封面id，根据该id可以获得专辑图片uri
            val albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
            var coverUri = getCoverUri(albumId)
            if(ZYEmptyUtils.isEmpty(coverUri)){
                coverUri = AppConfig.DEFAULT_SONG_URI
            }
            // 音乐文件名
            val fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
            // 音乐文件大小
            val fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
            // 发行时间
            val year = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR))

            val song = Song(id, title, artist, album, duration, uri, albumId, coverUri, fileName, fileSize, year)
            if (duration > 60 * 1000 && !musicList.contains(song)) {
                musicList.add(song)
            }
        }
        cursor.close()
        return musicList
    }

    private fun getCoverUri(albumId: Long?): String? {
        var uri: String? = null
        val cursor = context.contentResolver.query(
                Uri.parse("content://media/external/audio/albums/$albumId"),
                arrayOf("album_art"), null, null, null
        )
        if (cursor != null) {
            cursor.moveToNext()
            uri = cursor.getString(0)
            cursor.close()
        }
        return uri
    }
}