package com.lichkin.application.beans.in.impl;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 快速登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString(callSuper = true)
@RequiredArgsConstructor
public class FastLoginIn extends LKRequestBean {

    /** 手机号码 */
    private final String cellphone;

    /** 验证码 */
    private final String securityCode;

}
