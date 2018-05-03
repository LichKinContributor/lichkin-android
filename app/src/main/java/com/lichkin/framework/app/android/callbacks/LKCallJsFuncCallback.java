package com.lichkin.framework.app.android.callbacks;

import com.lichkin.framework.defines.beans.LKCallJsFuncCallbackBean;

/**
 * JavaScript调用回调函数
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface LKCallJsFuncCallback {

    /**
     * 执行方法
     * @param data JavaScript回调时传入数据
     */
    void call(LKCallJsFuncCallbackBean data);

}
