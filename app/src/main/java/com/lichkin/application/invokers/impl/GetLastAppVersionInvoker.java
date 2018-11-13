package com.lichkin.application.invokers.impl;

import com.lichkin.application.ApplicationStatics;
import com.lichkin.application.beans.in.impl.GetLastAppVersionIn;
import com.lichkin.application.beans.out.impl.GetLastAppVersionOut;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 获取最新客户端版本信息
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface GetLastAppVersionInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + ApplicationStatics.API_TYPE + "/GetLastAppVersion")
    Call<LKResponseBean<GetLastAppVersionOut>> invoke(@Body GetLastAppVersionIn in);

}
