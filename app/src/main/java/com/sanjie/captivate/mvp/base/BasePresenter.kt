package com.sanjie.captivate.mvp.base

/**
 * Created by SanJie on 2017/6/28.
 */
abstract class BasePresenter<T> {

    var view: T? = null

    fun onAttach(view: T?){
        this.view = view
    }

    fun onDetach(){
        this.view = null
    }

}