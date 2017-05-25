package com.sanjie.captivate.util;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by LangSanJie on 2017/5/3.
 */

public class Test {

    String[] a = new String[]{"art"};

    List<String> strings = new ArrayList<>();

    public void add() {
        strings.add("a");
    }

    private Test instance;

    public Test getInstance() {
        return instance;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

            }
        }
    };

    class MThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }

    private void Start(){
        int a = 10;
        int b = 2;
        int c = a / b;

    }
}
