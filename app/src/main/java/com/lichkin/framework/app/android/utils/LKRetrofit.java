package com.lichkin.framework.app.android.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lichkin.demo.R;
import com.lichkin.framework.app.android.LKApplication;
import com.lichkin.framework.app.android.callbacks.LKInvokeCallback;
import com.lichkin.framework.defines.beans.LKRequestBean;
import com.lichkin.framework.defines.beans.LKResponseBean;
import com.lichkin.framework.utils.LKRandomUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 请求工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKRetrofit<In extends LKRequestBean, Out> {

    /** 方法名 */
    private static final String METHOD_NAME = "invoke";

    /** 请求方法类型 */
    private Class<?> invokerClass;

    /** 环境上下文 */
    private Context context;
    /** 请求ID */
    private String requestId = LKRandomUtils.create(32);

    /**
     * 构造方法
     * @param invokerClass 请求方法类型
     */
    public LKRetrofit(Context context, Class<?> invokerClass) {
        super();
        this.context = context;
        this.invokerClass = invokerClass;
    }


    /**
     * 构造方法
     * @param invokerClass 请求方法类型
     */
    public LKRetrofit(Class<?> invokerClass) {
        super();
        this.context = LKApplication.getInstance();
        this.invokerClass = invokerClass;
    }

    /**
     * 调用接口
     * @param sync 是否同步
     * @param in 请求参数
     * @param callback 回调方法
     */
    public void call(boolean sync, In in, LKInvokeCallback<In, Out> callback) {
        if (sync) {
            callSync(in, callback);
        } else {
            callAsync(in, callback);
        }
    }

    /**
     * 初始化调用对象
     * @param in 请求参数
     * @param callback 回调方法
     * @return 调用对象
     */
    @SuppressWarnings("unchecked")
    private Call<LKResponseBean<Out>> initCall(In in, LKInvokeCallback<In, Out> callback) {
        // 创建请求类类对象
        Object invoker = LKRetrofitLoader.getInstance().create(invokerClass);

        // 通过反射创建调用对象
        try {
            return (Call<LKResponseBean<Out>>) invokerClass.getMethod(METHOD_NAME, in.getClass()).invoke(invoker, in);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 处理响应信息
     * @param requestId 请求ID
     * @param in 请求参数
     * @param callback 回调方法
     * @param response 响应信息
     */
    private void handleResponse(String requestId, In in, LKInvokeCallback<In, Out> callback, Response<LKResponseBean<Out>> response) {
        // 解析响应内容取得响应对象
        LKResponseBean<Out> bean = response.body();

        if (bean == null) {
            // errorCode不为0时，表示请求成功，但是业务失败了。则业务代码需要处理错误内容。通常只需要提示错误信息即可。
            callback.busError(context, requestId, in, -1, context.getString(R.string.response_error));
            return;
        }

        // 提取响应对象
        int errorCode = bean.getErrorCode();
        String errorMessage = bean.getErrorMessage();
        Out responseDatas = bean.getDatas();

        // 首先判断错误编码
        if (errorCode == 0) {
            // errorCode为0时，表示请求成功，并且业务也是成功的。则业务代码仅需处理响应数据即可。
            callback.success(context, requestId, in, responseDatas);
        } else {
            // errorCode不为0时，表示请求成功，但是业务失败了。则业务代码需要处理错误内容。通常只需要提示错误信息即可。
            callback.busError(context, requestId, in, errorCode, errorMessage);
        }
    }

    /**
     * 同步调用接口
     * @param in 请求参数
     * @param callback 回调方法
     */
    public void callSync(In in, LKInvokeCallback<In, Out> callback) {
        LKLog.i(String.format("callSync -> requestId[%s] -> requestDatas[%s]", requestId, in.toString()));

        // 初始化调用对象
        Call<LKResponseBean<Out>> call = initCall(in, callback);
        if (call == null) {
            return;
        }

        // 同步执行请求，并接收响应内容。
        try {
            Response<LKResponseBean<Out>> response = call.execute();
            // 处理响应信息
            handleResponse(requestId, in, callback, response);
        } catch (Throwable e) {
            // 此时发生异常是请求失败了，有可能是网络不通等导致，也有可能是服务器端异常处理没有返回200状态导致。业务代码需要处理异常。
            callback.error(context, requestId, in, e);
        }
    }


    /**
     * 异步调用接口
     * @param in 请求参数
     * @param callback 回调方法
     */
    public void callAsync(final In in, final LKInvokeCallback<In, Out> callback) {
        LKLog.i(String.format("callAsync -> requestId[%s] -> requestDatas[%s]", requestId, in.toString()));

        // 初始化调用对象
        Call<LKResponseBean<Out>> call = initCall(in, callback);
        if (call == null) {
            return;
        }

        // 异步执行请求，并接收响应内容。
        call.enqueue(new Callback<LKResponseBean<Out>>() {

            @Override
            public void onResponse(Call<LKResponseBean<Out>> call, Response<LKResponseBean<Out>> response) {
                // 处理响应信息
                handleResponse(requestId, in, callback, response);
            }

            @Override
            public void onFailure(@NonNull Call<LKResponseBean<Out>> call, Throwable e) {
                // 此时发生异常是请求失败了，有可能时网络不通等导致，也有可能是服务器端异常处理问题没有返回200状态导致。业务代码需要处理异常。
                callback.error(context, requestId, in, e);
            }

        });
    }

}
