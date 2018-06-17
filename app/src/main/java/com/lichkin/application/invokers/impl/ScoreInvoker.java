package com.lichkin.application.invokers.impl;

import com.lichkin.application.beans.in.impl.ScoreIn;
import com.lichkin.application.beans.out.impl.ScoreOut;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 评分
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface ScoreInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + "Score")
    Call<LKResponseBean<ScoreOut>> invoke(@Body ScoreIn in);

}
