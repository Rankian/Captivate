package com.sanjie.captivate.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout
import com.sanjie.captivate.R
import com.sanjie.zy.common.ZYActivityStack
import com.sanjie.zy.utils.ZYKeyboardUtils
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import kotlinx.android.synthetic.main.ic_toolbar.*

/**
 * Created by LangSanJie on 2017/4/12.
 */
open abstract class BaseActivity : AppCompatActivity(), BGASwipeBackLayout.PanelSlideListener {

    var swipeBackLayout: BGASwipeBackLayout? = null

    var mToolbar: Toolbar? = null
    var tvTitleTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initSwipeBackLayout()
        super.onCreate(savedInstanceState)
        ZYActivityStack.getInstance().addActivity(this)
        setContentView(getLayoutId())
        if (hasToolbar()) {
            initAppToolBar()
        }

        initView()
        processLogic()
        checkPermissions()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    /**
     * 获取标题
     * @return
     */
    abstract fun getToolBarTitle(): String?

    open protected fun checkPermissions(){

    }

    /**
     * 点击事件
     */
    open protected fun onViewClick(){

    }

    open protected fun hasToolbarTitle(): Boolean{
        return true
    }

    /**
     * toolbar 右侧按钮点击
     */
    open fun onClickRightButton(view: View){

    }

    open protected fun showRightButton(show: Boolean){
        if(hasToolbar()){
            toolbar_right_btn.visibility = if(show) VISIBLE else GONE
        }
    }

    /**
     * 是否含有 Toolbar
     * 默认 返回true， 若不含，重写该方法返回false 即可
     * @return
     */
    open protected fun hasToolbar(): Boolean {
        return true
    }

    /**
     * 是否支持返回按钮
     * 默认 返回true， 若不支持，重写该方法返回false 即可
     * @return
     */
    open protected fun isSupportBack(): Boolean {
        return true
    }

    /**
     * 处理业务逻辑，状态恢复等操作
     */
    protected abstract fun processLogic()

    protected fun initAppToolBar() {
        mToolbar = findViewById(R.id.toolbar) as Toolbar
        if(hasToolbarTitle()){
            tvTitleTextView = findViewById(R.id.tv_titleTextView) as TextView
            tvTitleTextView!!.text = getToolBarTitle()
        }
        setSupportActionBar(mToolbar)
        //隐藏返回按钮 NavigationIcon
        supportActionBar!!.setDisplayHomeAsUpEnabled(isSupportBack())
        supportActionBar!!.title = ""
        if (isSupportBack()) {
            mToolbar!!.setNavigationOnClickListener(View.OnClickListener { finish() })
        }
    }

    private fun initSwipeBackLayout() {
        if (isSupportSwipeBack()) {
            swipeBackLayout = BGASwipeBackLayout(this)
            swipeBackLayout!!.attachToActivity(this)
            swipeBackLayout!!.setPanelSlideListener(this)

            // 下面四项项可以不配置，这里只是为了讲述接口用法。
            // 如果需要启用微信滑动返回样式，必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this)

            // 设置滑动返回是否可用。默认值为 true
            swipeBackLayout!!.setSwipeBackEnable(true)
            // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
            swipeBackLayout!!.setIsOnlyTrackingLeftEdge(true)
            // 设置是否是微信滑动返回样式。默认值为 true
            swipeBackLayout!!.setIsWeChatStyle(true)
            // 设置阴影资源 id。默认值为 R.drawable.bga_swipebacklayout_shadow
            swipeBackLayout!!.setShadowResId(R.drawable.bga_swipebacklayout_shadow)
            // 设置是否显示滑动返回的阴影效果。默认值为 true
            swipeBackLayout!!.setIsNeedShowShadow(true)
            // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
            swipeBackLayout!!.setIsShadowAlphaGradient(true)
        }
    }


    protected fun setSwipeBackEnable(swipeBackEnable: Boolean) {
        if (swipeBackLayout != null) {
            swipeBackLayout!!.setSwipeBackEnable(swipeBackEnable)
        }
    }

    /**
     * 是否支持滑动返回
     * 默认支持，若不支持重写改方法返回false即可
     */
    open protected fun isSupportSwipeBack(): Boolean {
        return true
    }

    override fun onPanelClosed(panel: View?) {
    }

    override fun onPanelSlide(panel: View?, slideOffset: Float) {
    }

    override fun onPanelOpened(panel: View?) {
        swipeBackward()
    }

    /**
     * 跳转到下一个Activity，不销毁当前Activity

     * @param cls 下一个Activity的Class
     */
    fun forwardActivity(cls: Class<*>, bundle: Bundle?) {
        ZYKeyboardUtils.closeKeyboard(this)
        val intent = Intent(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        executeForwardAnim()
    }

    /**
     * 执行跳转到下一个Activity的动画
     */
    fun executeForwardAnim() {
        overridePendingTransition(R.anim.activity_forward_enter, R.anim.activity_forward_exit)
    }

    /**
     * 回到上一个Activity，并销毁当前Activity
     */
    fun backward() {
        ZYKeyboardUtils.closeKeyboard(this)
        finish()
        executeBackwardAnim()
    }

    fun swipeBackward() {
        ZYKeyboardUtils.closeKeyboard(this)
        finish()

    }

    /**
     * 执行回到到上一个Activity的动画
     */
    fun executeBackwardAnim() {
        overridePendingTransition(R.anim.activity_backward_enter, R.anim.activity_backward_exit)
    }

    /**
     * 执行滑动返回到到上一个Activity的动画
     */
    fun executeSwipeBackAnim() {
        overridePendingTransition(R.anim.activity_swipeback_enter, R.anim.activity_swipeback_exit)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}