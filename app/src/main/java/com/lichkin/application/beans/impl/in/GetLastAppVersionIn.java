package com.lichkin.application.beans.impl.in;

import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.ToString;

/**
 * 获取最新客户端版本信息
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString(callSuper = true)
public class GetLastAppVersionIn extends LKRequestBean {

    /** 客户端系统版本 */
    private String osVersion;

    /** 生产厂商 */
    private String brand;

    /** 机型信息 */
    private String model;

    /** 设备唯一标识 */
    private String uuid;

    /** 屏幕宽 */
    private Short screenWidth;

    /** 屏幕高 */
    private Short screenHeight;

    /**
     * 构造方法
     */
    public GetLastAppVersionIn() {
        super();
        osVersion = LKAndroidStatics.osVersion();
        brand = LKAndroidStatics.brand();
        model = LKAndroidStatics.model();
        uuid = LKAndroidStatics.uuid();
        screenWidth = LKAndroidStatics.screenWidth();
        screenHeight = LKAndroidStatics.screenHeight();
    }

}
