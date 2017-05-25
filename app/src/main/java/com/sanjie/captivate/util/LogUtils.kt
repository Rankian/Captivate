package com.sanjie.captivate.util

import android.util.Log
import com.sanjie.captivate.application.CapApplication
import com.sanjie.captivate.common.AppConfig

/**
 * Created by SanJie on 2017/5/24.
 */
class LogUtils {

    companion object {

        fun debug(message: String) {
            if (AppConfig.IS_DEBUG) {
                Log.d(CapApplication::class.java.simpleName, message)
            }
        }

        fun error(message: String) {
            if (AppConfig.IS_DEBUG) {
                Log.e(CapApplication::class.java.simpleName, message)
            }
        }

        fun warn(message: String){
            if (AppConfig.IS_DEBUG) {
                Log.w(CapApplication::class.java.simpleName, message)
            }
        }

    }
}