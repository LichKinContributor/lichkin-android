package com.lichkin.framework.defines.beans;

import lombok.Getter;
import lombok.ToString;

/**
 * 接口分页请求基本对象类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString(callSuper = true)
public class LKRequestPageBean extends LKRequestBean {

    /** 页码 */
    private int pageNumber;

    /** 每页数据量 */
    private int pageSize;

    /**
     * 构造方法
     * @param pageNumber 页码
     */
    public LKRequestPageBean(int pageNumber) {
        this.pageNumber = pageNumber;
        this.pageSize = 10;
    }

    /**
     * 构造方法
     * @param pageNumber 页码
     * @param pageSize 每页数据量
     */
    public LKRequestPageBean(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

}
