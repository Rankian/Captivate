package com.sanjie.captivate.demo

import android.view.View

/**
 * Created by SanJie on 2017/6/29.
 */
class WokerController {

    var worker: WorkerInterface? = null

    fun tell(f: (id: Int) -> Unit){
        print(f(5))
    }

    fun main(){
        tell {

        }
        ex {
            it.work(5)
        }
    }

    fun ex(f : (w: WorkerInterface) -> Unit){

    }
}