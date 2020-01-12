package com.example.teachingblog.base;

import android.app.Application;
import android.os.Handler;

import com.example.teachingblog.utils.LogUtil;

public class BaseApplication extends Application {

    private static Handler sHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化LogUtil
        LogUtil.init(this.getPackageName(), false);

        sHandler = new Handler();
    }

    public static Handler getsHandler() {
        return sHandler;
    }
}
