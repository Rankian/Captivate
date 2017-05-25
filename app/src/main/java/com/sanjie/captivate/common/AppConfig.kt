package com.sanjie.captivate.common

import android.os.Environment

/**
 * Created by LangSanJie on 2017/4/13.
 */
class AppConfig {
    companion object{

        val IS_DEBUG = true

        val APP_NAME = "三杰"

        val API_URL: String = "http://v.juhe.cn/"

        val BMOB_APP_ID = "a5092f2a1dc4b1c36ef9987c4c7197df"

        val FILE_STORE_BASE_PATH: String = Environment.getExternalStorageDirectory().toString() + "/" + APP_NAME + "/"

        val AMAP_APP_KEY = "1a39767895eceeec4d1f974f72311a8e"

        val IMAGE_URL: String = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492404825068&di=432e94dedd8cfaafa5481c3f3ae8b594&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2015%2F110%2F10%2FCTZ4405554FX.jpg"
        val NAVI_IMAGE_URL: String = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493726685692&di=8fc1f0e0cf050277aa56110b5ea654de&imgtype=0&src=http%3A%2F%2Fp3.gexing.com%2Fshaitu%2F2011%2F11%2F19%2F13374ec740a0c3414.jpg"
        val DEFAULT_SONG_URI: String = "http://www.itouxiang.net/uploads/allimg/140423/1_140423083632_1.jpg"

        val IS_FIRST_LAUNCH_APP = "isFirst"
    }
}