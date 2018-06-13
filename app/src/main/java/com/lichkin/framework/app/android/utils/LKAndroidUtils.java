package com.lichkin.framework.app.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
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
     * 获取屏幕像素比例
     * @return 屏幕像素比例
     */
    public static float getDpToPxRatio() {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
    }

    /**
     * 根据dp值获取px值
     * @param dp dp值
     * @return px值
     */
    public static int getPxValueByDpValue(final int dp) {
        return (int) (getDpToPxRatio() * dp + 0.5f);
    }

    /**
     * 根据px值获取dp值
     * @param px px值
     * @return dp值
     */
    public static int getDpValueByPxValue(final int px) {
        return (int) (px / getDpToPxRatio() + 0.5f);
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

    /**
     * 获取布局填充器
     * @return 布局填充器
     */
    public static LayoutInflater getLayoutInflater() {
        return (LayoutInflater) LKApplication.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

}
