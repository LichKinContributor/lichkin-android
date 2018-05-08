package com.lichkin.framework.utils;

import com.lichkin.framework.defines.enums.LKPairEnum;
import com.lichkin.framework.defines.enums.impl.LKRangeTypeEnum;

import java.util.Random;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 随机数工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LKRandomUtils {

    /**
     * 生成随机字符串（数字（常用）和字母（常用））
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String create(final int length) {
        return create(length, LKRangeTypeEnum.NUMBER_NORMAL_AND_LETTER_NORMAL);
    }


    /**
     * 生成随机数字符串
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String createNumber(final int length) {
        return create(length, LKRangeTypeEnum.NUMBER_WITHOUT_ZERO);
    }


    /**
     * 生成随机字符串
     * @param length 字符串长度
     * @param rangeTypeEnum 取值范围枚举
     * @return 随机字符串
     */
    public static String create(final int length, final LKPairEnum rangeTypeEnum) {
        final String rangeStr = rangeTypeEnum.getName();

        final char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = rangeStr.charAt(randomInRange(0, rangeStr.length() - 1));
        }
        return new String(result);
    }


    /**
     * 生成随机值
     * @param min 最小值
     * @param max 最大值
     * @return 随机值
     */
    public static int randomInRange(int min, int max) {
        return new Random().nextInt((max + 1) - min) + min;
    }

}
