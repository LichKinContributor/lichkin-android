package com.lichkin.application.beans.out.impl;

import com.lichkin.application.beans.out.LoginOut;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 账号登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class AccountLoginOut extends LoginOut {

    /** 令牌 */
    private String token;

    /** 登录名 */
    private String loginName;

}
