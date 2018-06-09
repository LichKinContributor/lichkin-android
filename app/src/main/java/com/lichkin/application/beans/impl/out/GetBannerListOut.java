package com.lichkin.application.beans.impl.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取Banner列表
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
public class GetBannerListOut {

    /** 图片URL */
    private String imageUrl;

    /** 页面地址 */
    private String pageUrl;

    /** 标题 */
    private String title;

}
