package com.lichkin.application.beans.out.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 快速登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class FastLoginOut extends AccountLoginOut {

    /** ture:登录;false:注册. */
    private boolean login;

}
