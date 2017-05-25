package com.sanjie.captivate.ui.fragment

import android.support.v4.app.Fragment
import com.sanjie.captivate.R
import com.sanjie.captivate.base.BaseFragment
import com.sanjie.captivate.ui.fragment.home.DiscoveryFragment
import com.sanjie.captivate.ui.fragment.home.MusicFragment
import com.sanjie.captivate.ui.fragment.home.PersonalFragment
import com.sanjie.captivate.ui.fragment.home.TravelFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home_page.*

/**
 * 主页
 * Created by SanJie on 2017/5/11.
 */
class HomePageFragment : BaseFragment() {

    companion object {
        val TAG = "HomePageFragment"
    }

    var fragments: ArrayList<Fragment>? = null

    val musicFragment = MusicFragment()
    val discoveryFragment = DiscoveryFragment()
    val travelFragment = TravelFragment()
    val personalFragment = PersonalFragment()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_page
    }

    override fun initView() {
        initFragments()
        home_page_radio_group.setOnCheckedChangeListener { group, checkedId ->
            (0..group.childCount - 1)
                    .filter { group.getChildAt(it).id == checkedId }
                    .forEach { switchFragment(it) }
        }
        home_page_radio_group.check(home_page_radio_group.getChildAt(0).id)
    }

    override fun processLogic() {

    }

    fun initFragments() {

        fragments = ArrayList()

        fragments!!.add(musicFragment)
        fragments!!.add(discoveryFragment)
        fragments!!.add(travelFragment)
        fragments!!.add(personalFragment)

        val transaction = childFragmentManager.beginTransaction()
        Observable.fromIterable(fragments)
                .subscribe {
                    transaction.add(R.id.home_page_content_frame, it)
                }
        transaction.commit()
    }

    fun switchFragment(position: Int) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.hide(fragments!![0]).hide(fragments!![1]).hide(fragments!![2]).hide(fragments!![3])
        transaction.show(fragments!![position]).commit()
    }

}