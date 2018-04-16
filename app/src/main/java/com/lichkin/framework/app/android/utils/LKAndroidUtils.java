package com.lichkin.framework.app.android.utils;

import android.content.Context;

import com.lichkin.framework.app.android.LKApplication;

/**
 * 常用工具类
 */
public class LKAndroidUtils {

    /**
     * 获取字符串内容
     * @param resId 资源ID
     * @return 字符串内容
     */
    public static String getString(int resId) {
        return LKApplication.getInstance().getString(resId);
    }

    /**
     * 获取字符串内容
     * @param context 环境上下文
     * @param resId 资源ID
     * @return 字符串内容
     */
    public static String getString(Context context, int resId) {
        return context.getString(resId);
    }


}
