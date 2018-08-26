package com.lichkin.application.beans.in.impl;

import com.lichkin.framework.app.android.beans.LKAMapLocation;
import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 打卡
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString(callSuper = true)
@RequiredArgsConstructor
public class PunchTheClockIn extends LKRequestBean {

    /** 公司ID */
    private final String compId;

    /** 高德定位信息 */
    private final LKAMapLocation amap;

}
