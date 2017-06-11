package com.urgentsms.app;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.utils.Utils;

/**
 * @auther gbh
 * Email:xidian_guobenhao@163.com
 * Created on 2017/6/11.
 */

public class MyApp extends Application{
    public static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context = this;
        Utils.init(this);
    }
}
