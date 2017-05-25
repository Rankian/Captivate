package com.sanjie.captivate.mvp.model

import cn.bmob.v3.BmobObject
import com.google.gson.Gson
import com.sanjie.zy.utils.ZYEmptyUtils
import org.json.JSONObject

/**
 * Created by LangSanJie on 2017/5/4.
 */
class Notice : BmobObject {

    var content: String = ""
    var colorContent: String = ""
    var start: Int = 0
    var end: Int = 0
    var color: String = ""

    constructor(content: String, colorContent: String, start: Int, end: Int, color: String) : super() {
        this.content = content
        this.colorContent = colorContent
        this.start = start
        this.end = end
        this.color = color
    }

    constructor() : super()

    fun getInstanceFromJSONObject(jsonObject: JSONObject): Notice? {
        if (ZYEmptyUtils.isEmpty(jsonObject)) return null
        return Gson().fromJson(jsonObject.toString(), Notice::class.java)
    }

    fun toJSONString(): String {
        return Gson().toJson(this)
    }
}