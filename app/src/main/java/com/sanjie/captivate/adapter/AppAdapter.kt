package com.sanjie.captivate.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import android.widget.RatingBar
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.sanjie.captivate.R
import com.sanjie.captivate.common.AppConfig
import com.sanjie.captivate.mvp.model.App
import com.sanjie.captivate.util.LogUtils
import com.sanjie.zy.adpter.ZYRecyclerViewAdapter
import com.sanjie.zy.adpter.ZYViewHolder
import com.sanjie.zy.http.download.DownloadInfo
import com.sanjie.zy.http.download.DownloadRetrofitManager
import com.sanjie.zy.http.listener.HttpProgressOnNextListener
import com.sanjie.zy.http.reactive.TransformUtils
import io.reactivex.Observable

/**
 * Created by SanJie on 2017/6/9.
 */
class AppAdapter(val context: Context, recyclerView: RecyclerView, datas: ArrayList<App>) :
        ZYRecyclerViewAdapter<App>(recyclerView, datas, R.layout.item_app_list) {

    var downloadManager = DownloadRetrofitManager.getInstance()

    override fun bindData(holder: ZYViewHolder?, app: App?, position: Int) {

        Glide.with(context).load(app!!.icon).into(holder!!.getView<AppCompatImageView>(R.id.item_app_icon_iv))
        holder.getView<RatingBar>(R.id.item_app_score_rb).rating = app.score!!

        holder.setText(R.id.item_app_name_tv, app.name)
                .setText(R.id.item_app_software_tv, app.software)
                .setText(R.id.item_app_intro_tv, app.intro)

        val downloadProgressBar = holder.getView<ProgressBar>(R.id.item_app_download_progress_bar)

        val downInfo = DownloadInfo(app.downloadUrl)
        downInfo.savePath = AppConfig.FILE_STORE_BASE_PATH + "app/" + app.name + ".apk"
        downInfo.listener = object : HttpProgressOnNextListener<DownloadInfo>(){

            override fun onComplete() {

            }

            override fun updateProgress(readCount: Long, contentCount: Long) {
                val progress = readCount.toFloat() / contentCount.toFloat() * 100
                app.disposable = progressToObservable(progress.toInt())
                        .compose(TransformUtils.defaultNewThreadSchedulers())
                        .subscribe {
                            downloadProgressBar.progress = it
                        }
            }

            override fun onNext(apk: DownloadInfo?) {
            }

            override fun onStart() {

            }

        }

        val downloadBtn = holder.getView<AppCompatTextView>(R.id.item_app_operate_btn)

        RxView.clicks(downloadBtn)
                .subscribe {
                    when (app.status == null){
                        true -> {
                            downloadManager.startDownload(downInfo)
                            app.status = 1
                            downloadBtn.text = "暂停"
                        }
                        else -> {
                            when (app.status){
                                0 -> {
                                    //开始下载
                                    downloadManager.startDownload(downInfo)
                                    app.status = 1
                                    downloadBtn.text = "暂停"
                                }
                                1 -> {
                                    //暂停
                                    downloadManager.pause(downInfo)
                                    app.status = 0
                                    app.disposable!!.dispose()
                                    downloadBtn.text = "继续"
                                }
                            }
                        }
                    }
                }

    }

    fun progressToObservable(progress: Int): Observable<Int>{
        return Observable.create {
            it.onNext(progress)
        }
    }

}