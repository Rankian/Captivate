package com.sanjie.captivate.mvp.presenter

import com.sanjie.captivate.mvp.model.News

/**
 * Created by LangSanJie on 2017/4/18.
 */
interface NewsPresenter {

    interface View{
        fun onResult(newsList: List<News>)
    }

    fun load()
}