package com.lichkin.framework.app.android.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.lichkin.app.android.demo.R;
import com.lichkin.framework.app.android.callbacks.LKBtnCallback;
import com.lichkin.framework.app.android.callbacks.LKInvokeCallback;
import com.lichkin.framework.app.android.widgets.LKDialog;
import com.lichkin.framework.defines.beans.LKRequestBean;
import com.lichkin.framework.defines.beans.LKResponseBean;
import com.lichkin.framework.utils.LKRandomUtils;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

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

    /** API配置键 */
    private String apiKey;


    /**
     * 构造方法
     * @param context 环境上下文
     * @param invokerClass 请求方法类型
     */
    public LKRetrofit(Context context, Class<?> invokerClass) {
        super();
        this.context = context;
        this.invokerClass = invokerClass;
    }


    /**
     * 构造方法
     * @param context 环境上下文
     * @param invokerClass 请求方法类型
     * @param apiKey API配置键
     */
    public LKRetrofit(Context context, Class<?> invokerClass, String apiKey) {
        this(context, invokerClass);
        this.apiKey = apiKey;
    }


    /**
     * 同步调用接口
     * @param in 请求参数
     * @param callback 回调方法
     */
    public void callSync(In in, LKInvokeCallback<In, Out> callback) {
        LKLog.i(String.format("callSync -> requestId[%s] -> requestDatas[%s]", requestId, in.toString()));

        if (LKPropertiesLoader.testRetrofit) {
            handleResponse(requestId, in, callback, getTestResponseBean());
            return;
        }

        // 初始化调用对象
        Call<LKResponseBean<Out>> call = initCall(in, callback);
        if (call == null) {
            return;
        }

        // 同步执行请求，并接收响应内容。
        try {
            Response<LKResponseBean<Out>> response = call.execute();
            // 处理响应信息
            handleResponse(requestId, in, callback, response.body());
        } catch (Throwable e) {
            // 此时发生异常是请求失败了，有可能是网络不通等导致，也有可能是服务器端异常处理没有返回200状态导致。业务代码需要处理异常。
            handleError(callback, context, requestId, in, e);
        }
    }


    /**
     * 异步调用接口
     * @param in 请求参数
     * @param callback 回调方法
     */
    public void callAsync(final In in, final LKInvokeCallback<In, Out> callback) {
        LKLog.i(String.format("callAsync -> requestId[%s] -> requestDatas[%s]", requestId, in.toString()));

        if (LKPropertiesLoader.testRetrofit) {
            handleResponse(requestId, in, callback, getTestResponseBean());
            return;
        }

        // 初始化调用对象
        Call<LKResponseBean<Out>> call = initCall(in, callback);
        if (call == null) {
            return;
        }

        // 异步执行请求，并接收响应内容。
        call.enqueue(new Callback<LKResponseBean<Out>>() {

            @Override
            public void onResponse(@NonNull Call<LKResponseBean<Out>> call, @NonNull Response<LKResponseBean<Out>> response) {
                // 处理响应信息
                handleResponse(requestId, in, callback, response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LKResponseBean<Out>> call, @NonNull Throwable e) {
                // 此时发生异常是请求失败了，有可能时网络不通等导致，也有可能是服务器端异常处理问题没有返回200状态导致。业务代码需要处理异常。
                handleError(callback, context, requestId, in, e);
            }

        });
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
        Object invoker = LKRetrofitLoader.getInstance(apiKey).create(invokerClass);

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
     * @param responseBean 响应信息
     */
    private void handleResponse(String requestId, In in, LKInvokeCallback<In, Out> callback, LKResponseBean<Out> responseBean) {
        if (responseBean == null) {
            // 没有响应对象，不应该出现的情况。
            callback.busError(context, requestId, in, -1, context.getString(R.string.response_error));
            return;
        }

        // 提取响应对象
        int errorCode = responseBean.getErrorCode();
        String errorMessage = responseBean.getErrorMessage();
        Out responseDatas = responseBean.getDatas();

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
     * 请求失败，有可能是网络不通等导致，也有可能是服务器端异常处理没有返回200状态导致。
     * @param callback 回调方法
     * @param context 环境上下文
     * @param requestId 请求ID
     * @param in 请求参数
     * @param e 异常对象
     */
    private void handleError(final LKInvokeCallback<In, Out> callback, Context context, final String requestId, final In in, Throwable e) {
        if (e instanceof java.net.ConnectException) {
            LKDialog dlg = new LKDialog(context, R.string.internet_auth_not_granted).setCancelable(false);
            dlg.setPositiveButton(new LKBtnCallback() {
                @Override
                public void call(Context context, DialogInterface dialog) {
                    callback.connectError(context, requestId, in, dialog);
                }
            });
            dlg.show();
        } else if (e instanceof SocketTimeoutException) {
            LKDialog dlg = new LKDialog(context, R.string.invoke_timeout).setCancelable(false);
            dlg.setPositiveButton(new LKBtnCallback() {
                @Override
                public void call(Context context, DialogInterface dialog) {
                    callback.timeoutError(context, requestId, in, dialog);
                }
            });
            dlg.show();
        } else {
            callback.error(context, requestId, in, e);
        }
    }


    /** 测试结果 */
    private List<LKResponseBean<Out>> testResponseBeans = new ArrayList<>();
    /** 模拟服务器异常 */
    private boolean error_INTERNAL_SERVER_ERROR = false;
    /** 模拟地址错误 */
    private boolean error_NOT_FOUND = false;
    /** 模拟配置错误 */
    private boolean error_CONFIG_ERROR = false;
    /** 模拟参数错误 */
    private boolean error_PARAM_ERROR = false;
    /** 模拟存表参数错误 */
    private boolean error_DB_VALIDATE_ERROR = false;


    /**
     * 增加模拟服务器异常
     */
    public void addTest_INTERNAL_SERVER_ERROR() {
        error_INTERNAL_SERVER_ERROR = true;
    }


    /**
     * 增加模拟地址错误
     */
    public void addTest_NOT_FOUND() {
        error_NOT_FOUND = true;
    }


    /**
     * 增加模拟配置错误
     */
    public void addTest_CONFIG_ERROR() {
        error_CONFIG_ERROR = true;
    }


    /**
     * 增加模拟参数错误
     */
    public void addTest_PARAM_ERROR() {
        error_PARAM_ERROR = true;
    }


    /**
     * 增加模拟存表参数错误
     */
    public void addTest_DB_VALIDATE_ERROR() {
        error_DB_VALIDATE_ERROR = true;
    }


    /**
     * 添加测试响应结果
     * @param responseDatas 响应结果
     */
    public void addTestResponseBeans(Out responseDatas) {
        testResponseBeans.add(new LKResponseBean<>(responseDatas));
    }


    /**
     * 添加测试响应结果
     * @param errorCode 错误编码
     * @param errorMessage 错误提示信息
     */
    public void addTestResponseBeans(int errorCode, String errorMessage) {
        testResponseBeans.add(new LKResponseBean<Out>(errorCode, errorMessage));
    }


    /**
     * 获取测试结果
     * @return 从测试结果中随机读取一个结果
     */
    private LKResponseBean<Out> getTestResponseBean() {
        //模拟一些场景
        if (error_INTERNAL_SERVER_ERROR) {
            testResponseBeans.add(new LKResponseBean<Out>(-1, LKAndroidUtils.getString(R.string.error_INTERNAL_SERVER_ERROR)));
        }
        if (error_NOT_FOUND) {
            testResponseBeans.add(new LKResponseBean<Out>(-2, LKAndroidUtils.getString(R.string.error_NOT_FOUND)));
        }
        if (error_CONFIG_ERROR) {
            testResponseBeans.add(new LKResponseBean<Out>(1, LKAndroidUtils.getString(R.string.error_CONFIG_ERROR)));
        }
        if (error_PARAM_ERROR) {
            testResponseBeans.add(new LKResponseBean<Out>(2, LKAndroidUtils.getString(R.string.error_PARAM_ERROR)));
        }
        if (error_DB_VALIDATE_ERROR) {
            testResponseBeans.add(new LKResponseBean<Out>(3, LKAndroidUtils.getString(R.string.error_DB_VALIDATE_ERROR)));
        }
        if (testResponseBeans.isEmpty()) {
            testResponseBeans.add(new LKResponseBean<Out>(null));
        }
        return testResponseBeans.get(LKRandomUtils.randomInRange(0, testResponseBeans.size() - 1));
    }

}
