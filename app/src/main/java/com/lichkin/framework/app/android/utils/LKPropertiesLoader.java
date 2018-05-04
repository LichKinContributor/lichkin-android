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

    static {
        Properties prop = new Properties();
        try {
            @Cleanup
            InputStream is = LKApplication.getInstance().getAssets().open("lichkin.properties");
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        baseUrl = prop.getProperty("lichkin.framework.api.baseUrl");
        timeout = Integer.parseInt(prop.getProperty("lichkin.framework.api.timeout"));
        testWebView = Boolean.parseBoolean(prop.getProperty("lichkin.framework.test.webView"));
    }

}
