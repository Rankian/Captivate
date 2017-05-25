package com.sanjie.captivate.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.sanjie.captivate.application.AppCache
import com.sanjie.captivate.http.reactive.TransformUtils
import com.sanjie.captivate.listener.OnPlayerEventListener
import com.sanjie.captivate.mvp.impl.MusicPresenterImpl
import com.sanjie.captivate.mvp.model.Song
import com.sanjie.captivate.mvp.presenter.MusicPresenter
import com.sanjie.zy.utils.ACache
import io.reactivex.Observable
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by SanJie on 2017/5/9.
 */
class PlayService : Service(), MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener, MusicPresenter.View {
    companion object {
        val TAG = "PlayService"
        val TIME_UPDATE = 100L

        fun startCommand(context: Context, action: String){
            val intent = Intent(context, PlayService::class.java)
            intent.action = action
            context.startService(intent)
        }
    }

    private var mMusicList: ArrayList<Song>? = null
    private val mPlayer: MediaPlayer = MediaPlayer()

    private var mAudioManager: AudioManager? = null
    private var mListener: OnPlayerEventListener? = null
    //正在播放的歌曲
    private var mPlayingSong: Song? = null
    //正在播放的歌曲序号
    private var mPlayingPosition: Int? = null
    private var isPausing: Boolean = false
    private var isPreparing: Boolean = false
    private var quitTimerRemain: Long? = null

    var musicPresenter: MusicPresenter? = null

    override fun onCreate() {
        super.onCreate()
        mMusicList = AppCache.getMusicList()
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        mPlayer.setOnCompletionListener(this)
    }

    override fun scannerMusicResult(list: List<Song>) {
        mMusicList!!.addAll(list)
        if (!mMusicList!!.isEmpty()) {
            mPlayingSong = if (mPlayingSong == null) mMusicList!![mPlayingPosition!!] else mPlayingSong
        }
    }

    /**
     * 扫描音乐
     */
    fun ScannerMusic() {
        musicPresenter = MusicPresenterImpl(applicationContext, this)
        musicPresenter!!.scannerMusic()
    }

    override fun onBind(intent: Intent?): IBinder {
        return PlayBinder()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.action != null){
        }
        return START_NOT_STICKY
    }

    override fun onCompletion(mp: MediaPlayer?) {

    }

    fun getOnPlayEventListener(): OnPlayerEventListener = mListener!!

    fun setOnPlayEventListener(listener: OnPlayerEventListener){
        mListener = listener
    }

    fun play(position: Int){
        if (mMusicList!!.isEmpty()){
            return
        }

        var mPosition = position

        if(mPosition < 0){
            mPosition = mMusicList!!.size - 1
        }else if(mPosition >= mMusicList!!.size){
            mPosition = 0
        }

        mPlayingPosition = mPosition
        val song = mMusicList!![mPlayingPosition!!]
        play(song)
    }

    fun play(song: Song){
        mPlayingSong = song
        try {
            mPlayer.reset()
            mPlayer.setDataSource(mPlayingSong!!.uri)
            mPlayer.prepareAsync()
            isPreparing = true
            mPlayer.setOnPreparedListener{
                isPreparing = false
                start()
            }
        }catch (e: IOException){

        }
    }

    override fun onAudioFocusChange(focusChange: Int) {

    }

    private fun start(){
        mPlayer.start()
        isPausing = false
        Observable.interval(100,TimeUnit.MILLISECONDS)
                .compose(TransformUtils.defaultNewThreadSchedulers())
                .subscribe {
                    if(isPlaying() && mListener != null){
                        mListener!!.onProgress(mPlayer.currentPosition)
                    }
                }
    }

    fun isPlaying(): Boolean = mPlayer.isPlaying

    inner class PlayBinder : Binder() {
        fun getService(): PlayService = this@PlayService
    }
}