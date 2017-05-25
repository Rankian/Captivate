package com.sanjie.captivate.mvp.model

import android.net.Uri
import com.google.gson.Gson
import com.sanjie.zy.base.ZYApplication
import com.sanjie.zy.utils.ZYEmptyUtils
import org.json.JSONObject

/**
 * Created by LangSanJie on 2017/5/3.
 */
class Song {
    var id: Long? = null
    //标题
    var title: String? = null
    //艺术家
    var artist: String? = null
    //专辑
    var album: String? = null
    //持续时间
    var duration: Long? = null
    //音乐uri
    var uri: String? = null
    //专辑封面id
    var albumId: Long? = null
    //根据专辑封面id获得专辑图片uri
    var coverUri: String? = null
    //音乐文件名r
    var fileName: String? = null
    //音乐文件大小
    var fileSize: Long? = null
    //发行时间
    var year: String? = null

    constructor()

    constructor(id: Long?, title: String?, artist: String?, album: String?, duration: Long?, uri: String?, albumId: Long?, coverUri: String?, fileName: String?, fileSize: Long?, year: String?) {
        this.id = id
        this.title = title
        this.artist = artist
        this.album = album
        this.duration = duration
        this.uri = uri
        this.albumId = albumId
        this.coverUri = coverUri
        this.fileName = fileName
        this.fileSize = fileSize
        this.year = year
    }

    fun getInstanceFromJSON(json: JSONObject): Song? {
        if (ZYEmptyUtils.isEmpty(json)) {
            return null
        }
        return Gson().fromJson(json.toString(), Song::class.java)
    }

    fun toJSONString(): String? {
        return Gson().toJson(this)
    }
}