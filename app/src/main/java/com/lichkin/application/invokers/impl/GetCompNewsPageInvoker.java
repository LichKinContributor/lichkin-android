package com.lichkin.application.invokers.impl;

import com.lichkin.application.beans.in.impl.GetCompNewsPageIn;
import com.lichkin.application.beans.out.impl.GetCompNewsPageOut;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKPageBean;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 获取公司新闻分页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface GetCompNewsPageInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API_APP_USEREMPLOYEE + "/GetNewsPage")
    Call<LKResponseBean<LKPageBean<GetCompNewsPageOut>>> invoke(@Body GetCompNewsPageIn in);

}
