package com.sanjie.captivate.mvp.model

import org.json.JSONObject

/**
 * Created by LangSanJie on 2017/4/27.
 */
class Weather {

    var temp: String = ""
    var humidity: String = ""
    var time: String = ""
    var temperature: String = ""
    var weather: String = ""
    var dressing_advice: String = ""
    var dressing_index: String = ""
    var date_y: String = ""
    var wind_direction: String = ""
    var wind_strength: String = ""

    var future: JSONObject? = null

    constructor(temp: String, humidity: String, time: String,
                temperature: String, weather: String, dressing_advice: String,
                dressing_index: String,date_y: String, wind_direction: String,
                wind_strength: String, future: JSONObject?) {
        this.temp = temp
        this.humidity = humidity
        this.time = time
        this.temperature = temperature
        this.weather = weather
        this.dressing_advice = dressing_advice
        this.dressing_index = dressing_index
        this.date_y = date_y
        this.wind_direction = wind_direction
        this.wind_strength = wind_strength
        this.future = future
    }

    constructor()


}