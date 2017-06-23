package com.sanjie.captivate.ui.activity.video

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.sanjie.captivate.R
import com.sanjie.captivate.adapter.VideoAdapter
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.mvp.impl.VideoPresenterImpl
import com.sanjie.captivate.mvp.model.Video
import com.sanjie.captivate.mvp.presenter.VideoPresenter
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import kotlinx.android.synthetic.main.activity_video.*

/**
 * Created by SanJie on 2017/6/23.
 */
class VideoActivity : BaseActivity(), VideoPresenter.View {

    companion object {
        val TAG = "VideoActivity"
    }

    var videoList: ArrayList<Video>? = null
    var videoAdapter: VideoAdapter? = null

    var videoPresenter: VideoPresenter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_video
    }

    override fun initView() {
        ZYStatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.main_color), 0)

        videoList = ArrayList()
        videoAdapter = VideoAdapter(this, video_recycler_view, videoList!!)
        videoAdapter!!.isLoadMore(false)
        videoAdapter!!.setOnItemClickListener { view, i ->
            val video = videoList!![i]
            val bundle = Bundle()
            bundle.putSerializable("video", video)
            forwardActivity(VideoPlayActivity::class.java, bundle)
        }
        video_recycler_view.layoutManager = LinearLayoutManager(this)
        video_recycler_view.adapter = videoAdapter
    }

    override fun getToolBarTitle(): String? {
        return "视频"
    }

    override fun processLogic() {
        videoPresenter = VideoPresenterImpl(this)
        videoPresenter!!.loadVideo()
    }

    override fun onVideoResult(videos: List<Video>) {
        videoList!!.addAll(videos)
        videoAdapter!!.notifyDataSetChanged()
    }
}