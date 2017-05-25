package com.sanjie.captivate.mvp.presenter

import com.sanjie.captivate.mvp.model.User

/**
 * Created by LangSanJie on 2017/4/19.
 */
interface UserInitPresenter {

    interface LoginView {
        fun onLoginResult(user: User?, e: Exception?)
    }

    interface VerifyView{
        fun onVerifyResult(exist: Boolean)
    }

    interface RegisterView {
        fun onRegisterResult(user: User?, e: Exception?)
    }

    fun register(phone: String, password: String, nickname: String, email: String)

    fun login(username: String, password: String)

    fun verify(phone: String)
}