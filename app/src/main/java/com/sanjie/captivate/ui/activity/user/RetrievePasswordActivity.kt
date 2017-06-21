package com.sanjie.captivate.ui.activity.user

import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.jakewharton.rxbinding2.view.RxView
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.mvp.impl.RetrievePasswordPresenterImpl
import com.sanjie.captivate.mvp.presenter.RetrievePasswordPresenter
import com.sanjie.zy.utils.ZYEmptyUtils
import com.sanjie.zy.utils.ZYKeyboardUtils
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import com.sanjie.zy.widget.ZYToast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_retrieve_password.*
import java.util.concurrent.TimeUnit

/**
 * 找回密码
 * Created by SanJie on 2017/5/26.
 */
class RetrievePasswordActivity : BaseActivity(), RetrievePasswordPresenter.VerifyView, RetrievePasswordPresenter.RetrieveView {

    companion object {
        val TAG = "RetrievePasswordActivity"
    }

    var retrievePresenter: RetrievePasswordPresenter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_retrieve_password
    }

    override fun initView() {
        ZYStatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.main_color), 0)
    }

    override fun getToolBarTitle(): String? {
        return "找回密码"
    }

    override fun processLogic() {

        retrievePresenter = RetrievePasswordPresenterImpl(this, this)

        RxView.clicks(retrieve_code_btn)
                .throttleFirst(2, TimeUnit.SECONDS)
                .map {
                    ZYEmptyUtils.isEmpty(retrieve_phone_et.text.toString().trim())
                }
                .subscribe {
                    when (it) {
                        true -> {
                            ZYToast.warning("请输入手机号码")
                        }
                        else -> {
                            sendSMSCode(retrieve_phone_et.text.toString().trim())
                            countDown()
                        }
                    }
                }
        RxView.clicks(retrieve_confirm_btn)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe {
                    if (verify()) {
                        retrievePresenter!!.verifyPhone(retrieve_phone_et.text.toString().trim())
                    }
                }
    }

    private fun verify(): Boolean {
        val phone = retrieve_phone_et.text.toString().trim()
        val code = retrieve_code_et.text.toString().trim()
        val password = retrieve_password_et.text.toString().trim()
        val confirmPassword = retrieve_confirm_password_et.text.toString().trim()

        if (ZYEmptyUtils.isEmpty(phone)) {
            ZYToast.warning("请输入手机号码")
            return false
        }
        if (ZYEmptyUtils.isEmpty(code)) {
            ZYToast.warning("请输入短信验证码")
            return false
        }
        if (ZYEmptyUtils.isEmpty(password)) {
            ZYToast.warning("请输入新密码")
            return false
        }
        if (ZYEmptyUtils.isEmpty(confirmPassword)) {
            ZYToast.warning("请确认新密码")
            return false
        }
        if (confirmPassword != password) {
            ZYToast.warning("两次输入密码不一致")
            return false
        }
        return true
    }

    override fun verifyPhoneResult(exist: Boolean) {
        when (exist) {
            true -> {
                retrievePresenter!!.retrievePassword(retrieve_code_et.text.toString().trim(), retrieve_confirm_password_et.text.toString().trim())
            }
            else -> {
                ZYToast.warning("手机号码不存在")
            }
        }
    }

    override fun retrieveResult(e: Exception?) {
        when (e == null) {
            true -> {
                ZYToast.info("密码重置成功，请重新登录")
                ZYKeyboardUtils.closeKeyboard(this@RetrievePasswordActivity)
                finish()
            }
            else -> {
                ZYToast.warning(e!!.localizedMessage)
            }
        }
    }

    /**
     * 发送验证码
     */
    fun sendSMSCode(phone: String) {
        BmobSMS.requestSMSCode(phone, "register", object : QueryListener<Int>() {
            override fun done(smsId: Int?, e: BmobException?) {
                if (e == null) {
                    ZYToast.success("短信验证码已发送，请注意查收")
                }
            }
        })
    }

    /**
     * 倒计时
     */
    fun countDown() {
        val times = 60
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    aLong ->
                    times - aLong!!.toInt()
                }
                .take(times.toLong())
                .subscribe {
                    retrieve_code_btn.text = "$it s后重试"
                    if (it == 1) {
                        retrieve_code_btn.isEnabled = true
                        retrieve_code_btn.text = "获取验证码"
                    }
                }

    }
}