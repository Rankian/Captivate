package com.sanjie.captivate.ui.fragment.home

import android.support.v7.widget.AppCompatTextView
import android.view.View.GONE
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseFragment
import com.sanjie.captivate.mvp.impl.AdvertisingPresenterImpl
import com.sanjie.captivate.mvp.impl.WeatherPresenterImpl
import com.sanjie.captivate.mvp.model.Advertising
import com.sanjie.captivate.mvp.model.Weather
import com.sanjie.captivate.mvp.presenter.AdvertisingPresenter
import com.sanjie.captivate.mvp.presenter.WeatherPresenter
import com.sanjie.captivate.util.AMapUtils
import com.sanjie.captivate.util.GlideImageLoader
import com.sanjie.zy.utils.ZYDateUtils
import com.sanjie.zy.utils.log.ZYLog
import com.sanjie.zy.widget.ZYToast
import com.sanjie.zy.widget.banner.BannerConfig
import com.sanjie.zy.widget.banner.Transformer
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import kotlinx.android.synthetic.main.fragment_discovery.*
import kotlinx.android.synthetic.main.include_fragment_title_bar.*
import kotlinx.android.synthetic.main.include_weather_layout.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by SanJie on 2017/5/11.
 */
class DiscoveryFragment : BaseFragment(), AdvertisingPresenter.View, WeatherPresenter.View {

    companion object {
        val TAG = "DiscoveryFragment"
    }

    var advertisingPresenter: AdvertisingPresenter? = null
    var weatherPresenter: WeatherPresenter? = null

    val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.CHINA)

    var locationCity = ""

    override fun getLayoutId(): Int {
        return R.layout.fragment_discovery
    }

    override fun initView() {
        fragment_title_tv.text = "发现"

        discovery_swipe_refresh_layout.setColorSchemeResources(R.color.main_color)
        discovery_swipe_refresh_layout.setOnRefreshListener {
            startLocation()
        }
    }

    override fun processLogic() {
        advertisingPresenter = AdvertisingPresenterImpl(this)
        advertisingPresenter!!.loadAdvertising()
        weatherPresenter = WeatherPresenterImpl(this)

        startLocation()
    }

    fun startLocation() {
        AMapUtils().with(this)
                .startLocation()
                .subscribe {
                    locationCity = it.city
                    //显示地址信息
                    discovery_weather_location_tv.text = it.district + "\u3000" + it.street
                    weatherPresenter!!.loadWeather(locationCity)
                }
    }

    fun initBanner(advertising: List<String>) {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner.setIndicatorGravity(BannerConfig.RIGHT)
        banner.setBannerAnimation(Transformer.ZoomIn)
        banner.setImageLoader(GlideImageLoader())
        banner.setImages(advertising)
        banner.setDelayTime(5000)
        banner.setOnBannerListener {
            ZYToast.success("$it")
        }
        banner!!.start()
    }

    override fun advertisingResult(advertisings: List<Advertising>) {
        val urls: MutableList<String> = ArrayList()
        Observable.fromIterable(advertisings)
                .subscribe {
                    urls.add(it.url)
                }
        initBanner(urls)
    }

    override fun WeatherResult(weather: Weather) {

        if (discovery_swipe_refresh_layout.isRefreshing)
            discovery_swipe_refresh_layout.isRefreshing = false

        discovery_weather_temp_tv.text = weather.temp + "°"
        discovery_weather_info_tv.text = weather.weather
        discovery_weather_dressing_tv.text = weather.dressing_index
        discovery_weather_wind_direction_tv.text = weather.wind_direction
        discovery_weather_wind_strength_tv.text = weather.wind_strength
        discovery_weather_humidity_tv.text = weather.humidity
        discovery_weather_soma_temp_tv.text = (weather.temp.toInt() - 1).toString() + "°"

        showFutureWeather(weather.future!!)
    }

    fun showFutureWeather(futureObject: JSONObject) {
        /*
        获取未来三天日期
        key
         */
        val keys = ArrayList<String>()
        futureObject.keys().toObservable()
                .take(3)
                .subscribe {
                    keys.add(it)
                }

        discovery_weather_future_list_view.removeAllViews()
        for (i in 0..2) {
            val view = activity.layoutInflater.inflate(R.layout.item_discovery_future_weather, null)
            val dateTv = view.findViewById<AppCompatTextView>(R.id.item_future_weather_date_tv)
            val infoTv = view.findViewById<AppCompatTextView>(R.id.item_future_weather_info_tv)
            val tempTv = view.findViewById<AppCompatTextView>(R.id.item_future_weather_temp_tv)
            val underLine = view.findViewById<AppCompatTextView>(R.id.item_future_weather_under_line)

            val `object` = futureObject.optJSONObject(keys[i])

            when (i) {
                0 -> {
                    dateTv.text = "今天"
                }
                1 -> {
                    dateTv.text = "明天"
                }
                2 -> {
                    dateTv.text = "周" + `object`.optString("week").substring(2, 3)
                    underLine.visibility = GONE
                }
            }
            infoTv.text = `object`.optString("weather")
            tempTv.text = `object`.optString("temperature")

            discovery_weather_future_list_view.addView(view)
        }
    }
}