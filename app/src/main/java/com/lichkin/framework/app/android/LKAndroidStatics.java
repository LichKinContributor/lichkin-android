package com.lichkin.framework.app.android;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.out.LoginOut;
import com.lichkin.application.beans.out.impl.AccountLoginOut;
import com.lichkin.defines.ActivitiStatics;
import com.lichkin.defines.CoreStatics;
import com.lichkin.defines.ReportStatics;
import com.lichkin.framework.app.android.beans.LKDynamicTab;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKSharedPreferences;

import java.util.List;
import java.util.UUID;

/**
 * 静态值，所有设置方法由框架实现，开发人员应只调用获取方法。
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKAndroidStatics {

    /** 客户端唯一标识 */
    private static String appKey;

    /** 客户端版本号（大版本号） */
    private static Byte versionX;

    /** 客户端版本号（中版本号） */
    private static Byte versionY;

    /** 客户端版本号（小版本号） */
    private static Short versionZ;

    /** 客户端系统版本 */
    private static String osVersion = android.os.Build.VERSION.RELEASE;

    /** 客户端SDK版本 */
    private static int targetSdkVersion;

    /** 生产厂商 */
    private static String brand = android.os.Build.BRAND;

    /** 机型信息 */
    private static String model = android.os.Build.MODEL;

    /** 设备标识 */
    private static String uuid;

    /** 设备标识已读 */
    private static boolean uuidLoaded = false;

    /** 屏幕宽度 */
    private static Short screenWidth;

    /** 屏幕高度 */
    private static Short screenHeight;

    /** 令牌 */
    private static String token;

    /** 登录名 */
    private static String loginName = "";

    /** 等级 */
    private static Integer level;

    /** 头像 */
    private static String photo;

    /** 安全中心地址 */
    private static String securityCenterUrl;

    /** 工作流页面地址 */
    private static String activitiUrl;

    /** 报表页面地址 */
    private static String reportUrl;

    /** 动态TAB页列表 */
    private static List<LKDynamicTab> dynamicTabs;

    /**
     * 获取客户端唯一标识
     * @return 客户端唯一标识
     */
    public static String appKey() {
        return appKey;
    }

    /**
     * 设置客户端唯一标识
     * @param appKey 客户端唯一标识
     */
    @Deprecated
    public static void appKey(String appKey) {
        LKAndroidStatics.appKey = appKey;
    }

    /**
     * 获取大版本号
     * @return 大版本号
     */
    public static Byte versionX() {
        return versionX;
    }

    /**
     * 设置大版本号
     * @param versionX 大版本号
     */
    @Deprecated
    public static void versionX(Byte versionX) {
        LKAndroidStatics.versionX = versionX;
    }

    /**
     * 获取中版本号
     * @return 中版本号
     */
    public static Byte versionY() {
        return versionY;
    }

    /**
     * 设置中版本号
     * @param versionY 中版本号
     */
    @Deprecated
    public static void versionY(Byte versionY) {
        LKAndroidStatics.versionY = versionY;
    }

    /**
     * 获取小版本号
     * @return 小版本号
     */
    public static Short versionZ() {
        return versionZ;
    }

    /**
     * 设置小版本号
     * @param versionZ 小版本号
     */
    @Deprecated
    public static void versionZ(Short versionZ) {
        LKAndroidStatics.versionZ = versionZ;
    }

    /**
     * 获取客户端系统版本
     * @return 客户端系统版本
     */
    public static String osVersion() {
        return osVersion;
    }

    /**
     * 获取客户端SDK版本
     * @return 客户端SDK版本
     */
    public static int targetSdkVersion() {
        return targetSdkVersion;
    }

    /**
     * 设置客户端SDK版本
     * @param targetSdkVersion 客户端SDK版本
     */
    @Deprecated
    public static void targetSdkVersion(int targetSdkVersion) {
        LKAndroidStatics.targetSdkVersion = targetSdkVersion;
    }

    /**
     * 获取生产厂商
     * @return 生产厂商
     */
    public static String brand() {
        return brand;
    }

    /**
     * 获取机型信息
     * @return 机型信息
     */
    public static String model() {
        return model;
    }

    /**
     * 获取设备标识
     * @return 设备标识
     */
    public static String uuid() {
        //没有加载过则先加载
        if (!uuidLoaded) {
            uuidLoaded = true;

            uuid = LKSharedPreferences.getString(LKSharedPreferences.UUID, null);
            if (uuid == null) {
                uuid = UUID.randomUUID().toString();
                LKSharedPreferences.putString(LKSharedPreferences.UUID, uuid);
            }
        }
        return uuid;
    }

    /**
     * 获取屏幕宽度
     * @return 屏幕宽度
     */
    public static Short screenWidth() {
        return screenWidth;
    }

    /**
     * 设置屏幕宽度
     * @param screenWidth 屏幕宽度
     */
    @Deprecated
    public static void screenWidth(Short screenWidth) {
        LKAndroidStatics.screenWidth = screenWidth;
    }

    /**
     * 获取屏幕高度
     * @return 屏幕高度
     */
    public static Short screenHeight() {
        return screenHeight;
    }

    /**
     * 设置屏幕高度
     * @param screenHeight 屏幕高度
     */
    @Deprecated
    public static void screenHeight(Short screenHeight) {
        LKAndroidStatics.screenHeight = screenHeight;
    }

    /**
     * 保存登录信息
     * @param login 登录信息
     */
    @SuppressWarnings("deprecation")
    public static void saveLoginInfo(LoginOut login) {
        if (login == null) {
            photo(null);
            level(1);
            securityCenterUrl(null);
            activitiUrl(null);
            reportUrl(null);
            token(null);
            loginName(null);
            return;
        }
        photo(login.getPhoto());
        level(login.getLevel());
        securityCenterUrl(login.getSecurityCenterUrl());
        activitiUrl(login.getApiServerRootUrl() + CoreStatics.SSO_URL + ActivitiStatics.ACTIVITI_CENTER_URL);
        reportUrl(login.getApiServerRootUrl() + CoreStatics.SSO_URL + ReportStatics.REPORT_CENTER_URL);
        if (login instanceof AccountLoginOut) {
            token(((AccountLoginOut) login).getToken());
            loginName(((AccountLoginOut) login).getLoginName());
        }
    }

    /**
     * 获取令牌
     * @return 令牌
     */
    public static String token() {
        if (token == null || "".equals(token)) {
            token = LKSharedPreferences.getString(LKSharedPreferences.TOKEN, "");
        }
        return token;
    }

    /**
     * 设置令牌
     * @param token 令牌
     */
    private static void token(String token) {
        LKAndroidStatics.token = token;
        LKSharedPreferences.putString(LKSharedPreferences.TOKEN, token);
    }

    /**
     * 获取登录名
     * @return 登录名
     */
    public static String loginName() {
        if (loginName == null || "".equals(loginName)) {
            loginName = LKSharedPreferences.getString(LKSharedPreferences.LOGIN_NAME, "");
            if ("".equals(loginName)) {
                loginName = LKAndroidUtils.getString(R.string.no_loginName);
            }
        }
        return loginName;
    }

    /**
     * 设置登录名
     * @param loginName 登录名
     */
    public static void loginName(String loginName) {
        LKAndroidStatics.loginName = loginName;
        LKSharedPreferences.putString(LKSharedPreferences.LOGIN_NAME, loginName);
    }

    /**
     * 获取等级
     * @return 等级
     */
    public static Integer level() {
        if (level == null) {
            level = LKSharedPreferences.getInt(LKSharedPreferences.LEVEL, 1);
        }
        return level;
    }

    /**
     * 设置等级
     * @param level 等级
     */
    private static void level(int level) {
        LKAndroidStatics.level = level;
        LKSharedPreferences.putInt(LKSharedPreferences.LEVEL, level);
    }

    /**
     * 获取头像
     * @return 头像
     */
    public static String photo() {
        if (photo == null || "".equals(photo)) {
            photo = LKSharedPreferences.getString(LKSharedPreferences.PHOTO, "");
        }
        return photo;
    }

    /**
     * 设置头像
     * @param photo 头像
     */
    @Deprecated
    public static void photo(String photo) {
        LKAndroidStatics.photo = photo;
        LKSharedPreferences.putString(LKSharedPreferences.PHOTO, photo);
    }

    /**
     * 获取安全中心地址
     * @return 安全中心地址
     */
    public static String securityCenterUrl() {
        if (securityCenterUrl == null || "".equals(securityCenterUrl)) {
            securityCenterUrl = LKSharedPreferences.getString(LKSharedPreferences.SECURITY_CENTER_URL, "");
        }
        return securityCenterUrl;
    }

    /**
     * 设置安全中心地址
     * @param securityCenterUrl 安全中心地址
     */
    private static void securityCenterUrl(String securityCenterUrl) {
        LKAndroidStatics.securityCenterUrl = securityCenterUrl;
        LKSharedPreferences.putString(LKSharedPreferences.SECURITY_CENTER_URL, securityCenterUrl);
    }

    /**
     * 获取工作流页面地址
     * @return 工作流页面地址
     */
    public static String activitiUrl() {
        if (activitiUrl == null || "".equals(activitiUrl)) {
            activitiUrl = LKSharedPreferences.getString(LKSharedPreferences.ACTIVITI_URL, "");
        }
        return activitiUrl;
    }

    /**
     * 设置工作流页面地址
     * @param activitiUrl 工作流页面地址
     */
    private static void activitiUrl(String activitiUrl) {
        LKAndroidStatics.activitiUrl = activitiUrl;
        LKSharedPreferences.putString(LKSharedPreferences.ACTIVITI_URL, activitiUrl);
    }

    /**
     * 设置报表页面地址
     * @param reportUrl 报表页面地址
     */
    private static void reportUrl(String reportUrl) {
        LKAndroidStatics.reportUrl = reportUrl;
        LKSharedPreferences.putString(LKSharedPreferences.REPORT_URL, reportUrl);
    }

    /**
     * 获取报表页面地址
     * @return 报表页面地址
     */
    public static String reportUrl() {
        if (reportUrl == null || "".equals(reportUrl)) {
            reportUrl = LKSharedPreferences.getString(LKSharedPreferences.REPORT_URL, "");
        }
        return reportUrl;
    }

    /**
     * 获取动态TAB页列表
     * @return 动态TAB页列表
     */
    public static List<LKDynamicTab> dynamicTabs() {
        if (dynamicTabs == null || dynamicTabs.isEmpty()) {
            String dynamicTabsStr = LKSharedPreferences.getString(LKSharedPreferences.TABS, "");
            if (!"".equals(dynamicTabsStr)) {
                dynamicTabs = LKDynamicTab.convertListObject(dynamicTabsStr);
            }
        }
        return dynamicTabs;
    }

    /**
     * 设置动态TAB页列表
     * @param dynamicTabs 动态TAB页列表
     */
    public static void dynamicTabs(List<LKDynamicTab> dynamicTabs) {
        LKAndroidStatics.dynamicTabs = dynamicTabs;
        StringBuilder sb = new StringBuilder();
        if (dynamicTabs != null && !dynamicTabs.isEmpty()) {
            for (LKDynamicTab info : dynamicTabs) {
                sb.append(info.convertString());
            }
        }
        LKSharedPreferences.putString(LKSharedPreferences.TABS, sb.toString());
    }

}
