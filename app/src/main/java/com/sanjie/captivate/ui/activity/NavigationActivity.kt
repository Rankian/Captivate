package com.sanjie.captivate.ui.activity

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.common.AppConfig
import com.sanjie.captivate.ui.activity.user.LoginActivity
import com.sanjie.zy.base.ZYApplication
import com.sanjie.zy.utils.ACache
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import kotlinx.android.synthetic.main.activity_navigation.*

/**
 * 引导页
 * Created by SanJie on 2017/5/16.
 */
class NavigationActivity : BaseActivity() {

    companion object {
        val TAG = "NavigationActivity"
    }

    var viewList: ArrayList<View>? = null
    var mAdapter: NavigationAdapter? = null

    val views = arrayOf(R.layout.include_navigation_view_first, R.layout.include_navigation_view_second,
            R.layout.include_navigation_view_third, R.layout.include_navigation_view_fourth)

    override fun getLayoutId(): Int {
        return R.layout.activity_navigation
    }

    override fun initView() {
        ZYStatusBarUtil.translucentStatusBar(this)

        viewList = ArrayList()
        getPageViewList()
        mAdapter = NavigationAdapter()

        navigation_view_pager.adapter = mAdapter
        view_pager_indicator_view.setViewPager(navigation_view_pager)
    }

    override fun getToolBarTitle(): String? {
        return null
    }

    override fun processLogic() {
        navigation_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    3 -> {
                        ACache.get(this@NavigationActivity).put(AppConfig.IS_FIRST_LAUNCH_APP, false)
                        forwardActivity(LoginActivity::class.java, null)
                    }
                }
            }

        })
    }

    fun getPageViewList(): List<View> {
        viewList!!.clear()
        for (i in 0..views.size - 1) {
            viewList!!.add(createView(i))
        }

        return viewList!!
    }

    fun createView(index: Int): View = layoutInflater.inflate(views[index], null)

    inner class NavigationAdapter : PagerAdapter() {

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return if (viewList == null) 0 else viewList!!.size
        }

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            val view = viewList!![position]
            container!!.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container!!.removeView(`object` as View)
        }

        override fun getItemPosition(`object`: Any?): Int {
            return POSITION_NONE
        }

    }

    override fun hasToolbar(): Boolean {
        return false
    }

    override fun isSupportSwipeBack(): Boolean {
        return false
    }

}