package com.sanjie.captivate.mvp.model

import cn.bmob.v3.BmobObject
import com.google.gson.Gson
import org.json.JSONObject

/**
 * Created by LangSanJie on 2017/5/5.
 */
class Advertising: BmobObject {

    var url: String = ""

    constructor(url: String) {
        this.url = url
    }

    fun getInstanceFromJSONObject(jsonObject: JSONObject): Advertising{
        return Gson().fromJson(jsonObject.toString(), Advertising::class.java)
    }

    fun toJSONString(): String{
        return Gson().toJson(this)
    }
}