package com.lichkin.framework.app.android.callbacks.impl;

import android.content.Context;

import com.lichkin.app.android.demo.R;
import com.lichkin.framework.app.android.callbacks.LKInvokeCallback;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKRequestBean;

/**
 * 请求回调基本实现类，子类只需要实现需要特殊实现的方法即可。
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKBaseInvokeCallback<In extends LKRequestBean, Out> implements LKInvokeCallback<In, Out> {

    @Override
    @Deprecated
    public void success(Context context, String requestId, In in, Out responseDatas) {
        // 成功应在开发过程中进行数据处理，默认处理只记录日志。
        LKLog.i(String.format("success -> requestId[%s] -> requestDatas[%s] -> responseDatas -> %s", requestId, in.toString(), responseDatas.toString()));
        success(context, in, responseDatas);
    }

    /**
     * 请求成功且业务成功
     * @param context 环境上下文
     * @param in 请求参数
     * @param responseDatas 响应数据
     */
    protected void success(Context context, In in, Out responseDatas) {
        LKToast.showTip(context, R.string.invoke_success);
    }


    @Override
    @Deprecated
    public void busError(Context context, String requestId, In in, int errorCode, String errorMessage) {
        // 业务失败时，当参数失败时，应在开发及测试阶段解决，则上线项目只有业务处理失败，应提示用户。
        LKLog.w(String.format("busError -> requestId[%s] -> requestDatas[%s] -> errorCode[%s] -> errorMessage[%s]", requestId, in.toString(), errorCode, errorMessage));
        busError(context, in, errorCode, errorMessage);
    }


    /**
     * 请求成功但业务失败
     * @param context 环境上下文
     * @param in 请求参数
     * @param errorCode 错误编码
     * @param errorMessage 错误提示信息
     */
    protected void busError(Context context, In in, int errorCode, String errorMessage) {
        if (errorMessage.contains(LKFrameworkStatics.SPLITOR)) {
            if (errorMessage.contains(LKFrameworkStatics.SPLITOR_FIELDS)) {
                String[] errorMessages = errorMessage.split(LKFrameworkStatics.SPLITOR_FIELDS);
                for (String msg : errorMessages) {
                    String[] msges = msg.split(LKFrameworkStatics.SPLITOR);
                    LKToast.showError(context, msges[0] + " -> " + msges[1]);
                }
            } else {
                String[] msges = errorMessage.split(LKFrameworkStatics.SPLITOR);
                LKToast.showError(context, msges[0] + " -> " + msges[1]);
            }
        } else {
            LKToast.showError(context, errorMessage);
        }
    }


    @Override
    @Deprecated
    public void error(Context context, String requestId, In in, Throwable e) {
        // 这是不应该发生的错误，默认处理不应提示用户，只做日志记录。
        LKLog.e(String.format("error -> requestId[%s] -> requestDatas[%s]", requestId, in.toString()), e);
        if (e instanceof java.net.ConnectException) {
            LKToast.showError(context, R.string.internet_auth_not_granted);
        } else {
            LKToast.showError(context, R.string.invoke_error);
        }
        error(context, in, e);
    }

    /**
     * 请求失败，有可能是网络不通等导致，也有可能是服务器端异常处理没有返回200状态导致。
     * @param context 环境上下文
     * @param in 请求参数
     * @param e 异常对象
     */
    protected void error(Context context, In in, Throwable e) {
    }

}
