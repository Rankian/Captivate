package com.sanjie.captivate.util;

import android.app.Notification;

/**
 * Created by SanJie on 2017/6/20.
 */

public class Test2 {

    private void m(){
        String str = "a";

        Notification notification;
        if(str.matches("[a-z]")){
            str = "B";
        }
    }
}
