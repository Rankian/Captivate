package com.sanjie.captivate.mvp.presenter

import com.sanjie.captivate.mvp.model.App

/**
 * Created by SanJie on 2017/6/9.
 */
interface AppPresenter {

    interface View {
        fun appResult(apps: List<App>)
    }

    fun loadApps(category: String)
}