package com.sanjie.captivate.ui.activity

import cn.bmob.v3.BmobUser
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.common.AppConfig
import com.sanjie.captivate.mvp.model.User
import com.sanjie.captivate.ui.activity.user.LoginActivity
import com.sanjie.zy.utils.ACache
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    var user: User? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        ZYStatusBarUtil.translucentStatusBar(this)

        user = BmobUser.getCurrentUser(User::class.java)
    }

    override fun getToolBarTitle(): String? {
        return null
    }

    override fun hasToolbar(): Boolean {
        return false
    }

    override fun processLogic() {
        countdown()
    }

    private fun countdown() {

        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(ACache.get(this).getAsObject(AppConfig.IS_FIRST_LAUNCH_APP) != null){
                        if (user != null) {
                            forwardActivity(MainActivity::class.java, null)
                        } else {
                            forwardActivity(LoginActivity::class.java, null)
                        }
                    }else{
                        forwardActivity(NavigationActivity::class.java, null)
                    }
                    finish()
                }
    }

}
