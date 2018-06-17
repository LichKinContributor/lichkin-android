package com.lichkin.application.beans.in.impl;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 头像上传
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString(callSuper = true)
@RequiredArgsConstructor
public class PhotoUploadIn extends LKRequestBean {

    /** 头像（Base64） */
    private final String photo;

}
