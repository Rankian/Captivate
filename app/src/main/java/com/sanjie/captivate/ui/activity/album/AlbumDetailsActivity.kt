package com.sanjie.captivate.ui.activity.album

import com.bumptech.glide.Glide
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.common.AppConfig
import com.sanjie.captivate.mvp.model.Album
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import kotlinx.android.synthetic.main.activity_album_details.*

class AlbumDetailsActivity : BaseActivity() {

    companion object Intent{
        val EXTRA: String = "album"
    }

    var album: Album? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_album_details
    }

    override fun initView() {
        setToolBarLayout()
        ZYStatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.main_color), 0)
    }

    override fun getToolBarTitle(): String? {
        return "姿谊"
    }

    override fun processLogic() {
//        album = intent.extras[EXTRA] as Album?
//        Glide.with(this@AlbumDetailsActivity).load(album!!.url).centerCrop().into(album_detail_iv)
        Glide.with(this@AlbumDetailsActivity).load(AppConfig.IMAGE_URL).centerCrop().into(album_detail_iv)
    }

    private fun setToolBarLayout(){
        toolbar_layout.isTitleEnabled = false
    }
}
