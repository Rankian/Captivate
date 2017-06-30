package com.sanjie.captivate.ui.activity

import com.sanjie.captivate.mvp.MvpActivity
import com.sanjie.captivate.mvp.presenter.MiPresenter
import com.sanjie.captivate.mvp.view.MiView

/**
 * Created by SanJie on 2017/6/28.
 */
class MiActivity : MvpActivity<MiView, MiPresenter>(), MiView{
    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getToolBarTitle(): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun processLogic() {
        presenter!!.getGankData()
    }

    override fun showLoadingDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getData(data: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDataFail(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initPresenter(): MiPresenter {
        return MiPresenter()
    }
}