package com.sanjie.captivate.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.sanjie.captivate.R
import com.sanjie.captivate.adapter.DynamicAdapter
import com.sanjie.captivate.base.BaseFragment
import com.sanjie.captivate.event.DynamicEvent
import com.sanjie.captivate.http.reactive.TransformUtils
import com.sanjie.captivate.mvp.impl.DynamicPresenterImpl
import com.sanjie.captivate.mvp.model.Dynamic
import com.sanjie.captivate.mvp.presenter.DynamicPresenter
import com.sanjie.zy.adpter.ZYRecyclerViewAdapter
import com.sanjie.zy.adpter.decoration.DividerDecoration
import com.sanjie.zy.utils.RxBus
import com.sanjie.zy.utils.ZYDisplayUtils
import com.sanjie.zy.widget.ZYToast
import kotlinx.android.synthetic.main.fragment_dynamic.*
import kotlinx.android.synthetic.main.include_fragment_title_bar.*

/**
 * Created by SanJie on 2017/5/22.
 */
class DynamicFragment : BaseFragment(), DynamicPresenter.LoadView, DynamicPresenter.LikeView {

    var dynamicPresenter: DynamicPresenter? = null

    var dynamicList: ArrayList<Dynamic>? = null
    var mAdapter: DynamicAdapter? = null

    var limit = 10
    var isRefresh = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_dynamic
    }

    override fun initView() {
        fragment_title_tv.text = "动态"

        dynamicList = ArrayList()
        mAdapter = DynamicAdapter(activity, dynamic_recycler_view, dynamicList!!)
        mAdapter!!.isLoadMore(false)
        dynamic_recycler_view.layoutManager = LinearLayoutManager(activity)
        dynamic_recycler_view.addItemDecoration(DividerDecoration(activity.resources.getColor(R.color.gray_30), ZYDisplayUtils.dp2px(3F)))
        dynamic_recycler_view.adapter = mAdapter

        dynamic_swipe_refresh_layout.setColorSchemeResources(R.color.main_color)
        dynamic_swipe_refresh_layout.setOnRefreshListener {
            isRefresh = true
            limit = 10
            dynamicPresenter!!.load(limit)
        }
        mAdapter!!.setOnLoadMoreListener(object : ZYRecyclerViewAdapter.OnLoadMoreListener {
            override fun onRetry() {
            }

            override fun onLoadMore() {
                isRefresh = false
                limit += 10
                dynamicPresenter!!.load(limit)
            }
        })
        mAdapter!!.setOperateListener(object : DynamicAdapter.OnOperateListener{
            override fun onForwarding(position: Int) {
                ZYToast.warning("想转发到哪儿去嘛")
            }

            override fun onComment(position: Int) {
                ZYToast.warning("程序员哥哥还在开发中")
            }

            override fun onLike(position: Int) {
                dynamicPresenter!!.like(dynamicList!![position])
            }
        })
    }

    override fun processLogic() {
        dynamicPresenter = DynamicPresenterImpl(this, this)
        dynamicPresenter!!.load(limit)

        RxBus.singleton().toObservable(DynamicEvent::class.java)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe {
                    isRefresh = true
                    limit = 10
                    dynamicPresenter!!.load(limit)
                }
    }

    override fun loadResult(dynamicList: List<Dynamic>) {

        if (dynamic_swipe_refresh_layout.isRefreshing) {
            dynamic_swipe_refresh_layout.isRefreshing = false
        }
        if (isRefresh) {
            this.dynamicList!!.clear()
        }
        /*
        是否存在下一页
        dynamicList.size < 10 不存在下一页
        dynamicList.size >= 10 可能存在下一页
         */
        if (dynamicList.size < 10) {
            mAdapter!!.isLoadMore(false)
            mAdapter!!.showLoadComplete()
        } else {
            mAdapter!!.isLoadMore(true)
        }
        this.dynamicList!!.addAll(dynamicList)
        mAdapter!!.notifyDataSetChanged()
    }

    override fun likeResult(likeCount: Int, dynamic: Dynamic) {

    }
}