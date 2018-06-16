package com.lichkin.application.beans.impl.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 快速登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
public class FastLoginOut {

    /** ture:登录;false:注册. */
    private boolean login;

    /** 令牌 */
    private String token;

    /** 登录名 */
    private String loginName;

    /** 等级 */
    private int level;

}
