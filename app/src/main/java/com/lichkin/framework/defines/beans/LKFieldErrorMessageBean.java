package com.lichkin.framework.defines.beans;

import com.lichkin.framework.defines.LKFrameworkStatics;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 带字段信息的错误信息对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKFieldErrorMessageBean {

    /** 字段名 */
    private String fieldName;
    /** 字段对应的错误信息 */
    private String errorMessage;

    /**
     * 构造方法
     * @param str 错误信息原文
     */
    public LKFieldErrorMessageBean(String str) {
        String[] msg = str.split(LKFrameworkStatics.SPLITOR);
        fieldName = msg[0];
        errorMessage = msg[1];
    }

}
