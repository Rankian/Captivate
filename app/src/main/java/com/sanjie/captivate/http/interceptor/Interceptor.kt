package com.sanjie.captivate.http.interceptor

import com.sanjie.zy.utils.ZYNetworkUtils
import com.sanjie.zy.utils.log.ZYLog
import okhttp3.CacheControl
import okhttp3.Interceptor

/**
 * Created by LangSanJie on 2017/4/13.
 */
class Interceptor {

    companion object T {
        val TAG: String = "Interceptor"
        var interceptor: Interceptor? = null

        fun cacheInterceptor(): Interceptor {
            interceptor = Interceptor { chain ->
                var request = chain.request()//获取请求
                //没有网络的时候强制使用缓存数据
                if (!ZYNetworkUtils.isConnected()) {
                    request = request.newBuilder()
                            //强制使用缓存数据
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build()
                    ZYLog.d("no network, is from cache", TAG)
                }
                val originalResponse = chain.proceed(request)
                if (ZYNetworkUtils.isConnected()) {
                    originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + 0)
                            .build()
                } else {
                    //设置无网络的缓存时间
                    val maxTime = 4 * 24 * 60 * 60
                    originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                            .build()
                }
            }
            return interceptor as Interceptor
        }

        fun onlyCacheInterceptor(): Interceptor {
            interceptor = okhttp3.Interceptor { chain ->
                var request = chain.request()//获取请求
                var response = chain.proceed(request)
                val maxAge: Int = 60
                response
                        .newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build()
            }
            return interceptor as Interceptor
        }
    }
}