package com.lichkin.framework.app.android.callbacks;

import android.content.Context;
import android.content.DialogInterface;

import com.lichkin.framework.defines.beans.LKRequestBean;

/**
 * 请求回调函数
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface LKInvokeCallback<In extends LKRequestBean, Out> {

    /**
     * 请求成功且业务成功
     * @param context 环境上下文
     * @param requestId 请求ID
     * @param in 请求参数
     * @param responseDatas 响应数据
     */
    void success(Context context, String requestId, In in, Out responseDatas);


    /**
     * 请求成功但业务失败
     * @param context 环境上下文
     * @param requestId 请求ID
     * @param in 请求参数
     * @param errorCode 错误编码
     * @param errorMessage 错误提示信息
     */
    void busError(Context context, String requestId, In in, int errorCode, String errorMessage);


    /**
     * 请求失败，服务器端异常处理没有返回200状态导致。
     * @param context 环境上下文
     * @param requestId 请求ID
     * @param in 请求参数
     * @param e 异常对象
     */
    void error(Context context, String requestId, In in, Throwable e);

    /**
     * 网络链接异常
     * @param context 环境上下文
     * @param requestId 请求ID
     * @param in 请求参数
     * @param dialog 错误提示弹窗对象
     */
    void connectError(Context context, String requestId, In in, DialogInterface dialog);

    /**
     * 网络链接超时异常
     * @param context 环境上下文
     * @param requestId 请求ID
     * @param in 请求参数
     * @param dialog 错误提示弹窗对象
     */
    void timeoutError(Context context, String requestId, In in, DialogInterface dialog);

}
