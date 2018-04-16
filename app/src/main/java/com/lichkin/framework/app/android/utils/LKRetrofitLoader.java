package com.lichkin.framework.app.android.utils;

import com.lichkin.framework.app.android.LKApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * 基于retrofit自定义扩展工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKRetrofitLoader {

    /** retrofit实例 */
    private static final Retrofit retrofit;

    static {
        Properties prop = new Properties();
        try {
            @Cleanup
            InputStream is = LKApplication.getInstance().getAssets().open("lichkin.properties");
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 从配置文件中读取基本路径，并创建retrofit实例。
        String baseUrl = prop.getProperty("lichkin.framework.app.baseUrl");
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(JacksonConverterFactory.create()).build();
    }


    /**
     * 获取实例对象
     * @return 实例
     */
    static Retrofit getInstance() {
        return retrofit;
    }

}
