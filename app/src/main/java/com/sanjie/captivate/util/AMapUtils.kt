package com.sanjie.captivate.util

import android.app.Activity
import android.support.v4.app.Fragment
import com.sanjie.zy.utils.log.ZYLog
import io.reactivex.Observable

/**
 * 高德地图 功能类
 * Created by LangSanJie on 2017/5/2.
 */
class AMapUtils {


    var mLocation: AMapLocation? = null

    fun with(activity: Activity): AMapUtils {
        mLocation = AMapLocation.get(activity)
        return this
    }

    fun with(fragment: Fragment): AMapUtils {
        mLocation = AMapLocation.get(fragment.activity!!)
        return this
    }

    fun isOnceLocation(isOnceLocation: Boolean): AMapUtils {
        mLocation!!.setOnceLocation(isOnceLocation)
        return this
    }

    fun startLocation(): Observable<com.amap.api.location.AMapLocation> {
        return mLocation!!.getLocationSubject().filter {
            aBoolean ->
            aBoolean
        }.flatMap {
            mLocation!!.getResultSubject()
        }.take(1)
    }
}