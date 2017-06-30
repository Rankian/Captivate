package com.sanjie.captivate.mvp.presenter

import com.sanjie.captivate.mvp.base.BasePresenter
import com.sanjie.captivate.mvp.view.MiView


/**
 * Created by SanJie on 2017/6/28.
 */
class MiPresenter : BasePresenter<MiView>(){

    fun getGankData(){
        view!!.getData("1")
        view!!.getDataFail("0")
    }

}