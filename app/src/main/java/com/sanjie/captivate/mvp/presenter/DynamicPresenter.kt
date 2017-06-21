package com.sanjie.captivate.mvp.presenter

import com.sanjie.captivate.mvp.model.Dynamic

/**
 * Created by SanJie on 2017/5/22.
 */
interface DynamicPresenter {

    interface PublishView {
        fun onPublishResult(objectId: String)
    }

    interface LoadView {
        fun loadResult(dynamicList: List<Dynamic>)
    }

    interface LikeView{
        fun likeResult(likeCount: Int, dynamic: Dynamic)
    }

    fun publish(dynamic: Dynamic)

    fun load(limit: Int)

    fun like(dynamic: Dynamic)
}