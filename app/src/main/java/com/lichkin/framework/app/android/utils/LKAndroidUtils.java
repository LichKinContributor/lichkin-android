package com.lichkin.framework.app.android.utils;

import android.content.Context;
import android.content.res.Resources;

import com.lichkin.framework.app.android.LKApplication;

import java.util.Locale;

/**
 * 常用工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
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


    /**
     * 获取国际化类型
     * @return 国际化类型
     */
    public static String getLocale() {
        Resources res = LKApplication.getInstance().getResources();
        Locale locale = res.getConfiguration().locale;

        if (locale.equals(Locale.CHINA)) {
            return Locale.CHINA.toString();
        }
        return Locale.ENGLISH.toString();
    }

}
