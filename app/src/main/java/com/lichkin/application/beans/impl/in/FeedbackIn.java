package com.lichkin.application.beans.impl.in;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 反馈
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString(callSuper = true)
@RequiredArgsConstructor
public class FeedbackIn extends LKRequestBean {

    /** 反馈内容 */
    private final String content;

    /** 图片（Base64） */
    private final String img;

}
