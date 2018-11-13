package com.lichkin.application.invokers.impl;

import com.lichkin.application.ApplicationStatics;
import com.lichkin.application.beans.in.impl.AccountLoginIn;
import com.lichkin.application.beans.out.impl.AccountLoginOut;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 账号登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface AccountLoginInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + ApplicationStatics.API_TYPE + "/AccountLogin")
    Call<LKResponseBean<AccountLoginOut>> invoke(@Body AccountLoginIn in);

}
