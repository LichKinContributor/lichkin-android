package com.lichkin.application.beans.out.nested;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 公司信息
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CompInfo {

    /** 公司名称 */
    private String compName;

    /** 公司图片(Base64) */
    private String compImg;

    /**
     * 构造方法
     * @param compName 公司名称
     * @param compImg 公司图片(Base64)
     */
    public CompInfo(String compName, String compImg) {
        this.compName = compName;
        this.compImg = compImg;
    }

    /**
     * 构造方法
     * @param arr 字符串数组
     */
    public CompInfo(String[] arr) {
        this.compName = arr[0];
        this.compImg = arr[1];
    }

}
