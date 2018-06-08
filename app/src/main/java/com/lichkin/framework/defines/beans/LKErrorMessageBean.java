package com.lichkin.framework.defines.beans;

import lombok.Getter;
import lombok.Setter;

/**
 * 错误提示信息对象
 * 根据枚举类型取对应类型的错误提示信息
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class LKErrorMessageBean {

    /** 枚举类型为STRING时的错误信息 */
    private String errorMessage;
    /** 枚举类型为STRING_ARR时的错误信息 */
    private String[] errorMessageArr;
    /** 枚举类型为FIELD_ARR时的错误信息 */
    private LKFieldErrorMessageBean[] fieldErrorMessageArr;

    public enum TYPE {
        /** 字符串 */
        STRING,
        /** 字符串数组 */
        STRING_ARR,
        /** 带字段信息的数组 */
        FIELD_ARR
    }


}
