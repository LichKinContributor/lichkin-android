package com.lichkin.framework.app.android.utils;

import android.widget.ImageView;

import com.lichkin.app.android.demo.R;
import com.squareup.picasso.Picasso;

/**
 * 图片工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKImageLoader {

    /**
     * 加载图像
     * @param url 图片地址
     * @param imageView 图片视图对象
     */
    public static void load(String url, ImageView imageView) {
        load(url, imageView, R.drawable.no_image);
    }

    /**
     * 加载图像
     * @param url 图片地址
     * @param imageView 图片视图对象
     * @param drawableResId 无图片资源ID
     */
    public static void load(String url, ImageView imageView, int drawableResId) {
        Picasso.get().load(url).placeholder(drawableResId).into(imageView);
    }

}
