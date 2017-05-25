package com.sanjie.captivate.http.converter

import com.sanjie.zy.utils.log.ZYLog
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Converter

/**
 * Created by LangSanJie on 2017/4/13.
 */
class JsonRequestBodyConverter<T> : Converter<T, RequestBody> {
    constructor()

    companion object Type {
        val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")!!
    }



    override fun convert(value: T): RequestBody {
        return RequestBody.create(MEDIA_TYPE, value.toString())
    }
}