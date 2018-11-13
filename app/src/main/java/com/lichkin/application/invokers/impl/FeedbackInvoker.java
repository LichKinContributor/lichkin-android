package com.lichkin.application.invokers.impl;

import com.lichkin.application.ApplicationStatics;
import com.lichkin.application.beans.in.impl.FeedbackIn;
import com.lichkin.application.beans.out.impl.FeedbackOut;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 反馈
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface FeedbackInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + ApplicationStatics.API_TYPE + "/Feedback")
    Call<LKResponseBean<FeedbackOut>> invoke(@Body FeedbackIn in);

}
