package com.sanjie.captivate.http.reactive

import com.sanjie.zy.utils.ZYNetworkUtils
import com.sanjie.zy.utils.log.ZYLog
import com.sanjie.zy.widget.ZYToast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 * Created by LangSanJie on 2017/4/13.
 */
open class MObserver<T> : Observer<T>{

    companion object T{
        val TAG: String = "MObserver"
    }

    override fun onSubscribe(d: Disposable?) {
        ZYLog.d("onSubscribe")
        if(!ZYNetworkUtils.isConnected()) ZYToast.error("请连接网络或稍后重试...")
    }

    override fun onNext(t: T) {
        ZYLog.json(t.toString())
    }

    override fun onComplete() {
    }

    override fun onError(e: Throwable?) {
        ZYLog.d(e!!.message)
    }

}