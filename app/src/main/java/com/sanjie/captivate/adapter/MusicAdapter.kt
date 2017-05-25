package com.sanjie.captivate.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.sanjie.captivate.R
import com.sanjie.captivate.common.AppConfig
import com.sanjie.captivate.mvp.model.Song
import com.sanjie.captivate.view.ItemTouchHelperAdapter
import com.sanjie.zy.adpter.ZYRecyclerViewAdapter
import com.sanjie.zy.adpter.ZYViewHolder
import com.sanjie.zy.utils.ZYDateUtils
import com.sanjie.zy.utils.ZYEmptyUtils
import com.sanjie.zy.widget.view.ZYCircleImageView
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by SanJie on 2017/5/8.
 */
class MusicAdapter(val context: Context, mRecyclerView: RecyclerView, dataList: MutableList<Song>) :
        ZYRecyclerViewAdapter<Song>(mRecyclerView, dataList, R.layout.item_home_music_list), ItemTouchHelperAdapter {

    override fun bindData(holder: ZYViewHolder?, data: Song?, position: Int) {
        holder!!.setText(R.id.item_music_title_tv, if (ZYEmptyUtils.isEmpty(data!!.title)) "未知歌曲" else data.title)
                .setText(R.id.item_music_artist_tv, if (ZYEmptyUtils.isEmpty(data.artist)) "未知" else data.artist)
                .setText(R.id.item_music_duration_tv, if (ZYEmptyUtils.isEmpty(data.duration)) "00:00" else ZYDateUtils.millis2String(data.duration!!, "mm:ss"))
        Glide.with(context).load(data.coverUri).into(holder.getView<View>(R.id.item_music_cover_iv) as ZYCircleImageView)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(dataLists, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDelete(position: Int) {
        //删除数据
        dataLists.remove(dataLists!![position])
        notifyItemRemoved(position)
    }
}