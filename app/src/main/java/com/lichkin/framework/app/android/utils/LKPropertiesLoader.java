package com.lichkin.framework.app.android.utils;

import com.lichkin.framework.app.android.LKApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;

/**
 * 配置文件加载工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKPropertiesLoader {

    /** 请求根路径 */
    public static final String baseUrl;

    /** 请求超时时长 */
    public static final int timeout;

    /** 测试交互 */
    public static final boolean testWebView;

    /** 测试接口请求 */
    public static final boolean testRetrofit;

    /** 属性配置 */
    private static final Properties prop;


    static {
        prop = new Properties();
        try {
            @Cleanup
            InputStream is = LKApplication.getInstance().getAssets().open("lichkin.properties");
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        baseUrl = getString("lichkin.framework.api.baseUrl");
        timeout = getInteger("lichkin.framework.api.timeout");
        testWebView = getBoolean("lichkin.framework.test.webView");
        testRetrofit = getBoolean("lichkin.framework.test.retrofit");
    }


    /**
     * 获取字符串
     * @param key 键
     * @return 值
     */
    public static String getString(String key) {
        return prop.getProperty(key);
    }


    /**
     * 获取整数
     * @param key 键
     * @return 值
     */
    public static int getInteger(String key) {
        return Integer.parseInt(getString(key));
    }


    /**
     * 获取布尔
     * @param key 键
     * @return 值
     */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(getString(key));
    }

}
