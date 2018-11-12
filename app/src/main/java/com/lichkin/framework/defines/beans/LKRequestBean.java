package com.lichkin.framework.defines.beans;

import com.lichkin.framework.app.android.LKAndroidStatics;

import lombok.Getter;
import lombok.ToString;

/**
 * 接口请求基本对象类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString
public class LKRequestBean {

    /** 统一请求参数 */
    private Datas datas;

    /**
     * 构造方法
     */
    public LKRequestBean() {
        datas = new Datas(LKAndroidStatics.appKey(), LKAndroidStatics.versionX(), LKAndroidStatics.versionY(), LKAndroidStatics.versionZ(), LKAndroidStatics.token());
    }

}
