package com.sanjie.captivate.mvp.presenter

/**
 * Created by SanJie on 2017/5/26.
 */
interface RetrievePasswordPresenter {

    interface VerifyView{
        fun verifyPhoneResult(exist: Boolean)
    }

    interface RetrieveView {
        fun retrieveResult(e: Exception?)
    }

    fun verifyPhone(phone: String)

    fun retrievePassword(code: String, password: String)
}