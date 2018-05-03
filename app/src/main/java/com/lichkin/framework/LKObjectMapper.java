package com.lichkin.framework;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.lichkin.framework.app.android.utils.LKLog;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 对象映射工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 * @deprecated 仅供框架使用。
 */
@Deprecated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LKObjectMapper {


    /** 过滤器ID */
    private static final String JSON_FILTER_ID = "JsonFilter";

    /**
     * 默认值
     * @param mapper 对象映射对象
     * @param nullable 是否允许空值
     * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
     * @return 默认值
     */
    private static String defaultValue(ObjectMapper mapper, boolean nullable, boolean isArray) {
        if (nullable) {
            return null;
        }
        if (isArray) {
            return "[]";
        } else {
            return "{}";
        }
    }

    /**
     * 对象转字符串
     * @param mapper 对象映射对象
     * @param obj 待转换对象
     * @param nullable 是否允许空值
     * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
     * @return 字符串
     */
    protected static String writeValueAsString(ObjectMapper mapper, Object obj, boolean nullable, boolean isArray) {
        if (obj != null) {
            try {
                return mapper.writeValueAsString(obj);
            } catch (final Exception e) {
                // ignore this
                LKLog.e("writeValueAsString", e);
            }
        }
        return defaultValue(mapper, nullable, isArray);
    }

    /**
     * 字符串转对象
     * @param mapper 对象映射对象
     * @param <T> 对象泛型
     * @param str 字符串
     * @param clazz 对象类型
     * @return 对象
     */
    protected static <T> T toObj(ObjectMapper mapper, String str, Class<T> clazz) {
        if (str != null && !"".equals(str)) {
            try {
                return mapper.readValue(str, clazz);
            } catch (final Exception e) {
                // ignore this
                LKLog.e("toObj", e);
            }
        }
        return null;
    }

    /**
     * 字符串转HashMap对象
     * @param mapper 对象映射对象
     * @param str 字符串
     * @return Map对象
     */
    protected static HashMap<String, String> toMap(ObjectMapper mapper, String str) {
        if (str != null && !"".equals(str)) {
            try {
                return mapper.readValue(str, mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, String.class));
            } catch (final Exception e) {
                // ignore this
                LKLog.e("toMap", e);
            }
        }
        return null;
    }

    /**
     * 字符串转ArrayList对象
     * @param mapper 对象映射对象
     * @param <T> 对象泛型
     * @param str 字符串
     * @param clazz List泛型对象类型
     * @return List对象
     */
    protected static <T> ArrayList<T> toList(ObjectMapper mapper, String str, Class<T> clazz) {
        if (str != null && !"".equals(str)) {
            try {
                return mapper.readValue(str, mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz));
            } catch (final Exception e) {
                // ignore this
                LKLog.e("toList", e);
            }
        }
        return null;
    }

    /**
     * 将字符串转换为JsonNode对象
     * @param mapper 对象映射对象
     * @param str 字符串
     * @return JsonNode对象
     */
    protected static JsonNode readTree(ObjectMapper mapper, String str) {
        try {
            return mapper.readTree(str);
        } catch (final Exception e) {
            // ignore this
            LKLog.e("readTree", e);
        }
        return null;
    }

    /**
     * 深层读取JsonNode对象
     * @param mapper 对象映射对象
     * @param str 字符串
     * @param keys 键数组
     * @return JsonNode对象
     */
    protected static JsonNode deepGet(ObjectMapper mapper, String str, String... keys) {
        try {
            JsonNode jsonNode = mapper.readTree(str);
            LKLog.d("deepGet -> " + jsonNode.asText());
            if (keys != null && keys.length > 0) {
                for (final String key : keys) {
                    jsonNode = jsonNode.get(key);
                    LKLog.d("deepGet -> " + jsonNode.asText());
                }
            }
            return jsonNode;
        } catch (final Exception e) {
            // ignore this
            LKLog.e("deepGet", e);
        }
        return null;
    }

    /**
     * 对象转字符串
     * @param mapper 对象映射对象
     * @param obj 待转换对象
     * @param nullable 是否允许空值
     * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
     * @param excludesPropertyArray 排除的字段名
     * @return 字符串
     */
    protected static String writeValueAsStringWithExcludes(ObjectMapper mapper, Object obj, boolean nullable, boolean isArray, String... excludesPropertyArray) {
        if (excludesPropertyArray != null && excludesPropertyArray.length > 0) {
            mapper.setFilterProvider(new SimpleFilterProvider().addFilter(JSON_FILTER_ID, SimpleBeanPropertyFilter.serializeAllExcept(excludesPropertyArray)));
            mapper.addMixIn(obj.getClass(), LKJsonFilter.class);
        }
        return writeValueAsString(mapper, obj, nullable, isArray);
    }

    /**
     * 对象转字符串
     * @param mapper 对象映射对象
     * @param obj 待转换对象
     * @param nullable 是否允许空值
     * @param isArray 是否为数组，当obj为null且nullable指定了不能为空时决定返回形式的区别。
     * @param includesPropertyArray 包含的字段名
     * @return 字符串
     */
    protected static String writeValueAsStringWithIncludes(ObjectMapper mapper, Object obj, boolean nullable, boolean isArray, String... includesPropertyArray) {
        if (includesPropertyArray != null && includesPropertyArray.length > 0) {
            mapper.setFilterProvider(new SimpleFilterProvider().addFilter(JSON_FILTER_ID, SimpleBeanPropertyFilter.filterOutAllExcept(includesPropertyArray)));
            mapper.addMixIn(obj.getClass(), LKJsonFilter.class);
        }
        return writeValueAsString(mapper, obj, nullable, isArray);
    }


    /**
     * 默认过滤器，什么都不处理。
     * @author SuZhou LichKin Information Technology Co., Ltd.
     */
    @JsonFilter(JSON_FILTER_ID)
    private interface LKJsonFilter {
    }

}
