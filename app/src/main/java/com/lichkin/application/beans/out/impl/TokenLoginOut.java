package com.lichkin.application.beans.out.impl;

import com.lichkin.application.beans.out.LoginOut;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 令牌登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class TokenLoginOut extends LoginOut {

    /** 登录名 */
    private String loginName;

}
