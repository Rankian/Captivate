package com.sanjie.captivate.util

import java.util.*

/**
 * 系统工具类
 * Created by SanJie on 2017/5/22.
 */
class SystemUtils {

    companion object {
        /**
         * 获取手机系统语言
         */
        fun getSystemLanguage(): String = Locale.getDefault().language

        /**
         * 获取手机系统版本号
         */
        fun getSystemVersion(): String = android.os.Build.VERSION.RELEASE

        /**
         * 获取手机型号
         */
        fun getDeviceModel(): String = android.os.Build.MODEL

        /**
         * 获取手机厂商名称
         */
        fun getDeviceBrand(): String = android.os.Build.BRAND
    }
}