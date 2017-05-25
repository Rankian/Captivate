package com.sanjie.captivate.http.converter

import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by LangSanJie on 2017/4/13.
 */
class JsonConverterFactory : Converter.Factory(){

    companion object T{
        fun create() = JsonConverterFactory()
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *> {
        return JsonResponseBodyConverter<JSONObject>()
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out Annotation>?, methodAnnotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody> {
        return JsonRequestBodyConverter<JSONObject>()
    }
}