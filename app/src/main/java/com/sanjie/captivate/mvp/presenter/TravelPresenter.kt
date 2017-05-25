package com.sanjie.captivate.mvp.presenter

import com.sanjie.captivate.mvp.model.Travel

/**
 * Created by SanJie on 2017/5/11.
 */
interface TravelPresenter {

    interface View{
        fun travelResult(travels: List<Travel>)
    }

    fun loadTravel()
}