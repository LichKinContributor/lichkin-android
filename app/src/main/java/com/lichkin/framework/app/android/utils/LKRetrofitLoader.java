package com.lichkin.framework.app.android.utils;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        // 从配置文件中读取基本路径，并创建retrofit实例。
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(LKPropertiesLoader.timeout, TimeUnit.SECONDS);
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Accept-Language", LKAndroidUtils.getLocale())
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient httpClient = client.build();
        retrofit = new Retrofit.Builder().baseUrl(LKPropertiesLoader.baseUrl).addConverterFactory(JacksonConverterFactory.create()).client(httpClient).build();
    }


    /**
     * 获取实例对象
     * @return 实例
     */
    static Retrofit getInstance() {
        return retrofit;
    }

}
