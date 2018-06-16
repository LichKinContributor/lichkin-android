package com.lichkin.framework.app.android.utils;

import java.security.MessageDigest;

/**
 * MD5工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKMD5 {

    /**
     * 将字符串转换为md5编码
     * @param str 字符串
     * @return md5编码
     */
    public static String md5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes("UTF-8"));
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    result.append("0");
                }
                result.append(temp);
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
