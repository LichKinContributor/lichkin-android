package com.lichkin.application.beans.out;

import com.lichkin.application.beans.out.nested.CompInfo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
public class LoginOut {

    /** 头像 */
    private String photo;

    /** 等级 */
    private int level;

    /** 安全中心地址 */
    private String securityCenterUrl;

    /** 公司列表 */
    private List<CompInfo> listComp;

}
