package com.sanjie.captivate.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseFragment
import com.sanjie.captivate.mvp.impl.WeatherPresenterImpl
import com.sanjie.captivate.mvp.model.Weather
import com.sanjie.captivate.mvp.presenter.WeatherPresenter
import com.sanjie.captivate.util.AMapUtils
import kotlinx.android.synthetic.main.fragment_weather.*

/**
 * Created by LangSanJie on 2017/4/27.
 */
class WeatherFragment : BaseFragment(), WeatherPresenter.View {

    companion object {
        val TAG = "WeatherFragment"
    }

    var weatherPresenter: WeatherPresenter? = null

    override fun initView() {
        swipe_refresh_layout.setOnRefreshListener {
            startLocation()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_weather
    }

    override fun processLogic() {
        weatherPresenter = WeatherPresenterImpl(this)
        startLocation()
    }

    fun startLocation() {
        AMapUtils().with(this)
                .startLocation()
                .subscribe {
                    val location = it.province + it.city + it.district + it.street + it.streetNum + it.aoiName
                    val address = it.address
                    weather_city_name_tv.text = it.district
                    weather_address_tv.text = location
                    //定位成功，加载天气
                    weatherPresenter!!.loadWeather(it.city)
                }
    }

    override fun WeatherResult(weather: Weather) {

        if (swipe_refresh_layout.isRefreshing) {
            swipe_refresh_layout.isRefreshing = false
        }

        weather_info_tv.text = weather.weather
        weather_air_quality_tv.text = weather.dressing_index
        weather_humidity_tv.text = weather.humidity
        weather_update_time_tv.text = weather.time
        weather_today_time_tv.text = weather.date_y
        weather_temp_tv.text = weather.temp + "℃"
        weather_temp_low_tv.text = weather.temperature.split("~")[0]
        weather_temp_hight_tv.text = weather.temperature.split("~")[1]
        weather_dressing_advice_tv.text = weather.dressing_advice

    }
}