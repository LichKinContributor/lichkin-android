package com.lichkin.application.invokers.impl;

import com.lichkin.application.beans.in.impl.PhotoUploadIn;
import com.lichkin.application.beans.out.impl.PhotoUploadOut;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 头像上传
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface PhotoUploadInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + "PhotoUpload")
    Call<LKResponseBean<PhotoUploadOut>> invoke(@Body PhotoUploadIn in);

}
