package com.lichkin.framework.app.android.utils;

import android.content.Context;
import android.widget.Toast;

import com.lichkin.framework.app.android.LKApplication;

/**
 * 提示工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKToast {

    /**
     * 提示并显示内容
     * @param context 环境上下文
     * @param text 提示内容
     * @param duration 持续时间
     * @see Toast
     * @deprecated 使用showTip或showError
     */
    @Deprecated
    public static void makeTextAndShow(final Context context, final CharSequence text, int duration) {
        //TODO 自定义实现时长
        if (duration > 2000) {
            duration = Toast.LENGTH_LONG;
        } else {
            duration = Toast.LENGTH_SHORT;
        }

        Toast.makeText(context, text, duration).show();
    }

    /**
     * 提示并显示内容
     * @param context 环境上下文
     * @param resId 提示内容
     * @param duration 持续时间
     * @see Toast
     * @deprecated 使用showTip或showError
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public static void makeTextAndShow(final Context context, final int resId, final int duration) {
        makeTextAndShow(context, context.getString(resId), duration);
    }

    /**
     * 提示并显示内容
     * @param text 提示内容
     * @param duration 持续时间
     * @see Toast
     * @deprecated 使用showTip或showError
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public static void makeTextAndShow(final CharSequence text, int duration) {
        makeTextAndShow(LKApplication.getInstance(), text, duration);
    }

    /**
     * 提示并显示内容
     * @param resId 提示内容
     * @param duration 持续时间
     * @see Toast
     * @deprecated 使用showTip或showError
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public static void makeTextAndShow(final int resId, final int duration) {
        makeTextAndShow(LKApplication.getInstance(), resId, duration);
    }

    /**
     * 显示提示信息
     * @param context 环境上下文
     * @param text 提示内容
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void showTip(final Context context, final CharSequence text) {
        makeTextAndShow(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示提示信息
     * @param context 环境上下文
     * @param resId 提示内容
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void showTip(final Context context, final int resId) {
        makeTextAndShow(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示提示信息
     * @param text 提示内容
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void showTip(final CharSequence text) {
        makeTextAndShow(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示提示信息
     * @param resId 提示内容
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void showTip(final int resId) {
        makeTextAndShow(resId, Toast.LENGTH_SHORT);
    }


    /**
     * 显示错误信息
     * @param context 环境上下文
     * @param text 提示内容
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void showError(final Context context, final CharSequence text) {
        makeTextAndShow(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 显示错误信息
     * @param context 环境上下文
     * @param resId 提示内容
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void showError(final Context context, final int resId) {
        makeTextAndShow(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示错误信息
     * @param text 提示内容
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void showError(final CharSequence text) {
        makeTextAndShow(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示错误信息
     * @param resId 提示内容
     * @see Toast
     */
    @SuppressWarnings("deprecation")
    public static void showError(final int resId) {
        makeTextAndShow(resId, Toast.LENGTH_LONG);
    }

}
