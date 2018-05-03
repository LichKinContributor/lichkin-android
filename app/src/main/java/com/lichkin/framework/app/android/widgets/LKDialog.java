package com.lichkin.framework.app.android.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.lichkin.app.android.demo.R;
import com.lichkin.framework.app.android.callbacks.LKBtnCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;

/**
 * 确认对话框
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKDialog {

    /** 上下文环境 */
    private Context context;

    /** 对话框构建对象 */
    private AlertDialog.Builder builder;

    /**
     * 构造方法
     * @param context 上下文环境
     */
    public LKDialog(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }

    /**
     * 构造方法
     * @param context 上下文环境
     * @param message 提示信息
     */
    public LKDialog(Context context, String message) {
        this(context);
        builder.setMessage(message);
    }

    /**
     * 构造方法
     * @param context 上下文环境
     * @param messageResId 提示信息资源ID
     */
    public LKDialog(Context context, int messageResId) {
        this(context, LKAndroidUtils.getString(messageResId));
    }

    /**
     * 设置是否可以在点击对话框以外的地方退出对话框
     * @param cancelable 是否可以
     * @return 对话框对象
     */
    public LKDialog setCancelable(boolean cancelable) {
        builder.setCancelable(cancelable);
        return this;
    }

    /**
     * 设置标题
     * @return 对话框对象
     */
    public LKDialog setDefaultTitle() {
        return setTitle(R.string.dlg_tip_title);
    }

    /**
     * 设置标题
     * @param titleResId 标题资源ID
     * @return 对话框对象
     */
    public LKDialog setTitle(int titleResId) {
        builder.setTitle(LKAndroidUtils.getString(titleResId));
        return this;
    }


    /**
     * 设置确定按钮
     * @param callback 回调方法
     * @return 对话框对象
     */
    public LKDialog setPositiveButton(final LKBtnCallback callback) {
        return setPositiveButton(R.string.btn_positive_name, callback);
    }

    /**
     * 设置确定按钮
     * @param btnNameResId 按钮名称资源ID
     * @param callback 回调方法
     * @return 对话框对象
     */
    public LKDialog setPositiveButton(int btnNameResId, final LKBtnCallback callback) {
        builder.setPositiveButton(LKAndroidUtils.getString(btnNameResId), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.call(context, dialog);
            }

        });
        return this;
    }


    /**
     * 设置取消按钮
     * @return 对话框对象
     */
    public LKDialog setNegativeButton() {
        return setNegativeButton(R.string.btn_negative_name, new LKBtnCallback() {
            @Override
            public void call(Context context, DialogInterface dialog) {
            }
        });
    }


    /**
     * 设置取消按钮
     * @param callback 回调方法
     * @return 对话框对象
     */
    public LKDialog setNegativeButton(final LKBtnCallback callback) {
        return setNegativeButton(R.string.btn_negative_name, callback);
    }


    /**
     * 设置取消按钮
     * @param btnNameResId 按钮名称资源ID
     * @param callback 回调方法
     * @return 对话框对象
     */
    public LKDialog setNegativeButton(int btnNameResId, final LKBtnCallback callback) {
        builder.setNegativeButton(LKAndroidUtils.getString(btnNameResId), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.call(context, dialog);
            }

        });
        return this;
    }

    /**
     * 显示对话框
     */
    public void show() {
        builder.show();
    }


}