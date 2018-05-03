package com.lichkin.framework.app.android.callbacks;

import android.content.Context;
import android.content.DialogInterface;

/**
 * 按钮回调函数
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface LKBtnCallback {

    /**
     * 执行方法
     * @param context 弹窗时使用的上下文环境
     * @param dialog 弹窗对象
     */
    void call(Context context, DialogInterface dialog);

}
