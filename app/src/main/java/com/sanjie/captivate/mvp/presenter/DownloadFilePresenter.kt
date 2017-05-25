package com.sanjie.captivate.mvp.presenter

import java.io.File

/**
 * Created by SanJie on 2017/5/12.
 */
interface DownloadFilePresenter {

    interface View{
        fun downloadSuccess(file: File)
    }

    fun download(url: String)
}