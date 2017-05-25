package com.sanjie.captivate.mvp.impl

import android.content.Context
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UploadFileListener
import com.sanjie.captivate.mvp.model.Dynamic
import com.sanjie.captivate.mvp.presenter.DynamicPresenter
import com.sanjie.zy.utils.ZYCompressHelper
import com.sanjie.zy.utils.log.ZYLog
import com.sanjie.zy.widget.ZYToast
import io.reactivex.rxkotlin.toObservable
import java.io.File

/**
 * Created by SanJie on 2017/5/22.
 */
class DynamicPresenterImpl : DynamicPresenter {

    var context: Context? = null
    var publishView: DynamicPresenter.PublishView? = null
    var loadView: DynamicPresenter.LoadView? = null

    constructor(context: Context, publishView: DynamicPresenter.PublishView?) {
        this.context = context
        this.publishView = publishView
    }

    constructor(loadView: DynamicPresenter.LoadView?) {
        this.loadView = loadView
    }

    constructor()

    override fun publish(dynamic: Dynamic) {

        uploadPhoto(dynamic)
    }

    override fun load(limit: Int) {
        val query = BmobQuery<Dynamic>()
        query.setLimit(limit)
        query.setSkip(limit - 10)
        query.order("-createdAt")
        query.findObjectsObservable(Dynamic::class.java)
                .subscribe {
                    ZYLog.d("DynamicPresenterImpl：${it.size}")
                    loadView!!.loadResult(it)
                }
    }

    private fun uploadPhoto(dynamic: Dynamic) {

        val urls = ArrayList<String>()

        var index = 0

        if (dynamic.photos!!.size > 0) {
            dynamic.photos!!.toObservable()
                    .subscribe {
                        val file = ZYCompressHelper.getDefault(context).compressToFile(File(it))
                        val uploadFile = BmobFile(file)
                        uploadFile.uploadblock(object : UploadFileListener() {
                            override fun done(e: BmobException?) {
                                when (e == null) {
                                    true -> {
                                        index++
                                        urls.add(uploadFile.fileUrl)
                                        if (index == dynamic.photos!!.size) {
                                            dynamic.photos = urls
                                            dynamicPublish(dynamic)
                                        }
                                    }
                                    else -> {

                                    }
                                }
                            }
                        })
                    }
        }else{
            dynamicPublish(dynamic)
        }
    }

    private fun dynamicPublish(dynamic: Dynamic) {
        dynamic.save(object : SaveListener<String>() {
            override fun done(objectId: String?, e: BmobException?) {
                when (e == null) {
                    true -> {
                        publishView!!.onPublishResult(objectId!!)
                    }
                    else -> {
                        ZYToast.warning("怎么回事，居然没发出去")
                    }
                }
            }
        })
    }
}