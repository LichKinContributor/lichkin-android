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

    /** 页面测试 */
    public static final boolean pageTest;

    /** 页面根路径 */
    public static final String pageBaseUrl;

    /** 页面后缀 */
    public static final String pageSuffix;

    /** 请求根路径 */
    public static final String baseUrl;

    /** 请求超时时长 */
    public static final int timeout;

    /** 测试交互 */
    public static final boolean testWebView;

    /** 测试交互页面URL */
    public static final String testWebViewUrl;

    /** 测试接口请求 */
    public static final boolean testRetrofit;

    /** 新闻图片位置。true:顶部；false:底部。 */
    public static final boolean newsPositionTop;

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

        pageTest = getBoolean("lichkin.framework.page.test");
        pageBaseUrl = getString("lichkin.framework.page.baseUrl");
        pageSuffix = getString("lichkin.framework.page.suffix");

        testWebView = getBoolean("lichkin.framework.test.webView");
        testWebViewUrl = getString("lichkin.framework.test.webView.url");

        baseUrl = getString("lichkin.framework.api.baseUrl");
        timeout = getInteger("lichkin.framework.api.timeout");
        testRetrofit = getBoolean("lichkin.framework.test.retrofit");

        newsPositionTop = getBoolean("lichkin.framework.news.image.position.top");
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


    /**
     * 获取浮点
     * @param key 键
     * @return 值
     */
    public static double getDouble(String key) {
        return Double.valueOf(getString(key));
    }


    /**
     * 获取除法公式结果
     * @param key 键
     * @return 值
     */
    public static double getDivision(String key) {
        String division = getString(key);
        String[] result = division.split("/");
        double divisor = Double.valueOf(result[0]);
        double dividend = Double.valueOf(result[1]);
        return divisor / dividend;
    }

}
