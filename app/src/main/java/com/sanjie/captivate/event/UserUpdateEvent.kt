package com.sanjie.captivate.event

import com.sanjie.captivate.mvp.model.User

/**
 * Created by LangSanJie on 2017/4/27.
 */
class UserUpdateEvent{
    var user: User? = null

    constructor(user: User){
        this.user = user
    }

    constructor() : super()


}