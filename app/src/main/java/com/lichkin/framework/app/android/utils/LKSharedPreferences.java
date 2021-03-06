package com.lichkin.framework.app.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.lichkin.framework.app.android.LKApplication;

import java.util.Set;

/**
 * 存储文件
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKSharedPreferences {

    /** UUID存储键 */
    public static final String UUID = "lichkin.framework.app.uuid";

    /** 令牌存储键 */
    public static final String TOKEN = "lichkin.framework.app.token";

    /** 登录名键 */
    public static final String LOGIN_NAME = "lichkin.framework.app.loginName";

    /** 等级键 */
    public static final String LEVEL = "lichkin.framework.app.level";

    /** 头像键 */
    public static final String PHOTO = "lichkin.framework.app.photo";

    /** 安全中心地址键 */
    public static final String SECURITY_CENTER_URL = "lichkin.framework.app.securityCenterUrl";

    /** 工作流页面地址键 */
    public static final String ACTIVITI_URL = "lichkin.framework.app.activitiUrl";

    /** 报表页面地址键 */
    public static final String REPORT_URL = "lichkin.framework.app.reportUrl";

    /** 公司列表键 */
    public static final String TABS = "lichkin.framework.app.tabs";

    /** 存储文件名 */
    private static final String SHARED_PREFERENCES_NAME = "LichKin";

    /** 存储对象 */
    private static final SharedPreferences preferences;

    static {
        preferences = LKApplication.getInstance().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 存储内容
     * @param key 键
     * @param value 值
     */
    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 获取值
     * @param key 键
     * @param defaultValue 默认值
     * @return 值
     */
    public static String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    /**
     * 存储内容
     * @param key 键
     * @param value 值
     */
    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 获取值
     * @param key 键
     * @param defaultValue 默认值
     * @return 值
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     * 存储内容
     * @param key 键
     * @param value 值
     */
    public static void putFloat(String key, float value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 获取值
     * @param key 键
     * @param defaultValue 默认值
     * @return 值
     */
    public static float getFloat(String key, float defaultValue) {
        return preferences.getFloat(key, defaultValue);
    }

    /**
     * 存储内容
     * @param key 键
     * @param value 值
     */
    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 获取值
     * @param key 键
     * @param defaultValue 默认值
     * @return 值
     */
    public static int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    /**
     * 存储内容
     * @param key 键
     * @param value 值
     */
    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 获取值
     * @param key 键
     * @param defaultValue 默认值
     * @return 值
     */
    public static long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    /**
     * 存储内容
     * @param key 键
     * @param value 值
     */
    public static void putStringSet(String key, Set<String> value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    /**
     * 获取值
     * @param key 键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Set<String> getStringSet(String key, Set<String> defaultValue) {
        return preferences.getStringSet(key, defaultValue);
    }

}
