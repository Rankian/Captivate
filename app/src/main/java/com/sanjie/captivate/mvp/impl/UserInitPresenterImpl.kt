package com.sanjie.captivate.mvp.impl

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.sanjie.captivate.mvp.model.User
import com.sanjie.captivate.mvp.presenter.UserInitPresenter
import com.sanjie.zy.utils.log.ZYLog

/**
 * Created by LangSanJie on 2017/4/19.
 */
class UserInitPresenterImpl : UserInitPresenter {
    var mLoginView: UserInitPresenter.LoginView? = null
    var mRegisterView: UserInitPresenter.RegisterView? = null
    var mVerifyView: UserInitPresenter.VerifyView? = null

    constructor(loginView: UserInitPresenter.LoginView) {
        mLoginView = loginView
    }

    constructor(registerView: UserInitPresenter.RegisterView,verifyView: UserInitPresenter.VerifyView) {
        mRegisterView = registerView
        mVerifyView = verifyView
    }

    constructor(verifyView: UserInitPresenter.VerifyView){
        mVerifyView = verifyView
    }

    override fun verify(phone: String) {
        val query = BmobQuery<User>()
        query.addWhereEqualTo("username",phone)
        query.findObjects(object : FindListener<User>(){
            override fun done(users: MutableList<User>?, e: BmobException?) {
                ZYLog.d("用户是否存在：" + (e == null && users!!.size != 0))
                mVerifyView!!.onVerifyResult((e == null && users!!.size != 0))
            }
        })
    }

    override fun register(phone: String, password: String, nickname: String, email: String) {
        val user = User()
        user.username = phone
        user.setPassword(password)
        user.nickname = nickname
        user.email = email
        user.signUp(object : SaveListener<User>() {
            override fun done(user: User?, e: BmobException?) {
                mRegisterView!!.onRegisterResult(user, e)
            }
        })
    }

    override fun login(username: String, password: String) {
        val user = User()
        user.username = username
        user.setPassword(password)
        user.login(object : SaveListener<User>() {
            override fun done(user: User?, e: BmobException?) {
                mLoginView!!.onLoginResult(user, e)
            }

        })

    }
}