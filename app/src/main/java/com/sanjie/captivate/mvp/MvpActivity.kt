package com.sanjie.captivate.mvp

import android.os.Bundle
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.mvp.base.BasePresenter



/**
 * Created by SanJie on 2017/6/28.
 */

abstract class MvpActivity<V, P : BasePresenter<V>> : BaseActivity() {

    protected var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = initPresenter()
    }

    override fun onResume() {
        super.onResume()
        presenter!!.onAttach(this as V)
    }

    override fun onDestroy() {
        presenter!!.onDetach()
        super.onDestroy()
    }

    abstract fun initPresenter(): P

}