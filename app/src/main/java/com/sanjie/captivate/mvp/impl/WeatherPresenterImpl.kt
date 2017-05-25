package com.sanjie.captivate.mvp.impl

import com.sanjie.captivate.http.RetrofitManager
import com.sanjie.captivate.http.reactive.TransformUtils
import com.sanjie.captivate.mvp.model.Weather
import com.sanjie.captivate.mvp.presenter.WeatherPresenter
import com.sanjie.zy.utils.log.ZYLog
import com.sanjie.zy.widget.ZYToast

/**
 * Created by LangSanJie on 2017/4/27.
 */
class WeatherPresenterImpl(weatherView: WeatherPresenter.View) : WeatherPresenter {

    var weatherView: WeatherPresenter.View? = weatherView

    override fun loadWeather(cityName: String) {
        RetrofitManager.getInstance().getWeatherApi()
                .loadWeather(cityName, "057533f70adfa2ac63765b88fc5dcb6a")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe {
                    when (it.optString("resultcode")){
                        "200" -> {
                            val result = it.optJSONObject("result")
                            val sk = result.optJSONObject("sk")
                            val today = result.optJSONObject("today")
                            val weather = Weather(sk.optString("temp"), sk.optString("humidity"), sk.optString("time"),
                                    today.optString("temperature"), today.optString("weather"), today.optString("dressing_advice"), today.optString("dressing_index"),
                                    today.optString("date_y"),sk.optString("wind_direction"), sk.optString("wind_strength"),
                                    result.optJSONObject("future"))
                            weatherView!!.WeatherResult(weather)
                        }
                        else -> {
                            ZYToast.error("加载天气信息失败")
                        }
                    }
                }
    }
}