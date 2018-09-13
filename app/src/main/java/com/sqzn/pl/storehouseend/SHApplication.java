package com.sqzn.pl.storehouseend;

import android.app.Application;

public class SHApplication extends Application {


    private static SHApplication instance;

    /**
     * 单例模式中获取唯一的MyApplication实例
     *
     * @return
     */
    public static SHApplication getInstance() {
        if (null == instance) {
            instance = new SHApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}
