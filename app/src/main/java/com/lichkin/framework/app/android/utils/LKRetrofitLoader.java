package com.lichkin.framework.app.android.utils;

import android.support.annotation.NonNull;

import com.lichkin.framework.json.LKJsonUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
class LKRetrofitLoader {

    /** retrofit实例集合 */
    private static final Map<String, Retrofit> retrofitMap = new HashMap<>();

    /** retrofit实例 */
    private static final Retrofit retrofit;


    static {
        retrofit = createRetrofit(LKPropertiesLoader.baseUrl, LKPropertiesLoader.timeout);

        String apisKeys = LKPropertiesLoader.getString("lichkin.framework.apis");
        if (apisKeys != null && !"".equals(apisKeys)) {
            String[] apis = apisKeys.split(",");
            for (String api : apis) {
                retrofitMap.put(api, createRetrofit(LKPropertiesLoader.getString("lichkin.framework.api.baseUrl." + api), LKPropertiesLoader.getInteger("lichkin.framework.api.timeout." + api)));
            }
        }
    }


    /**
     * 创建Retrofit实例对象
     * @param baseUrl 根路径
     * @param timeout 超时时长
     * @return Retrofit实例对象
     */
    private static Retrofit createRetrofit(String baseUrl, int timeout) {
        // 从配置文件中读取基本路径，并创建retrofit实例。
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(timeout, TimeUnit.SECONDS);
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
        return new Retrofit.Builder().baseUrl(baseUrl+"/").addConverterFactory(JacksonConverterFactory.create(LKJsonUtils.newObjectMapper())).client(httpClient).build();
    }


    /**
     * 获取实例对象
     * @param apiKey API配置键
     * @return 实例
     */
    static Retrofit getInstance(String apiKey) {
        if (apiKey == null) {
            return retrofit;
        }
        return retrofitMap.get(apiKey);
    }

}
