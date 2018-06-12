package com.lichkin.framework.app.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.lichkin.framework.app.android.LKApplication;
import com.lichkin.framework.app.android.beans.LKScreen;

import java.util.Locale;

/**
 * 常用工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKAndroidUtils {

    /**
     * 获取资源
     * @return 资源
     */
    private static Resources getResources() {
        return LKApplication.getInstance().getResources();
    }


    /**
     * 获取字符串内容
     * @param resId 资源ID
     * @return 字符串内容
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }


    /**
     * 获取图片资源
     * @param resId 资源ID
     * @return 图片资源
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
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

    /**
     * 获取状态栏高度
     * @return 状态栏高度
     */
    public static float getStatusBarHeight() {
        Resources resources = getResources();
        return resources.getDimension(resources.getIdentifier("status_bar_height", "dimen", "android"));
    }

    /**
     * 获取屏幕分辨率
     * @return 屏幕分辨率
     */
    public static LKScreen getScreenDispaly() {
        WindowManager windowManager = (WindowManager) LKApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return new LKScreen(1080, 1920);
        }

        final DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return new LKScreen(dm.widthPixels, dm.heightPixels);
    }

}
