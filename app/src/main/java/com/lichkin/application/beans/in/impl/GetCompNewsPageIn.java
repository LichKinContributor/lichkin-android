package com.lichkin.application.beans.in.impl;

import lombok.Getter;
import lombok.ToString;

/**
 * 获取公司新闻分页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString(callSuper = true)
public class GetCompNewsPageIn extends GetNewsPageIn {

    /** 公司ID */
    private String compId;

    /** 分类编码 */
    private String categoryCode = "";

    /**
     * 构造方法
     * @param pageNumber 页码
     * @param compId 公司ID
     */
    public GetCompNewsPageIn(int pageNumber, String compId) {
        super(pageNumber, 10);
        this.compId = compId;
    }

}
