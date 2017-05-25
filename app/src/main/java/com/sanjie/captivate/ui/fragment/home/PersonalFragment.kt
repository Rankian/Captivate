package com.sanjie.captivate.ui.fragment.home

import android.view.View
import cn.bmob.v3.BmobUser
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseFragment
import com.sanjie.captivate.mvp.model.User
import com.sanjie.captivate.ui.activity.user.UserCenterActivity
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import kotlinx.android.synthetic.main.fragment_personal.*

/**
 * Created by SanJie on 2017/5/11.
 */
class PersonalFragment : BaseFragment() {

    companion object{
        val TAG = "PersonalFragment"
    }

    var user: User? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_personal
    }

    override fun initView() {

        user = BmobUser.getCurrentUser(User::class.java)
        Glide.with(activity).load(user!!.avatar).centerCrop().error(R.mipmap.default_avatar).into(personal_user_avatar)
        personal_username_tv.text = user!!.nickname
        personal_user_signature_tv.text = user!!.signature
    }

    override fun onViewClick() {
        super.onViewClick()
        onClick(personal_user_option)
    }

    fun onClick(view: View){
        RxView.clicks(view)
                .subscribe {
                    when (view){
                        personal_user_option -> forwardActivity(UserCenterActivity::class.java, null)
                    }
                }
    }

    override fun processLogic() {
    }
}