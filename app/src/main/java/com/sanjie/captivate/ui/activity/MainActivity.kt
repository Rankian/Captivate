package com.sanjie.captivate.ui.activity

import android.Manifest
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.bmob.v3.BmobUser
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.common.AppConfig
import com.sanjie.captivate.event.UserUpdateEvent
import com.sanjie.captivate.mvp.model.User
import com.sanjie.captivate.ui.activity.app.AppActivity
import com.sanjie.captivate.ui.activity.dynamic.PublishDynamicActivity
import com.sanjie.captivate.ui.activity.user.UserCenterActivity
import com.sanjie.captivate.ui.fragment.DynamicFragment
import com.sanjie.captivate.ui.fragment.home.DiscoveryFragment
import com.sanjie.captivate.ui.fragment.home.MusicFragment
import com.sanjie.captivate.ui.fragment.home.PersonalFragment
import com.sanjie.captivate.ui.fragment.home.TravelFragment
import com.sanjie.zy.common.ZYActivityStack
import com.sanjie.zy.utils.RxBus
import com.sanjie.zy.utils.log.ZYLog
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import com.sanjie.zy.widget.ZYToast
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    var headerView: View? = null
    var userAvatar: ImageView? = null
    var usernameTv: TextView? = null
    var userSignatureTv: TextView? = null
    var headerViewContainer: ImageView? = null

    var fragments: ArrayList<Fragment>? = null

    val musicFragment = MusicFragment()
    val dynamicFragment = DynamicFragment()
    val discoveryFragment = DiscoveryFragment()
    val travelFragment = TravelFragment()
    val personalFragment = PersonalFragment()

    var user: User? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

        user = BmobUser.getCurrentUser(User::class.java)
        ZYLog.json(user!!.toJSONString())
        initNavigation()

        RxView.clicks(main_page_publish_btn)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe {
                    forwardActivity(PublishDynamicActivity::class.java, null)
                }
    }

    fun initUser() {
        Glide.with(this@MainActivity).load(AppConfig.NAVI_IMAGE_URL).centerCrop().into(headerViewContainer)
        Glide.with(this@MainActivity).load(user!!.avatar).centerCrop().into(userAvatar)
        usernameTv!!.text = user!!.nickname
        userSignatureTv!!.text = user!!.signature

    }

    override fun checkPermissions() {
        super.checkPermissions()
        RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe {
                    when (it) {
                        true -> {
                        }
                        else -> {
                        }
                    }
                }
    }

    /**
     * 初始化 NavigationView
     */
    fun initNavigation() {

        headerView = navigation_view.getHeaderView(0)
        userAvatar = headerView!!.findViewById<ImageView>(R.id.user_avatar_iv)
        usernameTv = headerView!!.findViewById<TextView>(R.id.username_tv)
        userSignatureTv = headerView!!.findViewById<TextView>(R.id.signature_tv)
        headerViewContainer = headerView!!.findViewById<ImageView>(R.id.navigation_header_bg)

        initUser()
        //用户头像
        RxView.clicks(userAvatar!!)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe {
                    forwardActivity(UserCenterActivity::class.java, null)
                }

        ZYStatusBarUtil.translucentStatusBar(this)
//        ZYStatusBarUtil.setColorForDrawerLayout(this, drawer_layout, resources.getColor(R.color.colorPrimary), 0)

        navigation_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.app_menu -> forwardActivity(AppActivity::class.java, null)
                R.id.set_menu -> ZYToast.info("想设置，没门儿")
                R.id.suggest_menu -> forwardActivity(TitleItemDecorationActivity::class.java, null)
                R.id.update_menu -> {
                    ZYToast.info("你莫点了嘛，没得更新啊")
                }
                R.id.about_menu -> forwardActivity(AboutActivity::class.java, null)
                R.id.exit_menu -> exit()
            }
            true
        }
    }

    override fun getToolBarTitle(): String? {
        return null
    }

    override fun processLogic() {

        initFragment()
        main_page_radio_group.setOnCheckedChangeListener { group, checkedId ->
            (0..group.childCount - 1)
                    .filter { group.getChildAt(it).id == checkedId }
                    .forEach {
                        when (it) {
                            0 -> switchFragment(0)
                            1 -> switchFragment(1)
                            3 -> switchFragment(2)
                            4 -> switchFragment(3)
                        }
                    }
        }
        main_page_radio_group.check(main_page_radio_group.getChildAt(0).id)

        RxBus.singleton()
                .toObservable(UserUpdateEvent::class.java)
                .subscribe { userUpdateEvent ->
                    user = userUpdateEvent.user
                    initUser()
                }
    }

    fun initFragment() {
        fragments = ArrayList<Fragment>()

        fragments!!.add(dynamicFragment)
        fragments!!.add(discoveryFragment)
        fragments!!.add(travelFragment)
        fragments!!.add(personalFragment)

        val transaction = supportFragmentManager.beginTransaction()
        Observable.fromIterable(fragments)
                .subscribe {
                    transaction.add(R.id.main_content_frame, it)
                }
        transaction.commit()
    }

    fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.hide(fragments!![0]).hide(fragments!![1]).hide(fragments!![2]).hide(fragments!![3])
        transaction.show(fragments!![position]).commit()
    }

    override fun isSupportBack(): Boolean {
        return false
    }

    override fun isSupportSwipeBack(): Boolean {
        return false
    }

    override fun hasToolbar(): Boolean {
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
                    drawer_layout.closeDrawer(Gravity.LEFT)
                    return false
                }
                moveTaskToBack(true)
            }
            KeyEvent.KEYCODE_MENU -> {
                if (!drawer_layout.isDrawerOpen(Gravity.LEFT)) {
                    drawer_layout.openDrawer(Gravity.LEFT)
                }
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    private fun exit() {
        ZYToast.error("2s后我们就拜拜啦")
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe {
                    finish()
                    ZYActivityStack.getInstance().appExit()
                }
    }
}
