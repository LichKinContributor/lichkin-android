package com.lichkin.framework.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lichkin.framework.LKObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * JSON工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@SuppressWarnings("deprecation")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LKJsonUtils extends LKObjectMapper {


    /**
     * 对象转字符串
     * @param obj 待转换对象
     * @param nullable 是否允许空值
     * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
     * @return 字符串
     */
    public static String toJson(Object obj, boolean nullable, boolean isArray) {
        return writeValueAsString(new ObjectMapper(), obj, nullable, isArray);
    }


    /**
     * 对象转字符串
     * @param obj 待转换对象
     * @return 字符串
     */
    public static String toJson(Object obj) {
        return writeValueAsString(new ObjectMapper(), obj, false, false);
    }


    /**
     * 字符串转对象
     * @param <T> 对象泛型
     * @param json 字符串
     * @param clazz 对象类型
     * @return 对象
     */
    public static <T> T toObj(String json, Class<T> clazz) {
        return toObj(new ObjectMapper(), json, clazz);
    }


    /**
     * 字符串转HashMap对象
     * @param json 字符串
     * @return Map对象
     */
    public static HashMap<String, String> toMap(String json) {
        return toMap(new ObjectMapper(), json);
    }


    /**
     * 字符串转ArrayList对象
     * @param <T> 对象泛型
     * @param json 字符串
     * @param clazz List泛型对象类型
     * @return List对象
     */
    public static <T> ArrayList<T> toList(String json, Class<T> clazz) {
        return toList(new ObjectMapper(), json, clazz);
    }


    /**
     * 将字符串转换为JsonNode对象
     * @param json 字符串
     * @return JsonNode对象
     */
    public static JsonNode toJsonNode(String json) {
        return readTree(new ObjectMapper(), json);
    }


    /**
     * 深层读取JsonNode对象
     * @param json 字符串
     * @param keys 键数组
     * @return JsonNode对象
     */
    public static JsonNode deepGet(String json, String... keys) {
        return deepGet(new ObjectMapper(), json, keys);
    }


    /**
     * 对象转字符串
     * @param obj 待转换对象
     * @param nullable 是否允许空值
     * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
     * @param excludesPropertyArray 排除的字段名
     * @return 字符串
     */
    public static String toJsonWithExcludes(Object obj, boolean nullable, boolean isArray, String... excludesPropertyArray) {
        return writeValueAsStringWithExcludes(new ObjectMapper(), obj, nullable, isArray, excludesPropertyArray);
    }


    /**
     * 对象转字符串
     * @param obj 待转换对象
     * @param excludesPropertyArray 排除的字段名
     * @return 字符串
     */
    public static String toJsonWithExcludes(Object obj, String... excludesPropertyArray) {
        return writeValueAsStringWithExcludes(new ObjectMapper(), obj, false, false, excludesPropertyArray);
    }


    /**
     * 对象转字符串
     * @param obj 待转换对象
     * @param nullable 是否允许空值
     * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
     * @param includesPropertyArray 包含的字段名
     * @return 字符串
     */
    public static String toJsonWithIncludes(Object obj, boolean nullable, boolean isArray, String... includesPropertyArray) {
        return writeValueAsStringWithIncludes(new ObjectMapper(), obj, nullable, isArray, includesPropertyArray);
    }


    /**
     * 对象转字符串
     * @param obj 待转换对象
     * @param includesPropertyArray 包含的字段名
     * @return 字符串
     */
    public static String toJsonWithIncludes(Object obj, String... includesPropertyArray) {
        return writeValueAsStringWithIncludes(new ObjectMapper(), obj, false, false, includesPropertyArray);
    }

}
