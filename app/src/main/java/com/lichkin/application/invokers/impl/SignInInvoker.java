package com.lichkin.application.invokers.impl;

import com.lichkin.application.beans.impl.in.SignInIn;
import com.lichkin.application.beans.impl.out.SignInOut;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 签到
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface SignInInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + "SignIn")
    Call<LKResponseBean<SignInOut>> invoke(@Body SignInIn in);

}
