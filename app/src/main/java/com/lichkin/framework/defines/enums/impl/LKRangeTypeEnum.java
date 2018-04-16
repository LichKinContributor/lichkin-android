package com.lichkin.framework.defines.enums.impl;

import com.lichkin.framework.defines.enums.LKPairEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 取值范围枚举类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@RequiredArgsConstructor
public enum LKRangeTypeEnum implements LKPairEnum {

    /** 数字（无零） */
    NUMBER_WITHOUT_ZERO("123456789"),

    /** 数字 */
    NUMBER_FULL("0123456789"),

    /** 字母（常用） */
    LETTER_NORMAL("qwertasdfgzxcvb"),

    /** 字母（小写） */
    LETTER_FULL_LOWERCASE("abcdefghijklmnopqrstuvwxyz"),

    /** 字母（大写） */
    LETTER_FULL_UPPERCASE("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),

    /** 字母（大小写） */
    LETTER_FULL("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),

    /** 数字（常用）和字母（常用） */
    NUMBER_NORMAL_AND_LETTER_NORMAL("qwertasdfgzxcvb12345"),

    /** 数字和字母（小写） */
    NUMBER_AND_LETTER_FULL_LOWERCASE("0123456789abcdefghijklmnopqrstuvwxyz"),

    /** 数字和字母（大写） */
    NUMBER_AND_LETTER_FULL_UPPERCASE("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"),

    /** 数字和字母（大小写） */
    NUMBER_AND_LETTER_FULL("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

    /** 名称 */
    private final String name;

}
