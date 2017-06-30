package com.sanjie.captivate.mvp.impl

import cn.bmob.v3.BmobQuery
import com.sanjie.captivate.mvp.model.Travel
import com.sanjie.captivate.mvp.presenter.TravelPresenter

/**
 * Created by SanJie on 2017/5/11.
 */
class TravelPresenterImpl(val travelView: TravelPresenter.View): TravelPresenter {

    override fun loadTravel() {
        BmobQuery<Travel>()
                .order("-createdAt")
                .findObjectsObservable(Travel::class.java)
                .subscribe {
                    travelView.travelResult(it)
                }
    }
}