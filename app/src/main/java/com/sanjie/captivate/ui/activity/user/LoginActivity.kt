package com.sanjie.captivate.ui.activity.user

import android.view.KeyEvent
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.mvp.impl.UserInitPresenterImpl
import com.sanjie.captivate.mvp.model.User
import com.sanjie.captivate.mvp.presenter.UserInitPresenter
import com.sanjie.captivate.ui.activity.MainActivity
import com.sanjie.zy.utils.ZYEmptyUtils
import com.sanjie.zy.utils.ZYEncryptUtils
import com.sanjie.zy.utils.keyboard.ZYKeyboardVisibilityEvent
import com.sanjie.zy.utils.log.ZYLog
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import com.sanjie.zy.widget.ZYLoadingDialog
import com.sanjie.zy.widget.ZYToast
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

/**
 * Created by LangSanJie on 2017/4/19.
 */
class LoginActivity : BaseActivity(), UserInitPresenter.LoginView {

    var userInitPresenter: UserInitPresenter? = null

    var loadingDialog: ZYLoadingDialog? = null

    companion object {
        val TAG = "LoginActivity"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {

        ZYStatusBarUtil.translucentStatusBar(this)

        loadingDialog = ZYLoadingDialog(this)

        RxView.clicks(login_btn)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (inputVerify()) {
                        login(login_username_dt.text.toString(), login_password_et.text.toString())
                    }
                }
        RxView.clicks(register_btn)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    forwardActivity(RegisterActivity::class.java, null)
                }

        ZYKeyboardVisibilityEvent.setEventListener(this@LoginActivity, {
            when(it){
                true -> {
                    ZYLog.d("键盘打开")
                }
                else -> {
                    ZYLog.d("键盘隐藏")
                }
            }
        })

        Glide.with(this@LoginActivity).load("http://s7.sinaimg.cn/mw690/0035blA0gy6ENAnxlT8d6&690").centerCrop().into(login_user_avatar_iv)
    }

    override fun getToolBarTitle(): String? {
        return null
    }

    override fun processLogic() {
        userInitPresenter = UserInitPresenterImpl(this)
    }

    fun login(username: String, password: String) {
        loadingDialog!!.setIndeterminateDrawable(resources.getDrawable(R.drawable.loading_dialog_progress_bar_style))
        loadingDialog!!.setMessage("正在登录...")
        loadingDialog!!.show()
        userInitPresenter!!.login(username, ZYEncryptUtils.MD5(password))
    }

    override fun onLoginResult(user: User?, e: Exception?) {
        //登录结果返回
        loadingDialog!!.dismiss()
        if (e == null) {
            forwardActivity(MainActivity::class.java, null)
        } else {
            ZYToast.warning("用户名或密码错误")
            ZYLog.e(e.message)
        }
    }

    fun inputVerify(): Boolean {

        val loginName = login_username_dt.text.toString()
        val loginPassword = login_password_et.text.toString()

        if (ZYEmptyUtils.isEmpty(loginName) || ZYEmptyUtils.isEmpty(loginName.trim())) {
            ZYToast.warning("请输入用户名")
            return false
        }
        if (ZYEmptyUtils.isEmpty(loginPassword) || ZYEmptyUtils.isEmpty(loginPassword.trim())) {
            ZYToast.warning("请输入密码")
            return false
        }
        return true
    }

    override fun hasToolbar(): Boolean {
        return false
    }

    override fun isSupportSwipeBack(): Boolean {
        return false
    }

    override fun isSupportBack(): Boolean {
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false)
        }
        return super.onKeyDown(keyCode, event)
    }

}