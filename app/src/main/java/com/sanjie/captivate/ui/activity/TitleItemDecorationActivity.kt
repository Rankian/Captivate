package com.sanjie.captivate.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseActivity
import com.sanjie.captivate.mvp.model.City
import com.sanjie.captivate.view.decoration.TitleItemDecoration
import com.sanjie.zy.adpter.ZYRecyclerViewAdapter
import com.sanjie.zy.adpter.ZYViewHolder
import com.sanjie.zy.adpter.decoration.DividerDecoration
import com.sanjie.zy.utils.ZYDisplayUtils
import com.sanjie.zy.utils.statusbar.ZYStatusBarUtil
import kotlinx.android.synthetic.main.activity_title_item_decoration.*

/**
 * Created by SanJie on 2017/6/20.
 */
class TitleItemDecorationActivity : BaseActivity() {

    companion object {
        val TAG = "TitleItemDecorationActivity"
    }

    private var mDatas: ArrayList<City>? = null

    private var layoutManager: LinearLayoutManager? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_title_item_decoration
    }

    override fun initView() {
        ZYStatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.main_color), 0)
        layoutManager = LinearLayoutManager(this)
        title_item_decoration_recycler_view.layoutManager = layoutManager
        initDatas(resources.getStringArray(R.array.provinces))
        title_item_decoration_recycler_view.adapter = CityAdapter()
        title_item_decoration_recycler_view.addItemDecoration(TitleItemDecoration(this, mDatas!!))
        title_item_decoration_recycler_view.addItemDecoration(DividerDecoration(resources.getColor(R.color.gray_30), ZYDisplayUtils.dp2px(1F)))

        title_item_decoration_index_bar.setPressedShowTextView(title_item_decoration_SideBarHint_tv)
                .setNeedRealIndex(true)
                .setLayoutManager(layoutManager!!)
                .setSourceDatas(mDatas!!)
    }

    override fun getToolBarTitle(): String? {
        return "分类列表"
    }

    override fun processLogic() {
    }

    private fun initDatas(datas: Array<String>) {
        mDatas = ArrayList()
        for (i in 0..datas.size - 1) {
            val city = City()
            city.city = datas[i]
            mDatas!!.add(city)
        }
    }

    inner class CityAdapter : ZYRecyclerViewAdapter<City>(title_item_decoration_recycler_view, mDatas!!, R.layout.item_city) {
        override fun bindData(holder: ZYViewHolder?, city: City?, position: Int) {
            holder!!.setText(R.id.item_city_name_tv, city!!.city)
        }
    }
}