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

    /** 公司令牌 */
    private String compToken;

    /** 分类编码 */
    private String categoryCode = "";

    /**
     * 构造方法
     * @param pageNumber 页码
     * @param compToken 公司令牌
     */
    public GetCompNewsPageIn(int pageNumber, String compToken) {
        super(pageNumber, 10);
        this.compToken = compToken;
    }

}
