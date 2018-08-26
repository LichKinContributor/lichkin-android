package com.lichkin.application.invokers.impl;

import com.lichkin.application.beans.in.impl.PunchTheClockIn;
import com.lichkin.application.beans.out.impl.PunchTheClockOut;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKResponseBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 打卡
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface PunchTheClockInvoker {

    @POST(LKFrameworkStatics.WEB_MAPPING_API_APP_USEREMPLOYEE + "/PunchTheClock")
    Call<LKResponseBean<PunchTheClockOut>> invoke(@Body PunchTheClockIn in);

}
