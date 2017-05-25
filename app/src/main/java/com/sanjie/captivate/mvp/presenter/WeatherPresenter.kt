package com.sanjie.captivate.mvp.presenter

import com.sanjie.captivate.mvp.model.Weather

/**
 * Created by LangSanJie on 2017/4/27.
 */
interface WeatherPresenter {

    interface View{
        fun WeatherResult(weather: Weather)
    }

    fun loadWeather(cityName: String)
}