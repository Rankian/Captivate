package com.sanjie.captivate.http

import com.sanjie.captivate.common.AppConfig
import com.sanjie.captivate.http.api.*
import com.sanjie.captivate.http.converter.JsonConverterFactory
import com.sanjie.captivate.http.interceptor.Interceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File

/**
 * Created by LangSanJie on 2017/4/13.
 */
class RetrofitManager {

    private var retrofit: Retrofit? = null

    fun getWeatherApi(): WeatherApi{
        return retrofit!!.create(WeatherApi::class.java)
    }
    fun downloadApi(): DownloadApi{
        return retrofit!!.create(DownloadApi::class.java)
    }

    constructor() {
        retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.API_URL)
                .client(getCacheOkHttpClient())
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object T {
        fun getOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        var request = chain.request()
                                .newBuilder()
                                .addHeader("uid", 1.toString())
                                .build()
                        chain.proceed(request)
                    }
                    .build()
        }

        fun getCacheOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .cache(Cache(File(AppConfig.APP_NAME + "cacheData"), 3 * 1024 * 1024))
                    .addInterceptor(Interceptor.cacheInterceptor())
                    .build()
        }

        var retrofitManager = RetrofitManager()

        fun getInstance(): RetrofitManager {
            return retrofitManager
        }
    }
}