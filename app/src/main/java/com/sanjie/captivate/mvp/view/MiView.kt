package com.sanjie.captivate.mvp.view

import com.sanjie.captivate.mvp.base.BaseView

/**
 * Created by SanJie on 2017/6/28.
 */
interface MiView : BaseView{

    fun getData(data: String)

    fun getDataFail(msg: String)
}