package com.lichkin.framework.app.android.beans;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.lichkin.framework.app.android.activities.LKWebViewActivity;
import com.lichkin.framework.app.android.callbacks.LKCallback;
import com.lichkin.framework.utils.LKRandomUtils;

import java.util.HashMap;
import java.util.Map;

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

    /** 视图TAG */
    private String tag;

    /**
     * 设置视图TAG
     * @param tag 视图TAG
     * @return 本对象
     */
    public LKDynamicButton tag(String tag) {
        this.tag = tag;
        return this;
    }

    /**
     * 获取视图TAG
     * @return 视图TAG
     */
    public String tag() {
        if (tag != null) {
            return tag;
        }
        return LKRandomUtils.create(10);
    }

    /** 按钮图片ID */
    private final int btnImgResId;

    /** 按钮标题ID */
    private final int btnTitleResId;

    /** 按钮点击后打开的Activity类型 */
    private final Class<?> toActivityClass;

    /** 页面地址 */
    private String url;

    /** 页面参数 */
    private Map<String, Object> params = new HashMap<>();

    /** 弹窗页面对象 */
    private DialogFragment dialogFragment;

    /** 弹窗对象管理器 */
    private FragmentManager fragmentManager;

    /** 回调方法 */
    private LKCallback callback;

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
     * 添加参数
     * @param key 键
     * @param value 值
     * @return 本对象
     */
    public LKDynamicButton addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    /**
     * 构造方法（页面访问方式，后续传入URL值。）
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

    /**
     * 构造方法（回调方法式）
     * @param btnImgResId 按钮图片ID
     * @param btnTitleResId 按钮标题ID
     * @param callback 回调方法
     */
    public LKDynamicButton(int btnImgResId, int btnTitleResId, LKCallback callback) {
        this.btnImgResId = btnImgResId;
        this.btnTitleResId = btnTitleResId;
        this.toActivityClass = null;
        this.callback = callback;
    }

}
