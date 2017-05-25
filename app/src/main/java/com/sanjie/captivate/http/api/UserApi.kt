package com.sanjie.captivate.http.api

import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by LangSanJie on 2017/4/13.
 */
interface UserApi {

    @POST("user/login")
    fun login(@Query("loginName") loginName: String, @Query("loginPassword") loginPassword: String
    , @Query("deviceToken") deviceToken: String, @Query("deviceType") deviceType: String): Observable<JSONObject>
}