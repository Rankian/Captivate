package com.sanjie.captivate.adapter

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by SanJie on 2017/6/27.
 */
class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatHolder>(){

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: ChatHolder?, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChatHolder {
        return ChatHolder(parent!!.rootView)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    inner class ChatHolder(val itemView: View) : RecyclerView.ViewHolder(itemView){
        var contentTv: AppCompatTextView? = null
    }
}