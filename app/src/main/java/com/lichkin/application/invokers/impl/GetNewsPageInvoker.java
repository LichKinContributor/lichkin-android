package com.lichkin.application.invokers.impl;

import com.lichkin.application.beans.impl.in.GetNewsPageIn;
import com.lichkin.application.beans.impl.out.GetNewsPageOut;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKPageBean;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 获取新闻分页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface GetNewsPageInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + "GetNewsPage")
    Call<LKResponseBean<LKPageBean<GetNewsPageOut>>> invoke(@Body GetNewsPageIn in);

}
