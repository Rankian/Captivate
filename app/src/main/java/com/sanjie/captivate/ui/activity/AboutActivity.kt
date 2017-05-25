package com.sanjie.captivate.ui.activity

import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.zy.utils.ZYAppUtils
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import kotlinx.android.synthetic.main.activity_about.*

/**
 * 关于
 * Created by SanJie on 2017/5/11.
 */
class AboutActivity : BaseActivity() {

    companion object {
        val TAG = "AboutActivity"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_about
    }

    override fun initView() {
        ZYStatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.main_color), 0)

        about_app_version_name.text = ZYAppUtils.getVersionName(this@AboutActivity)
    }

    override fun getToolBarTitle(): String? {
        return "关于"
    }

    override fun processLogic() {
    }
}