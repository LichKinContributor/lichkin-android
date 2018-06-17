package com.lichkin.application.beans.out.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取最新客户端版本信息
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
public class GetLastAppVersionOut {

    /** 强制更新 */
    private boolean forceUpdate;

    /** 客户端版本号（大版本号） */
    private byte versionX;

    /** 客户端版本号（中版本号） */
    private byte versionY;

    /** 客户端版本号（小版本号） */
    private short versionZ;

    /** 版本提示信息 */
    private String tip;

    /** 版本信息页面地址 */
    private String url;

}
