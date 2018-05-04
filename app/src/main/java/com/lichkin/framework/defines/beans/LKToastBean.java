package com.lichkin.framework.defines.beans;

import lombok.Getter;
import lombok.Setter;

/**
 * 提示窗对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class LKToastBean {

    /** 显示时长 */
    private int timeout;

    /** 日志内容 */
    private String msg;

    /** 是JSON内容 */
    private boolean jsonMsg;

}
