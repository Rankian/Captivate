package com.sanjie.captivate.mvp.model

import cn.bmob.v3.BmobObject
import com.google.gson.Gson
import org.json.JSONObject

/**
 * 动态
 * Created by SanJie on 2017/5/22.
 */
class Dynamic : BmobObject {

    //作者名字
    var authorName: String? = null
    //作者头像
    var authorAvatar: String? = null
    //内容
    var content: String? = null
    //照片
    var photos: ArrayList<String>? = null
    //设备来源
    var source: String? = null
    //转发量
    var forwarding: Int? = null
    //评论
    var comment: Int? = null
    //点赞数
    var like: Int? = null


    constructor(authorName: String?, authorAvatar: String?, content: String?, photos: ArrayList<String>?, source: String?, forwarding: Int?, comment: Int?, like: Int?) : super() {
        this.authorName = authorName
        this.authorAvatar = authorAvatar
        this.content = content
        this.photos = photos
        this.source = source
        this.forwarding = forwarding
        this.comment = comment
        this.like = like
    }

    constructor() : super()

    fun getInstanceFromJSONObject(jsonObject: JSONObject): Dynamic = Gson().fromJson(jsonObject.toString(), Dynamic::class.java)

    fun getJSONString(): String = Gson().toJson(Dynamic::class.java)
}