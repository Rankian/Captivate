package com.sanjie.captivate.mvp.impl

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.sanjie.captivate.mvp.model.User
import com.sanjie.captivate.mvp.presenter.RetrievePasswordPresenter
import com.sanjie.zy.utils.log.ZYLog

/**
 * Created by SanJie on 2017/5/26.
 */
class RetrievePasswordPresenterImpl(val verifyPhoneView: RetrievePasswordPresenter.VerifyView,
                                    val retrieveView: RetrievePasswordPresenter.RetrieveView) : RetrievePasswordPresenter {

    override fun verifyPhone(phone: String) {
        val query = BmobQuery<User>()
        query.addWhereEqualTo("username",phone)
        query.findObjects(object : FindListener<User>(){
            override fun done(users: MutableList<User>?, e: BmobException?) {
                ZYLog.d("用户是否存在：" + (e == null && users!!.size != 0))
                verifyPhoneView.verifyPhoneResult((e == null && users!!.size != 0))
            }
        })
    }

    override fun retrievePassword(code: String, password: String) {
        BmobUser.resetPasswordBySMSCode(code, password, object : UpdateListener() {
            override fun done(e: BmobException?) {
                retrieveView.retrieveResult(e)
            }
        })
    }
}