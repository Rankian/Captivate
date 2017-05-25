package com.sanjie.captivate.mvp.impl

import cn.bmob.v3.BmobQuery
import com.sanjie.captivate.mvp.model.Advertising
import com.sanjie.captivate.mvp.presenter.AdvertisingPresenter

/**
 * Created by LangSanJie on 2017/5/5.
 */
class AdvertisingPresenterImpl(var view: AdvertisingPresenter.View): AdvertisingPresenter {

    override fun loadAdvertising() {
        val query = BmobQuery<Advertising>()
        query.findObjectsObservable(Advertising::class.java)
                .subscribe {
                    view.advertisingResult(it)
                }
    }
}