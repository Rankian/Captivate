package com.sanjie.captivate.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanjie.zy.utils.ZYKeyboardUtils

/**
 * Created by LangSanJie on 2017/4/13.
 */
abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentView = inflater!!.inflate(getLayoutId(), container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        onViewClick()
        processLogic()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun processLogic()


    fun forwardActivity(activityClass: Class<*>, bundle: Bundle?) {
        ZYKeyboardUtils.closeKeyboard(activity)
        val intent = Intent(activity, activityClass)
        if (bundle != null) intent.putExtras(bundle)
        startActivity(intent)
    }

    /**
     * 点击事件
     */
    open protected fun onViewClick(){

    }
}