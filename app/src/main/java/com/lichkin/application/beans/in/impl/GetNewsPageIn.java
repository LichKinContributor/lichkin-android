package com.lichkin.application.beans.in.impl;

import com.lichkin.framework.defines.beans.LKRequestPageBean;

import lombok.Getter;
import lombok.ToString;

/**
 * 获取新闻分页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString(callSuper = true)
public class GetNewsPageIn extends LKRequestPageBean {

    /**
     * 构造方法
     * @param pageNumber 页码
     */
    public GetNewsPageIn(int pageNumber) {
        super(pageNumber, 5);
    }

}
