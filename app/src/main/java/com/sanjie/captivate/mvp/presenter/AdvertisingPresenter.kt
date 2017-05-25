package com.sanjie.captivate.mvp.presenter

import com.sanjie.captivate.mvp.model.Advertising

/**
 * Created by LangSanJie on 2017/5/5.
 */
interface AdvertisingPresenter {

    interface View{
        fun advertisingResult(advertisings: List<Advertising>)
    }

    fun loadAdvertising()
}