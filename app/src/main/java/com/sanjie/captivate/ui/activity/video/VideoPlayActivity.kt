package com.sanjie.captivate.ui.activity.video

import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.mvp.model.Video
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import kotlinx.android.synthetic.main.activity_video_play.*

/**
 * Created by SanJie on 2017/6/23.
 */
class VideoPlayActivity : BaseActivity() {

    companion object{
        val TAG = "VideoPlayActivity"
    }

    var video: Video? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_video_play
    }

    override fun initView() {
        ZYStatusBarUtil.translucentStatusBar(this)
        video = intent.getSerializableExtra("video") as Video
        video_play_video_view.startPlayVideo(video)
    }

    override fun getToolBarTitle(): String? {
        return null
    }

    override fun hasToolbar(): Boolean {
        return false
    }

    override fun isSupportSwipeBack(): Boolean {
        return false
    }

    override fun processLogic() {
    }

    override fun onDestroy() {
        super.onDestroy()
        video_play_video_view.onDestroy()
    }
}