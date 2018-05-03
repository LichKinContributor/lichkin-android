package com.lichkin.framework.defines.beans;

import lombok.Getter;
import lombok.Setter;

/**
 * 日志对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class LKLogBean {

    /** 日志类型 */
    private String type;

    /** 日志内容 */
    private String msg;

    /** 是JSON内容 */
    private boolean jsonMsg;

}
