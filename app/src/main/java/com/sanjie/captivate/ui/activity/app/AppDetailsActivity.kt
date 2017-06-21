package com.sanjie.captivate.ui.activity.app

import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sanjie.captivate.R
import com.sanjie.captivate.adapter.AppScreenshotAdapter
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.mvp.model.App
import com.sanjie.zy.adpter.decoration.DividerDecoration
import com.sanjie.zy.utils.ZYDensityUtils
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import kotlinx.android.synthetic.main.activity_app_details.*
import kotlinx.android.synthetic.main.include_app_details_content.*

/**
 * Created by SanJie on 2017/6/9.
 */
class AppDetailsActivity : BaseActivity() {

    companion object {
        val TAG = "AppDetailsActivity"
        val INTENT_EXTRA_APP = "app"
    }

    var screenshots: ArrayList<String>? = null
    var screenshotAdapter: AppScreenshotAdapter? = null

    var app: App? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_app_details
    }

    override fun initView() {
        ZYStatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.holo_orange_light), 0)

        screenshots = ArrayList()
        screenshotAdapter = AppScreenshotAdapter(this, app_details_screenshot_recycler_view, screenshots!!)
        screenshotAdapter!!.isLoadMore(false)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManager.isAutoMeasureEnabled = true
        app_details_screenshot_recycler_view.layoutManager = layoutManager
        app_details_screenshot_recycler_view.adapter = screenshotAdapter

    }

    override fun getToolBarTitle(): String? {
        return app!!.name
    }

    override fun processLogic() {
        if (intent != null && intent.hasExtra(INTENT_EXTRA_APP)) {
            app = intent.extras.getSerializable(INTENT_EXTRA_APP) as App
        }

        Glide.with(this).load(app!!.icon).into(app_details_icon_iv)
        app_details_name_tv.text = app!!.name
        app_details_software_tv.text = app!!.software
        app_details_score_rb.rating = app!!.score!!
        app_details_intro_tv.text = app!!.intro
        app_details_introduction_tv.text = app!!.introduction
        app_details_developer_tv.text = "开发者：${app!!.developer}"
        app_details_update_date_tv.text = "更新时间：${app!!.createdAt}"
        app_details_version_tv.text = "版本号：${app!!.versionName}"

        screenshots!!.addAll(app!!.screenshot!!)
        screenshotAdapter!!.notifyDataSetChanged()
    }
}