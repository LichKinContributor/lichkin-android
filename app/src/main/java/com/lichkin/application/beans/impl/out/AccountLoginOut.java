package com.lichkin.application.beans.impl.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 账号登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
public class AccountLoginOut {

    /** 令牌 */
    private String token;

    /** 登录名 */
    private String loginName;

    /** 等级 */
    private int level;

    /** 头像 */
    private String photo;

    /** 安全中心地址 */
    private String securityCenterUrl;

}
