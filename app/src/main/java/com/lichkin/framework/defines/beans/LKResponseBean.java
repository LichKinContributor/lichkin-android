package com.lichkin.framework.defines.beans;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 接口响应基本对象类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKResponseBean<D> {

    /** 错误编码 */
    private int errorCode;

    /** 响应提示信息 */
    private String errorMessage;

    /** 响应数据 */
    private D datas;


    /**
     * 构造方法
     * @param datas 响应数据
     */
    public LKResponseBean(D datas) {
        super();
        this.datas = datas;
    }


    /**
     * 构造方法
     * @param errorCode 错误编码
     * @param errorMessage 错误提示信息
     */
    public LKResponseBean(int errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
