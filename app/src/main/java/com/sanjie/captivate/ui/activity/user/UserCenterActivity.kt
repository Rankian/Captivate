package com.sanjie.captivate.ui.activity.user

import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.v3.listener.UploadFileListener
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.event.UserUpdateEvent
import com.sanjie.captivate.mvp.model.User
import com.sanjie.zy.common.ZYActivityStack
import com.sanjie.zy.picture.ZYPicturePicker
import com.sanjie.zy.utils.RxBus
import com.sanjie.zy.utils.ZYCompressHelper
import com.sanjie.zy.utils.log.ZYLog
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import com.sanjie.zy.widget.ZYLoadingDialog
import com.sanjie.zy.widget.ZYToast
import kotlinx.android.synthetic.main.activity_user_center.*
import kotlinx.android.synthetic.main.include_user_center_content.*
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by LangSanJie on 2017/4/26.
 */
class UserCenterActivity : BaseActivity() {

    companion object{
        val TAG = "UserCenterActivity"
    }

    var user : User? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_user_center
    }

    override fun initView() {
        ZYStatusBarUtil.translucentStatusBar(this)

        user = BmobUser.getCurrentUser(User::class.java)
        Glide.with(this@UserCenterActivity).load(user!!.avatar).centerCrop().error(R.mipmap.default_avatar).into(user_center_user_avatar)
        user_center_username_tv.text = user!!.nickname
        user_center_user_signature_tv.text = user!!.signature

        RxView.clicks(user_center_back_btn)
                .throttleFirst(3,TimeUnit.SECONDS)
                .subscribe {
                    backward()
                }
        RxView.clicks(user_center_user_avatar)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe {
                    ZYPicturePicker.of()
                            .start(this@UserCenterActivity)
                            .subscribe { imageItems ->
                                uploadAvatar(imageItems[0].path)
                            }
                }

        RxView.clicks(login_out_btn)
                .subscribe {
                    BmobUser.logOut()
                    forwardActivity(LoginActivity::class.java, null)
                    ZYActivityStack.getInstance().finishAllActivity()
                }
    }

    override fun getToolBarTitle(): String? {
        return null
    }

    override fun processLogic() {

    }

    override fun hasToolbar(): Boolean {
        return false
    }

    fun uploadAvatar(path: String) {

        val dialog = ZYLoadingDialog(this)
        dialog.setMessage("上传中...")
        dialog.show()

        val avatarFile = ZYCompressHelper.getDefault(this).compressToFile(File(path))

        val bmobFile = BmobFile(avatarFile)
        bmobFile.uploadblock(object : UploadFileListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    user!!.avatar = bmobFile.fileUrl
                    user!!.update(user!!.objectId, object : UpdateListener() {
                        override fun done(e: BmobException?) {
                            dialog.dismiss()
                            if (e == null) {
                                ZYToast.success("头像上传成功")
                                Glide.with(this@UserCenterActivity).load(bmobFile.localFile).centerCrop().into(user_center_user_avatar)
                                user = BmobUser.getCurrentUser(User::class.java)
                                RxBus.singleton().post(UserUpdateEvent(user!!))
                            } else {
                                ZYLog.e(e.message)
                                ZYToast.error("头像上传失败")
                            }
                        }
                    })
                } else {
                    dialog.dismiss()
                    ZYToast.error("头像上传失败")
                    ZYLog.e(e.message)
                }
            }

            override fun onProgress(value: Int?) {
                super.onProgress(value)
            }
        })
    }
}