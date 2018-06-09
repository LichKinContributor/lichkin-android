package com.lichkin.application.invokers.impl;

import com.lichkin.application.beans.impl.in.GetBannerListIn;
import com.lichkin.application.beans.impl.out.GetBannerListOut;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 获取Banner列表
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface GetBannerListInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API + "GetBannerList")
    Call<LKResponseBean<List<GetBannerListOut>>> invoke(@Body GetBannerListIn in);

}
