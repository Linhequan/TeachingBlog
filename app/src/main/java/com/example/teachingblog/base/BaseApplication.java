package com.example.teachingblog.base;

import android.app.Application;

import com.example.teachingblog.utils.LogUtil;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化LogUtil
        LogUtil.init(this.getPackageName(), false);
    }
}
