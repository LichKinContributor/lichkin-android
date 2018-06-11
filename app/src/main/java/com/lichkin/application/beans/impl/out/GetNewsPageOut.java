package com.lichkin.application.beans.impl.out;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取新闻分页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
public class GetNewsPageOut {

    /** 地址 */
    private String url;

    /** 标题 */
    private String title;

    /** 简介 */
    private String brief;

    /** 图片地址 */
    private List<String> imageUrls;

}
