package com.sanjie.captivate.mvp.model

import cn.bmob.v3.BmobUser
import com.google.gson.Gson
import com.sanjie.zy.utils.ZYEmptyUtils
import org.json.JSONObject

/**
 * Created by LangSanJie on 2017/4/19.
 */
class User : BmobUser {

    var id: String = ""
    var signature: String = ""
    var phone: String = ""
    var nickname: String = ""
    var gender: Int = -1
    var avatar: String = ""

    constructor(id: String, signature: String, phone: String, nickname: String, gender: Int, avatar: String, id1: String) : super() {
        this.id = id
        this.signature = signature
        this.phone = phone
        this.nickname = nickname
        this.gender = gender
        this.avatar = avatar
        this.id = id1
    }

    constructor() : super()

    fun getInstanceFromJSON(json: JSONObject): User?{
        if(ZYEmptyUtils.isEmpty(json)){
            return null
        }
        return Gson().fromJson(json.toString(),User::class.java)
    }

    fun toJSONString(): String? {
        return Gson().toJson(this)
    }
}