package com.sanjie.captivate.mvp.view

import com.sanjie.captivate.mvp.model.News

/**
 * Created by LangSanJie on 2017/4/18.
 */
interface NewsView {

    fun onResult(newsList: List<News>)
}