package com.sanjie.captivate.ui.activity.dynamic

import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import cn.bmob.v3.BmobUser
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.sanjie.captivate.R
import com.sanjie.captivate.adapter.PublishPhotoAdapter
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.event.DynamicEvent
import com.sanjie.captivate.http.reactive.TransformUtils
import com.sanjie.captivate.mvp.impl.DynamicPresenterImpl
import com.sanjie.captivate.mvp.model.Dynamic
import com.sanjie.captivate.mvp.model.User
import com.sanjie.captivate.mvp.presenter.DynamicPresenter
import com.sanjie.captivate.util.SystemUtils
import com.sanjie.zy.picture.ZYPicturePicker
import com.sanjie.zy.utils.*
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import com.sanjie.zy.widget.ZYLoadingDialog
import com.sanjie.zy.widget.ZYToast
import kotlinx.android.synthetic.main.activity_publish_dynamic.*
import java.util.concurrent.TimeUnit

/**
 * Created by SanJie on 2017/5/22.
 */
class PublishDynamicActivity : BaseActivity(), DynamicPresenter.PublishView {

    companion object {
        val TAG = "PublishDynamicActivity"
    }

    var publishPresenter: DynamicPresenter? = null

    var photoList: ArrayList<String>? = null
    var photoAdapter: PublishPhotoAdapter? = null

    var user: User? = null

    var loadingDialog: ZYLoadingDialog? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_publish_dynamic
    }

    override fun initView() {
        ZYStatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.main_color), 0)
        showRightButton(true)

        user = BmobUser.getCurrentUser(User::class.java)
        loadingDialog = ZYLoadingDialog(this)

        photoList = ArrayList()
        photoAdapter = PublishPhotoAdapter(this@PublishDynamicActivity, publish_dynamic_photo_recycler_view, photoList!!, ZYDisplayUtils.getScreenWidth() / 3)
        photoAdapter!!.isLoadMore(false)
        photoAdapter!!.setDeleteListener(object : PublishPhotoAdapter.OnPhotoDeleteListener {
            override fun delete(position: Int) {
                photoList!!.removeAt(position)
                if (photoList!!.size < 9) {
                    publish_dynamic_increase_photo_btn.visibility = VISIBLE
                }
                photoAdapter!!.notifyDataSetChanged()
            }
        })
        val gridManager = GridLayoutManager(this, 3)
        gridManager.isAutoMeasureEnabled = true
        publish_dynamic_photo_recycler_view.layoutManager = gridManager
        publish_dynamic_photo_recycler_view.adapter = photoAdapter
    }

    override fun getToolBarTitle(): String? {
        return "发动态"
    }

    override fun onClickRightButton(view: View) {
        super.onClickRightButton(view)
        if (verify()) {
            val dynamic = Dynamic()
            dynamic.authorName = user!!.nickname
            dynamic.authorAvatar = user!!.avatar
            dynamic.source = SystemUtils.getDeviceBrand() + SystemUtils.getDeviceModel()
            dynamic.content = publish_dynamic_et.text.toString()
            dynamic.photos = photoList
            dynamic.like = 0
            dynamic.comment = 0
            publishPresenter!!.publish(dynamic)
            loadingDialog!!.setMessage("发布中...")
            loadingDialog!!.show()
        }
    }

    private fun verify(): Boolean {
        val content = publish_dynamic_et.text.toString()
        if (ZYEmptyUtils.isEmpty(content) && photoList!!.size == 0) {
            ZYToast.warning("还是说点儿什么吧")
            return false
        }
        if (content.length > 300) {
            ZYToast.warning("说得太多可不是好事噢")
            return false
        }
        return true
    }

    override fun processLogic() {
        publishPresenter = DynamicPresenterImpl(this@PublishDynamicActivity, this)

        //点击添加图片
        RxView.clicks(publish_dynamic_increase_photo_btn)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe {
                    ZYPicturePicker.of()
                            .camera(true)
                            .single(false)
                            .limit(9 - photoList!!.size)
                            .start(this@PublishDynamicActivity)
                            .compose(TransformUtils.defaultNewThreadSchedulers())
                            .map {
                                val pathList = ArrayList<String>()
                                it.forEach {
                                    pathList.add(it.path)
                                }
                                pathList
                            }.subscribe {
                        photoList!!.addAll(it)
                        if (photoList!!.size == 9) {
                            publish_dynamic_increase_photo_btn.visibility = GONE
                        }
                        photoAdapter!!.notifyDataSetChanged()
                    }
                }
        /**
         * EditText 字数控制
         */
        RxTextView.textChangeEvents(publish_dynamic_et)
                .map { it.text().toString() }
                .subscribe {
                    publish_dynamic_content_count_tv.text = "${it.length}/300"
                    publish_dynamic_content_count_tv.setTextColor(resources.getColor(if (it.length > 300) R.color.red else R.color.gray_80))
                }
    }

    override fun onPublishResult(objectId: String) {

        RxBus.singleton().post(DynamicEvent(objectId))

        loadingDialog!!.dismiss()
        ZYKeyboardUtils.closeKeyboard(this)
        finish()
    }
}