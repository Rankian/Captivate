package com.sanjie.captivate.http.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by SanJie on 2017/5/12.
 */
interface DownloadApi {

    @Streaming //大文件时要加不然会OOM
    @GET
    fun download(@Url url: String) : Observable<ResponseBody>
}