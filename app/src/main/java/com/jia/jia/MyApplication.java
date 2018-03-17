package com.jia.jia;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class MyApplication extends Application {


    public final static String baseUrl = "http://192.168.199.205:8080/";

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
