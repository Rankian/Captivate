package com.sanjie.captivate.ui.activity.user

import cn.bmob.sms.BmobSMS
import cn.bmob.sms.exception.BmobException
import cn.bmob.sms.listener.RequestSMSCodeListener
import cn.bmob.sms.listener.VerifySMSCodeListener
import com.jakewharton.rxbinding2.view.RxView
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.mvp.impl.UserInitPresenterImpl
import com.sanjie.captivate.mvp.model.User
import com.sanjie.captivate.mvp.presenter.UserInitPresenter
import com.sanjie.zy.utils.ZYEmptyUtils
import com.sanjie.zy.utils.ZYEncryptUtils
import com.sanjie.zy.utils.ZYRegexUtils
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import com.sanjie.zy.widget.ZYToast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_register.*
import java.util.concurrent.TimeUnit

/**
 * Created by LangSanJie on 2017/4/21.
 * 逻辑如下：
 *  1、验证输入内容
 *  2、验证手机号码并发送验证码
 *  3、验证码验证成功后进行注册
 */
class RegisterActivity : BaseActivity(), UserInitPresenter.RegisterView, UserInitPresenter.VerifyView {

    var userInitPresenter: UserInitPresenter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initView() {
        ZYStatusBarUtil.translucentStatusBar(this)

        RxView.clicks(register_back_btn)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    finish()
                }
        RxView.clicks(register_btn)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(inputVerify()){
                        verifySMSCode(register_phone_et.text.toString(),register_code_et.text.toString())
                    }
                }
        RxView.clicks(register_code_btn)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    verify(register_phone_et.text.toString())
                }
    }

    override fun getToolBarTitle(): String? {
        return null
    }

    override fun hasToolbar(): Boolean {
        return false
    }

    override fun processLogic() {
        userInitPresenter = UserInitPresenterImpl(this, this)
    }

    /**
     * 验证用户时候存在
     */
    fun verify(username: String) {
        userInitPresenter!!.verify(username)
    }

    /**
     * 注册
     */
    fun register(username: String, password: String, nickname: String, email: String) {
        userInitPresenter!!.register(username, ZYEncryptUtils.MD5(password), nickname, email)
    }

    override fun onVerifyResult(exist: Boolean) {
        if (exist) {
            ZYToast.warning("该用户已经存在")
        } else {
            register_code_btn.isEnabled = false
            sendSMSCode(register_phone_et.text.toString())
            countDown()
        }
    }

    override fun onRegisterResult(user: User?, e: Exception?) {
        if(e == null){
            //注册成功
            ZYToast.success("注册成功，请登录")
            finish()
        }else{
            ZYToast.error("对不起，注册失败，请稍后再试")
        }
    }

    fun inputVerify(): Boolean {
        val inputPhone = register_phone_et.text.toString()
        val inputCode = register_code_et.text.toString()
        val inputNickname = register_nickname_et.text.toString()
        val inputPassword = register_password_et.text.toString()
        val inputEmail = register_email_et.text.toString()

        if(ZYEmptyUtils.isEmpty(inputPhone) || ZYEmptyUtils.isEmpty(inputPhone.trim())){
            ZYToast.warning("请输入手机号码")
            return false
        }
        if(!ZYRegexUtils.checkMobile(inputPhone) || !ZYRegexUtils.checkMobile(inputPhone.trim())){
            ZYToast.warning("手机号码格式错误")
            return false
        }
        if(ZYEmptyUtils.isEmpty(inputCode) || ZYEmptyUtils.isEmpty(inputCode.trim())){
            ZYToast.warning("请输入短信验证码")
            return false
        }
        if(ZYEmptyUtils.isEmpty(inputNickname) || ZYEmptyUtils.isEmpty(inputNickname.trim())){
            ZYToast.warning("请输入昵称")
            return false
        }
        if(ZYEmptyUtils.isEmpty(inputPassword) || ZYEmptyUtils.isEmpty(inputPassword.trim())){
            ZYToast.warning("请输入密码")
            return false
        }
        if(inputPassword.length < 6){
            ZYToast.warning("密码长度不能小于6位")
            return false
        }
        if(ZYEmptyUtils.isEmpty(inputEmail) || ZYEmptyUtils.isEmpty(inputEmail.trim())){
            ZYToast.warning("请输入邮箱地址")
            return false
        }
        if(!ZYRegexUtils.checkEmail(inputEmail) || !ZYRegexUtils.checkEmail(inputEmail)){
            ZYToast.warning("邮箱地址格式错误")
            return false
        }

        return true
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
                    register_code_btn.text = "$it s后重试"
                    if (it == 1) {
                        register_code_btn.isEnabled = true
                        register_code_btn.text = "获取验证码"
                    }
                }

    }

    /**
     * 发送验证码
     */
    fun sendSMSCode(phone: String) {
        BmobSMS.requestSMSCode(this, phone, "register", object : RequestSMSCodeListener() {
            override fun done(smsId: Int?, e: BmobException?) {
                if (e == null) {
                    ZYToast.success("短信验证码已发送，请注意查收")
                }
            }
        })
    }

    /**
     * 验证验证码
     */
    fun verifySMSCode(phone: String, code: String){
        BmobSMS.verifySmsCode(this,phone,code,object : VerifySMSCodeListener(){
            override fun done(e: BmobException?) {
                if(e == null){
                    //短信验证成功，进行注册
                    register(register_phone_et.text.toString(),register_password_et.text.toString(),
                            register_nickname_et.text.toString(),register_email_et.text.toString())
                }else{
                    ZYToast.error("短信验证码错误")
                }
            }
        })
    }
}