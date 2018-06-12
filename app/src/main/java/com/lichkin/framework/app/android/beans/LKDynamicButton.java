package com.lichkin.framework.app.android.beans;

import com.lichkin.framework.app.android.activities.LKWebViewActivity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 按钮对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LKDynamicButton {

    /** 按钮图片ID */
    private final int btnImgResId;

    /** 按钮标题ID */
    private final int btnTitleResId;

    /** 按钮点击后打开的Activity类型 */
    private final Class<?> toActivityClass;

    /** 页面地址 */
    private String url;

    /**
     * 构造方法（页面访问方式）
     * @param btnImgResId 按钮图片ID
     * @param btnTitleResId 按钮标题ID
     * @param url 页面地址
     */
    public LKDynamicButton(int btnImgResId, int btnTitleResId, String url) {
        this.btnImgResId = btnImgResId;
        this.btnTitleResId = btnTitleResId;
        this.toActivityClass = LKWebViewActivity.class;
        this.url = url;
    }

    /**
     * 构造方法（页面访问方式）
     * @param btnImgResId 按钮图片ID
     * @param btnTitleResId 按钮标题ID
     */
    public LKDynamicButton(int btnImgResId, int btnTitleResId) {
        this.btnImgResId = btnImgResId;
        this.btnTitleResId = btnTitleResId;
        this.toActivityClass = LKWebViewActivity.class;
    }

}
