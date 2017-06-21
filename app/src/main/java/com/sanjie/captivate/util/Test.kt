package com.sanjie.captivate.util

import android.os.Handler
import android.os.Message

import com.sanjie.zy.http.download.DownloadInfo
import com.sanjie.zy.http.listener.HttpProgressOnNextListener

import java.util.ArrayList

import io.reactivex.Flowable

/**
 * Created by LangSanJie on 2017/5/3.
 */

class Test {

    internal var a = arrayOf("art")

    internal var strings: MutableList<String> = ArrayList()

    fun add() {
        strings.add("a")
    }

    val instance: Test? = null

    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {

            }
        }
    }

    internal inner class MThread : Thread() {
        override fun run() {
            super.run()
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            handler.sendEmptyMessage(1)
        }
    }

    private fun Start() {
        val a = 10
        val b = 2
        val c = a / b

        val info = DownloadInfo("")
        info.listener = listener

    }

    internal var listener: HttpProgressOnNextListener<*> = object : HttpProgressOnNextListener<DownloadInfo>() {
        override fun onNext(info: DownloadInfo) {

        }

        override fun onStart() {

        }

        override fun onComplete() {

        }

        override fun updateProgress(l: Long, l1: Long) {

        }
    }
}
