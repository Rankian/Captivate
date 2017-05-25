package com.sanjie.captivate.ui.fragment.home

import android.media.MediaPlayer
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.sanjie.captivate.R
import com.sanjie.captivate.adapter.MusicAdapter
import com.sanjie.captivate.base.BaseFragment
import com.sanjie.captivate.common.PlayConfig
import com.sanjie.captivate.http.reactive.TransformUtils
import com.sanjie.captivate.mvp.impl.MusicPresenterImpl
import com.sanjie.captivate.mvp.model.Song
import com.sanjie.captivate.mvp.presenter.MusicPresenter
import com.sanjie.captivate.view.ItemTouchHelperCallback
import com.sanjie.zy.utils.ACache
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_music_content.*
import kotlinx.android.synthetic.main.ic_home_play_music_layout.*
import kotlinx.android.synthetic.main.include_fragment_title_bar.*
import java.util.concurrent.TimeUnit

/**
 * Created by LangSanJie on 2017/4/27.
 */
class MusicFragment : BaseFragment(), MusicPresenter.View {

    var musicPresenter: MusicPresenter? = null

    var linearLayoutManager: LinearLayoutManager? = null

    var songList: ArrayList<Song>? = null
    var mAdapter: MusicAdapter? = null

    //recyclerView 是否在滚动
    var isMove: Boolean = false

    var isPlay = false
    var playPosition = 0
    val mPlayer = MediaPlayer()
    var lastPlayPosition: Int? = null

    /*
    Disposable
    切断 Observable
     */
    var disposable: Disposable? = null

    //ItemTouchHelper
    var callback: ItemTouchHelper.Callback? = null
    var touchHelper: ItemTouchHelper? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_music
    }

    override fun initView() {

        fragment_title_tv.text = "我的音乐"

        songList = ArrayList<Song>()
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        mAdapter = MusicAdapter(activity, recyclerView!!, songList!!)
        mAdapter!!.isLoadMore(false)
        recyclerView.adapter = mAdapter

        callback = ItemTouchHelperCallback(mAdapter!!)
        //用Callback构造ItemtouchHelper
        touchHelper = ItemTouchHelper(callback)
        touchHelper!!.attachToRecyclerView(recyclerView)

        mAdapter!!.setOnItemClickListener { v, position ->

            /*
            当前有歌曲在播放，切断进度条
            */
            if (disposable != null) {
                stopPlay()
            }
            playPosition = position
            //设置当前播放为true
            isPlay = true
            //播放当前歌曲
            play(playPosition)
        }

        if (ACache.get(activity).getAsObject(PlayConfig.PLAY_LASY_POSITION) != null) {
            lastPlayPosition = ACache.get(activity).getAsObject(PlayConfig.PLAY_LASY_POSITION) as Int?
        }
    }

    fun clicks(view: View) {
        RxView.clicks(view)
                .subscribe {
                    when (view) {
                        home_play_bar_play_btn -> {
                            home_play_bar_play_btn.setImageResource(if (isPlay) R.mipmap.icon_pause else R.mipmap.icon_play)
                            isPlay = !isPlay
                            if (isPlay) {
                                mPlayer.pause()
                            } else {
                                mPlayer.start()
                            }
                        }
                        home_play_bar_next_btn -> playNext()
                    }
                }
    }

    override fun onViewClick() {
        super.onViewClick()
        clicks(home_play_bar_play_btn)
        clicks(home_play_bar_next_btn)
    }

    override fun processLogic() {
        musicPresenter = MusicPresenterImpl(activity, this)
        musicPresenter!!.scannerMusic()
    }

    override fun scannerMusicResult(list: List<Song>) {
        songList!!.clear()
        songList!!.addAll(list)
        mAdapter!!.notifyDataSetChanged()
        if (lastPlayPosition != null && lastPlayPosition!! > 0) {
            //记录上次播放歌曲位置，并移动到该位置
            moveToPosition(lastPlayPosition!!)
            showSongInPlayBar(lastPlayPosition!!)
        }
    }

    fun moveToPosition(position: Int) {
        val firstItem = linearLayoutManager!!.findFirstVisibleItemPosition()
        val lastItem = linearLayoutManager!!.findLastVisibleItemPosition()

        if (position <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView!!.scrollToPosition(position)
        } else if (position <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            val top = recyclerView.getChildAt(position - firstItem).top
            recyclerView!!.scrollBy(0, top)
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView.scrollToPosition(position)
            isMove = true
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isMove) {
                    isMove = false
                    val position = lastPlayPosition!! - linearLayoutManager!!.findFirstVisibleItemPosition()
                    if (0 <= position && position < recyclerView!!.childCount) {
                        //获取要置顶的项顶部离RecyclerView的距离
                        val top = recyclerView!!.getChildAt(position).top
                        //最后的移动
                        recyclerView.scrollBy(0, top)
                    }
                }
            }
        })
    }

    fun savePlayPosition(position: Int) {
        ACache.get(activity).put(PlayConfig.PLAY_LASY_POSITION, position)
    }

    fun play(position: Int) {
        showSongInPlayBar(position)
        //保存当前播放歌曲位置
        savePlayPosition(position)
        startAnimation()
        val song = songList!![position]
        mPlayer.reset()
        mPlayer.setDataSource(song.uri)
        mPlayer.prepareAsync()
        mPlayer.setOnPreparedListener {
            start(song)
        }
    }

    fun playNext() {
        /*
        当前有歌曲在播放，切断进度条
        */
        if (disposable != null) {
            stopPlay()
        }
        isPlay = true

        home_play_bar_progress.progress = 0

        playPosition++
        if (playPosition == songList!!.size - 1) {
            playPosition = 0
        }
        play(playPosition)
        home_play_bar_play_btn!!.setImageResource(if (isPlay) R.mipmap.icon_pause else R.mipmap.icon_play)
    }

    fun showSongInPlayBar(position: Int) {
        val song = songList!![position]
        Glide.with(activity).load(song.coverUri).error(R.mipmap.default_avatar).into(home_play_bar_cover_iv)
        home_play_bar_title_tv.text = song.title
        home_play_bar_artist_tv.text = song.artist

        home_play_bar_play_btn.setImageResource(if (isPlay) R.mipmap.icon_pause else R.mipmap.icon_play)

        home_play_bar_progress.progress = 0
    }

    fun start(song: Song) {
        mPlayer.start()
        disposable = Observable.interval(100, TimeUnit.MILLISECONDS)
                .compose(TransformUtils.defaultNewThreadSchedulers())
                .subscribe {
                    val progress = mPlayer.currentPosition.toDouble() / song.duration!! * 100
                    if (mPlayer.isPlaying) {
                        home_play_bar_progress.progress = progress.toInt()
                    } else if (!mPlayer.isPlaying && progress.toInt() == 99) {
                        //顺利播放完成，自动播放下一首
                        playNext()
                    }
                }
    }

    fun stopPlay() {
        disposable!!.dispose()
    }

    fun startAnimation() {
        val rotateAnim = AnimationUtils.loadAnimation(activity, R.anim.play_bar_cover_rotate)
        rotateAnim.interpolator = LinearInterpolator()//不停顿
        rotateAnim.fillAfter = true
        home_play_bar_cover_iv.startAnimation(rotateAnim)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayer.stop()
    }

}