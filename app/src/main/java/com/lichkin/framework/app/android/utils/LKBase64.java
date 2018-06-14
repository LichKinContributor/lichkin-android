package com.lichkin.framework.app.android.utils;

import android.net.Uri;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.Cleanup;

/**
 * Base64工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKBase64 {

    /**
     * 将文件转换为Base64编码
     * @param filePath 文件路径
     * @return Base64编码
     */
    public static String toBase64(String filePath) {
        String result = null;
        try {
            if (filePath != null) {
                File file = new File(Uri.parse(filePath).getPath());
                if (file.exists() && file.isFile()) {
                    @Cleanup
                    InputStream is = new FileInputStream(file);
                    byte[] data = new byte[is.available()];
                    is.read(data);
                    result = Base64.encodeToString(data, Base64.DEFAULT);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
