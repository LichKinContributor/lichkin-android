package com.lichkin.application.beans.impl.in;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 获取短信验证码
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString(callSuper = true)
@RequiredArgsConstructor
public class GetSmsSecurityCodeIn extends LKRequestBean {

    /** 手机号码 */
    private final String cellphone;

}
