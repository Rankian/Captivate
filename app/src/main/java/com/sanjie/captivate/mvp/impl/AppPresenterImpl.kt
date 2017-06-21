package com.sanjie.captivate.mvp.impl

import cn.bmob.v3.BmobQuery
import com.sanjie.captivate.mvp.model.App
import com.sanjie.captivate.mvp.presenter.AppPresenter

/**
 * Created by SanJie on 2017/6/9.
 */
class AppPresenterImpl(val appView: AppPresenter.View) : AppPresenter {

    override fun loadApps(category: String) {
        val query = BmobQuery<App>()
        if (category.isNotEmpty()) {
            query.addWhereEqualTo("category", category)
        }
        query.findObjectsObservable(App::class.java)
                .subscribe {
                    appView.appResult(it)
                }
    }
}