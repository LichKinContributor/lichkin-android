package com.lichkin.framework.app.android;

import android.app.Application;

/**
 * 应用实例对象
 */
public class LKApplication extends Application {

    /** 实例 */
    private static LKApplication instance;

    /**
     * 获取实例
     * @return 实例
     */
    public static LKApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
