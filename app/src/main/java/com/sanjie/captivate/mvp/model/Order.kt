package com.sanjie.captivate.mvp.model

import com.google.gson.Gson
import org.json.JSONObject

/**
 * 订单
 * Created by SanJie on 2017/5/7.
 */
class Order {

    var id: Int? = null
    var orderNo: String? = null
    var orderDate: String? = null
    var shopName: String? = null
    var money: Double? = null

    constructor(id: Int?, orderNo: String?, orderDate: String?, shopName: String?, money: Double?) {
        this.id = id
        this.orderNo = orderNo
        this.orderDate = orderDate
        this.shopName = shopName
        this.money = money
    }

    fun getInstanceFromJSONObject(jsonObject: JSONObject): Order {
        return Gson().fromJson(jsonObject.toString(), Order::class.java)
    }

    fun toJSONString(): String {
        return Gson().toJson(this)
    }
}