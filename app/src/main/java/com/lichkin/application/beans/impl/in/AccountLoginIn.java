package com.lichkin.application.beans.impl.in;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 账号登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString(callSuper = true)
@RequiredArgsConstructor
public class AccountLoginIn extends LKRequestBean {

    /** 用户名 */
    private final String loginName;

    /** 密码 */
    private final String pwd;

}
