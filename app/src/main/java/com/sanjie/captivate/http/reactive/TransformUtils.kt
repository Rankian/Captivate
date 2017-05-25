package com.sanjie.captivate.http.reactive

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by LangSanJie on 2017/4/13.
 */
class TransformUtils {

    companion object{
        fun <T> defaultSchedulers() : ObservableTransformer<T, T>{
            return ObservableTransformer {
                upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }

        fun <T> defaultNewThreadSchedulers() : ObservableTransformer<T, T>{
            return ObservableTransformer {
                upstream ->
                upstream.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }

}