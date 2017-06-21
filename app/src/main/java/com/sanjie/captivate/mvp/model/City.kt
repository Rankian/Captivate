package com.sanjie.captivate.mvp.model

import com.sanjie.captivate.view.bean.BaseIndexPinYinBean

/**
 * Created by SanJie on 2017/6/20.
 */
class City : BaseIndexPinYinBean {

    var city: String? = null

    constructor()

    constructor(city: String?) {
        this.city = city
    }

    override fun getTarget(): String {
        return city!!
    }

}