package com.lichkin.application.invokers.impl;

import com.lichkin.application.beans.in.impl.GetDynamicTabsIn;
import com.lichkin.framework.app.android.beans.LKDynamicTab;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 获取动态TAB页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface GetDynamicTabsInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API_APP_USER + "/GetDynamicTabs")
    Call<LKResponseBean<List<LKDynamicTab>>> invoke(@Body GetDynamicTabsIn in);

}
