package com.lichkin.framework.app.android.utils;

import android.util.Log;

/**
 * 日志工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKLog {
    /** 日志标识 */
    private static final String TAG = "LichKin";

    /**
     * debug日志
     * @param msg 日志内容
     */
    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    /**
     * debug日志
     * @param msg 日志内容
     * @param t 异常对象
     */
    public static void d(String msg, Throwable t) {
        Log.d(TAG, msg, t);
    }

    /**
     * info日志
     * @param msg 日志内容
     */
    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    /**
     * info日志
     * @param msg 日志内容
     * @param t 异常对象
     */
    public static void i(String msg, Throwable t) {
        Log.i(TAG, msg, t);
    }

    /**
     * warning日志
     * @param msg 日志内容
     */
    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    /**
     * warning日志
     * @param msg 日志内容
     * @param t 异常对象
     */
    public static void w(String msg, Throwable t) {
        Log.w(TAG, msg, t);
    }

    /**
     * error日志
     * @param msg 日志内容
     */
    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    /**
     * error日志
     * @param msg 日志内容
     * @param t 异常对象
     */
    public static void e(String msg, Throwable t) {
        Log.e(TAG, msg, t);
    }

}
