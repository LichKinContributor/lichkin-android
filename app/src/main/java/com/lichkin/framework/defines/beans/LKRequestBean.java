package com.lichkin.framework.defines.beans;

import com.lichkin.framework.app.android.LKAndroidStatics;

import java.util.Locale;

import lombok.Getter;
import lombok.ToString;

/**
 * 接口请求基本对象类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString
public class LKRequestBean {

    /** 客户端唯一标识 */
    private String appKey;
    /** 客户端类型 */
    private String clientType;
    /** 客户端版本号（大版本号） */
    private Byte versionX;
    /** 客户端版本号（中版本号） */
    private Byte versionY;
    /** 客户端版本号（小版本号） */
    private Short versionZ;
    /** 国际化 */
    private String locale;
    /** 登录后获取得 */
    private String token;

    /**
     * 构造方法
     */
    public LKRequestBean() {
        appKey = LKAndroidStatics.appKey();
        clientType = "ANDROID";
        versionX = LKAndroidStatics.versionX();
        versionY = LKAndroidStatics.versionY();
        versionZ = LKAndroidStatics.versionZ();
        locale = Locale.getDefault().toString();
        token = LKAndroidStatics.token();
    }

}
