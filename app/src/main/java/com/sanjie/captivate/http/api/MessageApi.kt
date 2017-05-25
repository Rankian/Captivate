package com.sanjie.captivate.http.api

import com.sanjie.captivate.mvp.model.Message
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by LangSanJie on 2017/4/13.
 */
interface MessageApi {

    @GET("token")
    fun getToken(@Query("id") id: Int): Observable<String>

    @GET("message_page_list")
    fun load(@Query("receiveUserId") receiveUserId: String): Observable<List<Message>>
}