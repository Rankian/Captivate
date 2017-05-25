package com.sanjie.captivate.mvp.model

import cn.bmob.v3.BmobObject

/**
 * Created by SanJie on 2017/5/11.
 */
class Travel: BmobObject {

    var photoName: String? = null
    var photoUrl: String? = null
    var downloadUrl: String? = null

    constructor(photoName: String?, photoUrl: String?, downloadUrl: String?) {
        this.photoName = photoName
        this.photoUrl = photoUrl
        this.downloadUrl = downloadUrl
    }
}