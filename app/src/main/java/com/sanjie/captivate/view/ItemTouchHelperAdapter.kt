package com.sanjie.captivate.view

/**
 * Created by SanJie on 2017/5/10.
 */
interface ItemTouchHelperAdapter {
    //数据交换，移动
    fun onItemMove(fromPosition: Int, toPosition: Int)
    //数据删除，华东
    fun onItemDelete(position: Int)
}