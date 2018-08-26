package com.lichkin.framework.defines;

/**
 * 框架基本类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKFrameworkStatics {

    /** 系统默认配置值：API数据请求映射。 */
    private static final String WEB_MAPPING_API = "API";

    /** 系统默认配置值：API数据请求映射（仅应用于客户端请求）。 */
    private static final String WEB_MAPPING_API_APP = WEB_MAPPING_API + "/App";

    /** 系统默认配置值：API数据请求映射（用户客户端）。 */
    public static final String WEB_MAPPING_API_APP_USER = WEB_MAPPING_API_APP + "/User";

    /** 系统默认配置值：API数据请求映射（用户员工客户端）。 */
    public static final String WEB_MAPPING_API_APP_USEREMPLOYEE = WEB_MAPPING_API_APP_USER + "Employee";

    /** 系统默认配置值：页面请求映射。 */
    public static final String WEB_MAPPING_PAGES = ".dhtml";

    /** 多字段分隔符 */
    public static final String SPLITOR_FIELDS = "@#@";

    /** 标准分隔符 */
    public static final String SPLITOR = "#@#";

}
