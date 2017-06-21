package com.sanjie.captivate.ui.activity.app

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.sanjie.captivate.R
import com.sanjie.captivate.adapter.AppAdapter
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.mvp.impl.AppPresenterImpl
import com.sanjie.captivate.mvp.model.App
import com.sanjie.captivate.mvp.presenter.AppPresenter
import com.sanjie.zy.adpter.decoration.DividerDecoration
import com.sanjie.zy.utils.ZYDensityUtils
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import kotlinx.android.synthetic.main.activity_app.*

/**
 * Created by SanJie on 2017/6/9.
 */
class AppActivity : BaseActivity(), AppPresenter.View {

    companion object {
        val TAG = "AppActivity"
    }

    var appList: ArrayList<App>? = null
    var appAdapter: AppAdapter? = null

    var appPresenter: AppPresenter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_app
    }

    override fun initView() {
        ZYStatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.holo_orange_light), 0)

        appList = ArrayList()
        appAdapter = AppAdapter(this, app_recycler_view, appList!!)
        appAdapter!!.isLoadMore(false)
        app_recycler_view.layoutManager = LinearLayoutManager(this)
        app_recycler_view.addItemDecoration(DividerDecoration(resources.getColor(R.color.gray_30), ZYDensityUtils.dp2px(1F)))
        app_recycler_view.adapter = appAdapter
        appAdapter!!.setOnItemClickListener { view, i ->
            val app = appList!![i]
            val bundle = Bundle()
            bundle.putSerializable(AppDetailsActivity.INTENT_EXTRA_APP, app)
            forwardActivity(AppDetailsActivity::class.java, bundle)
        }
    }

    override fun getToolBarTitle(): String? {
        return "应用中心"
    }

    override fun processLogic() {
        appPresenter = AppPresenterImpl(this)
        appPresenter!!.loadApps("")
    }

    override fun appResult(apps: List<App>) {
        appList!!.addAll(apps)
        appAdapter!!.notifyDataSetChanged()
    }
}