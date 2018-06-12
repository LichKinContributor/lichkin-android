package com.lichkin.framework.app.android.beans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 屏幕对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LKScreen {

    /** 屏幕宽 */
    private final int width;

    /** 屏幕高 */
    private final int height;

}
