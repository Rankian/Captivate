package com.sanjie.captivate.application

import cn.bmob.sms.BmobSMS
import cn.bmob.v3.Bmob
import com.sanjie.captivate.common.AppConfig
import com.sanjie.captivate.util.GlidePicturePickerLoader
import com.sanjie.zy.base.ZYApplication
import com.sanjie.zy.common.ZYActivityStack
import com.sanjie.zy.picture.ZYPicturePicker
import com.sanjie.zy.utils.log.ZYLog
import com.tencent.bugly.crashreport.CrashReport

/**
 * Created by LangSanJie on 2017/4/12.
 */
class CapApplication : ZYApplication() {

    override fun onCreate() {
        super.onCreate()
        ZYLog.init()
        Bmob.initialize(this,AppConfig.BMOB_APP_ID)
        BmobSMS.initialize(this,AppConfig.BMOB_APP_ID)
        ZYPicturePicker.init(GlidePicturePickerLoader())
        CrashReport.initCrashReport(applicationContext,"b04d935e6a",true)
    }



    fun exit() {
        ZYActivityStack.getInstance().appExit()
    }
}