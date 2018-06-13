package com.lichkin.framework.app.android.beans;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

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

    /** 弹窗页面对象 */
    private DialogFragment dialogFragment;

    /** 弹窗对象管理器 */
    private FragmentManager fragmentManager;

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

    /**
     * 构造方法（弹窗访问方式）
     * @param btnImgResId 按钮图片ID
     * @param btnTitleResId 按钮标题ID
     * @param dialogFragment 弹窗页面对象
     * @param fragmentManager 弹窗对象管理器
     */
    public LKDynamicButton(int btnImgResId, int btnTitleResId, DialogFragment dialogFragment, FragmentManager fragmentManager) {
        this.btnImgResId = btnImgResId;
        this.btnTitleResId = btnTitleResId;
        this.toActivityClass = null;
        this.dialogFragment = dialogFragment;
        this.fragmentManager = fragmentManager;
    }


}
