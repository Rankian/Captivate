package com.sanjie.captivate.http.converter

import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter

/**
 * Created by LangSanJie on 2017/4/13.
 */
class JsonResponseBodyConverter<T> : Converter<ResponseBody, T> {
    constructor()

    companion object T {
        val TAG: String = "JsonResponseBodyConverter"
    }

    override fun convert(value: ResponseBody?): T {
//        val response : JSONObject? = JSONObject(value.toString())
//        return response as T
        val `object`: JSONObject
        try {
            `object` = JSONObject(value!!.string())
            return `object` as T
        } catch (e: JSONException) {
            return null!!
        }
    }
}