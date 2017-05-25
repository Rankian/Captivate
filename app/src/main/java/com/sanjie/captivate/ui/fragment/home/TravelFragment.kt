package com.sanjie.captivate.ui.fragment.home

import android.support.v7.widget.LinearLayoutManager
import com.sanjie.captivate.R
import com.sanjie.captivate.adapter.TravelAdapter
import com.sanjie.captivate.base.BaseFragment
import com.sanjie.captivate.mvp.impl.DownloadFilePresenterImpl
import com.sanjie.captivate.mvp.impl.TravelPresenterImpl
import com.sanjie.captivate.mvp.model.Travel
import com.sanjie.captivate.mvp.presenter.DownloadFilePresenter
import com.sanjie.captivate.mvp.presenter.TravelPresenter
import com.sanjie.zy.widget.ActionSheetDialog
import com.sanjie.zy.widget.ZYToast
import kotlinx.android.synthetic.main.fragment_travel.*
import kotlinx.android.synthetic.main.include_fragment_title_bar.*
import java.io.File

/**
 * Created by SanJie on 2017/5/11.
 */
class TravelFragment : BaseFragment(), TravelPresenter.View, DownloadFilePresenter.View {

    companion object {
        val TAG = "TravelFragment"
    }

    var travelPresenter: TravelPresenter? = null
    var downloadPresenter: DownloadFilePresenter? = null

    var travelList: ArrayList<Travel> = ArrayList()
    var mAdapter: TravelAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_travel
    }

    override fun initView() {

        fragment_title_tv.text = "印记"

        mAdapter = TravelAdapter(activity, travel_recycler_view, travelList)
        mAdapter!!.isLoadMore(false)
//        mAdapter!!.setOnItemLongClickListener { v, position ->
//            showSavePhoto(travelList[position].downloadUrl!!)
//            false
//        }
        travel_recycler_view.layoutManager = LinearLayoutManager(activity)
        travel_recycler_view.adapter = mAdapter

        travel_swipe_refresh_layout.setColorSchemeResources(R.color.main_color)
        travel_swipe_refresh_layout.setOnRefreshListener {
            travelPresenter!!.loadTravel()
        }
    }

    override fun processLogic() {
        downloadPresenter = DownloadFilePresenterImpl(this)
        travelPresenter = TravelPresenterImpl(this)
        travelPresenter!!.loadTravel()
    }

    override fun travelResult(travels: List<Travel>) {
        if (travel_swipe_refresh_layout.isRefreshing) {
            travel_swipe_refresh_layout.isRefreshing = false
        }
        travelList.clear()
        travelList.addAll(travels)
        mAdapter!!.notifyDataSetChanged()
    }

    override fun downloadSuccess(file: File) {
        ZYToast.success("图片已经保存到图库")
    }

    private fun showSavePhoto(url: String) {
        ActionSheetDialog(activity).builder()
                .setTitle("温馨提示")
                .setCancelable(false)
                .addSheetItem("保存图片", resources.getColor(R.color.main_color), {
                    downloadPresenter!!.download(url)
                })
                .show()
    }
}