package com.lichkin.framework.app.android.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

    /**
     * 将Base64编码转换为图像
     * @param base64Code Base64编码
     * @return 图像
     */
    public static Bitmap toBitmap(String base64Code) {
        byte[] bytes = Base64.decode(base64Code, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 将Base64编码转换为图像
     * @param base64Code Base64编码
     * @return 图像
     */
    public static Drawable toDrawable(String base64Code) {
        return new BitmapDrawable(toBitmap(base64Code));
    }

}
