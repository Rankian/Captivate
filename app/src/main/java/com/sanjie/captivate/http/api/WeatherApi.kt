package com.sanjie.captivate.http.api

import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by LangSanJie on 2017/4/27.
 */
interface WeatherApi {

    @GET("weather/index")
    fun loadWeather(@Query("cityname") cityName: String, @Query("key") key: String) : Observable<JSONObject>

}