package com.hentica.app.framework;

import android.support.multidex.MultiDexApplication;

/**
 * 程序入口
 *
 * @author mili
 * @createTime 2016-5-30 上午11:47:31
 */
public class AppApplication extends MultiDexApplication {
//public class AppApplication extends Application {

    static AppApplication mInstance;

    public static AppApplication getInstance() {

        return mInstance;
    }

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        LaunchUtil.onAppLaunch(this);
    }
}
