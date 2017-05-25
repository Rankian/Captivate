package com.sanjie.captivate.util

import android.util.Log
import com.sanjie.captivate.common.AppConfig
import com.sanjie.captivate.mvp.presenter.DownloadFilePresenter
import okhttp3.ResponseBody
import java.io.*

/**
 * Created by SanJie on 2017/5/12.
 */
class FileUtils {

    companion object {
        fun saveFile(body: ResponseBody, fileName: String, downloadView: DownloadFilePresenter.View) {
            var `is`: InputStream? = null
            val buf = ByteArray(2048)
            var len: Int
            var fos: FileOutputStream? = null
            try {
                `is` = body.byteStream()
                val dir = File(AppConfig.FILE_STORE_BASE_PATH + "photo")
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val file = File(dir, fileName)
                fos = FileOutputStream(file)
                len = `is`!!.read(buf)
                while (len != -1) {
                    fos.write(buf, 0, len)
                }
                fos.flush()
                //onCompleted();
                downloadView.downloadSuccess(file)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    if (`is` != null) `is`.close()
                    if (fos != null) fos.close()
                } catch (e: IOException) {
                    Log.e("saveFile", e.message)
                }

            }
        }
    }
}