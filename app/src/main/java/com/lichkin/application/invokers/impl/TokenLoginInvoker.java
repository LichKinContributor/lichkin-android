package com.lichkin.application.invokers.impl;

import com.lichkin.application.beans.impl.in.TokenLoginIn;
import com.lichkin.application.beans.impl.out.TokenLoginOut;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 令牌登录
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface TokenLoginInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + "AccountLogin")
    Call<LKResponseBean<TokenLoginOut>> invoke(@Body TokenLoginIn in);

}
