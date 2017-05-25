package com.sanjie.captivate.util

import android.content.Context
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.sanjie.zy.utils.log.ZYLog
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 *
 * 高德定位
 *
 * Created by LangSanJie on 2017/5/2.
 */
class AMapLocation(context: Context) {

    internal var context: Context? = context

    internal val resultSubject: PublishSubject<AMapLocation> = PublishSubject.create()
    internal val locationSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    var mLocationClient: AMapLocationClient? = null
    var mLocationClientOption: AMapLocationClientOption? = null

    companion object{
        private var instance: com.sanjie.captivate.util.AMapLocation? = null

        @Synchronized
        fun get(context: Context):com.sanjie.captivate.util.AMapLocation{
            if(instance == null){
                instance = com.sanjie.captivate.util.AMapLocation(context)
            }
            return instance!!
        }
    }

    val mLocationListener: AMapLocationListener = AMapLocationListener {
        if (it != null) {
            when (it.errorCode) {
                0 -> {
                    //定位成功，解析amapLocation获取相应内容
                    resultSubject.onNext(it)
                    mLocationClient!!.stopLocation()
                }
                else -> {
                    val errorInfo = "ErrorCode: ${it.errorCode},errorInfo:${it.errorInfo}"
                    ZYLog.d(errorInfo)
                }
            }
        }
    }

    /**
     * 初始化百度定位
     */
    fun init() {
        mLocationClient = AMapLocationClient(context)
        mLocationClientOption = AMapLocationClientOption()
        //设置定位模式
        mLocationClientOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置单次定位,默认false
//        mLocationClientOption!!.isOnceLocation = true
        //获取最近3s内精度最高的一次定位结果：
//        mLocationClientOption!!.isOnceLocationLatest = true
        //是否返回地址信息，默认返回
        mLocationClientOption!!.isNeedAddress = true

        mLocationClient!!.setLocationListener(mLocationListener)
        mLocationClient!!.setLocationOption(mLocationClientOption)
    }

    fun getLocationSubject(): BehaviorSubject<Boolean> {
        startLocation()
        return locationSubject
    }

    fun getResultSubject(): PublishSubject<AMapLocation> {
        return resultSubject
    }

    /**
     * 设置是否单词定位
     * 默认单次
     */
    fun setOnceLocation(isOnceLocation: Boolean) {
        mLocationClientOption!!.isOnceLocation = isOnceLocation
    }

    /**
     * 开始定位
     */
    fun startLocation() {
        locationSubject.onNext(true)
        mLocationClient!!.startLocation()
    }

    init {
        init()
    }

}