package com.lichkin.defines;

import com.lichkin.framework.defines.LKFrameworkStatics;

/**
 * 常量定义
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface CoreStatics {

    /** 用户单点登录地址 */
    String USER_SSO_URL = "/user/SSO" + LKFrameworkStatics.WEB_MAPPING_PAGES + "?redirectUrl=";

    /** 员工单点登录地址 */
    String EMPLOYEE_SSO_URL = "/employee/SSO" + LKFrameworkStatics.WEB_MAPPING_PAGES + "?redirectUrl=";

}
