package com.sanjie.captivate.mvp.impl

import com.sanjie.captivate.http.RetrofitManager
import com.sanjie.captivate.http.reactive.TransformUtils
import com.sanjie.captivate.mvp.presenter.DownloadFilePresenter
import com.sanjie.captivate.util.FileUtils
import com.sanjie.zy.utils.log.ZYLog

/**
 * Created by SanJie on 2017/5/12.
 */
class DownloadFilePresenterImpl(val downloadView: DownloadFilePresenter.View) : DownloadFilePresenter {

    override fun download(url: String) {
        RetrofitManager.getInstance()
                .downloadApi()
                .download(url)
                .compose(TransformUtils.defaultNewThreadSchedulers())
                .subscribe {
                    FileUtils.saveFile(it,"/20170512.apk",downloadView)
                }
    }
}