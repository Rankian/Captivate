package com.sanjie.captivate.mvp.impl

import android.content.Context
import com.sanjie.captivate.http.reactive.TransformUtils
import com.sanjie.captivate.mvp.model.Song
import com.sanjie.captivate.mvp.presenter.MusicPresenter
import com.sanjie.captivate.util.ScanMusicUtil
import com.sanjie.zy.utils.log.ZYLog
import io.reactivex.Observable

/**
 * Created by LangSanJie on 2017/5/3.
 */
class MusicPresenterImpl(val context: Context, view: MusicPresenter.View) : MusicPresenter {

    val musicView: MusicPresenter.View = view

    var scanner: ScanMusicUtil? = null

    override fun scannerMusic() {
        scanner = ScanMusicUtil(context)
        scanner!!
                .compose(TransformUtils.defaultNewThreadSchedulers())
                .subscribe {
                    musicView.scannerMusicResult(it)
                }
    }

}