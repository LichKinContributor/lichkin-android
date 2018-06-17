package com.lichkin.application.beans.in.impl;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 补充注册信息
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString(callSuper = true)
@RequiredArgsConstructor
public class SupplementRegisterInfoIn extends LKRequestBean {

    /** 用户名 */
    private final String loginName;

    /** 密码 */
    private final String pwd;

}
