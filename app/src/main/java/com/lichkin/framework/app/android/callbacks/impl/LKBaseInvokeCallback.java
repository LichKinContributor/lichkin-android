package com.lichkin.framework.app.android.callbacks.impl;

import android.content.Context;
import android.content.DialogInterface;

import com.lichkin.app.android.demo.R;
import com.lichkin.framework.app.android.callbacks.LKInvokeCallback;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.lichkin.framework.defines.beans.LKFieldErrorMessageBean;
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
        LKLog.i(String.format("success -> requestId[%s] -> requestDatas[%s] -> responseDatas -> [%s]", requestId, in.toString(), ((responseDatas == null) ? null : responseDatas.toString())));
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

        LKErrorMessageBean.TYPE errorType = LKErrorMessageBean.TYPE.STRING;
        LKErrorMessageBean errorBean = new LKErrorMessageBean();
        if (errorMessage == null) {
            errorBean.setErrorMessage(null);
        } else {
            if (errorMessage.contains(LKFrameworkStatics.SPLITOR_FIELDS)) {
                errorType = LKErrorMessageBean.TYPE.FIELD_ARR;
                String[] fieldErrorMessages = errorMessage.split(LKFrameworkStatics.SPLITOR_FIELDS);
                LKFieldErrorMessageBean[] fieldErrorMessageArr = new LKFieldErrorMessageBean[fieldErrorMessages.length];
                for (int i = 0; i < fieldErrorMessages.length; i++) {
                    String fieldErrorMessage = fieldErrorMessages[i];
                    fieldErrorMessageArr[i] = new LKFieldErrorMessageBean(fieldErrorMessage);
                }
                errorBean.setFieldErrorMessageArr(fieldErrorMessageArr);
            } else if (errorMessage.contains(LKFrameworkStatics.SPLITOR)) {
                errorType = LKErrorMessageBean.TYPE.STRING_ARR;
                errorBean.setErrorMessageArr(errorMessage.split(LKFrameworkStatics.SPLITOR));
            } else {
                errorBean.setErrorMessage(errorMessage);
            }
        }

        busError(context, in, errorCode, errorType, errorBean);
    }


    /**
     * 请求成功但业务失败
     * @param context 环境上下文
     * @param in 请求参数
     * @param errorCode 错误编码
     * @param errorType 错误提示信息类型
     * @param errorBean 错误提示信息对象
     */
    protected void busError(Context context, In in, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
        switch (errorType) {
            case STRING:
                LKToast.showError(context, errorBean.getErrorMessage());
                break;
            case STRING_ARR:
                String[] errorMessageArr = errorBean.getErrorMessageArr();
                for (String errorMessage : errorMessageArr) {
                    LKToast.showError(context, errorMessage);
                }
                break;
            case FIELD_ARR:
                LKFieldErrorMessageBean[] fieldErrorMessageArr = errorBean.getFieldErrorMessageArr();
                for (LKFieldErrorMessageBean fieldErrorMessage : fieldErrorMessageArr) {
                    LKToast.showError(context, fieldErrorMessage.getFieldName() + " -> " + fieldErrorMessage.getErrorMessage());
                }
                break;
        }
    }


    @Override
    @Deprecated
    public void error(Context context, String requestId, In in, Throwable e) {
        // 这是不应该发生的错误，默认处理不应提示用户，只做日志记录。
        LKLog.e(String.format("error -> requestId[%s] -> requestDatas[%s]", requestId, in.toString()), e);
        LKToast.showError(context, R.string.invoke_error);
    }

    @Override
    public void connectError(Context context, String requestId, In in, DialogInterface dialog) {
        //TODO
    }

    @Override
    public void timeoutError(Context context, String requestId, In in, DialogInterface dialog) {
        //TODO
    }
}
