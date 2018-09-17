package com.sqzn.pl.storehouseend;

import android.app.Activity;
import android.app.Application;

import com.sqzn.pl.storehouseend.Utils.Util;

import java.util.LinkedList;
import java.util.List;

public class SHApplication extends Application {


    private static SHApplication instance;
    private List<Activity> activitys = new LinkedList<Activity>();

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
        instance = this;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() >= 0) {
            activitys.add(activity);
        }
    }

    public void removeActivity() {
        if (activitys != null && activitys.size() > 0) {
            activitys.remove(activitys.size() - 1);
        }
    }
    // 遍历所有Activity并finish
    public void exit() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
            activitys.clear();
        }
    }


}
