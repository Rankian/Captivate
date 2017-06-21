package com.sanjie.captivate.mvp.model

import cn.bmob.v3.BmobObject
import com.google.gson.Gson
import io.reactivex.disposables.Disposable
import org.json.JSONObject
import java.io.Serializable

/**
 * 应用
 * Created by SanJie on 2017/6/9.
 */
class App : BmobObject, Serializable {

    var name: String? = null
    var icon: String? = null
    var downloadUrl: String? = null
    var developer: String? = null
    var versionName: String? = null
    var introduction: String? = null
    var intro: String? = null
    var packageName: String? = null
    var software: String? = null
    var category: String? = null
    var score: Float? = null
    var screenshot: ArrayList<String>? = null
    var disposable: Disposable? = null
    /*
    0   未下载
    1   暂停
    2   继续
     */
    var status: Int? = null

    constructor() : super()

    constructor(name: String?, icon: String?, downloadUrl: String?, developer: String?, versionName: String?,
                introduction: String?, intro: String?, packageName: String?, software: String?, category: String?,
                score: Float?, screenshot: ArrayList<String>?, status: Int?) : super() {
        this.name = name
        this.icon = icon
        this.downloadUrl = downloadUrl
        this.developer = developer
        this.versionName = versionName
        this.introduction = introduction
        this.intro = intro
        this.packageName = packageName
        this.software = software
        this.category = category
        this.score = score
        this.screenshot = screenshot
        this.status = status
    }

    fun getInstanceFromJSONObject(jsonObject: JSONObject): App = Gson().fromJson(jsonObject.toString(), App::class.java)

    fun getJSONString(): String = Gson().toJson(App::class.java)

}