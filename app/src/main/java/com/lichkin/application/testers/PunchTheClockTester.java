package com.lichkin.application.testers;

import com.lichkin.application.beans.in.impl.PunchTheClockIn;
import com.lichkin.application.beans.out.impl.PunchTheClockOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class PunchTheClockTester {

    public static void test(LKRetrofit<PunchTheClockIn, PunchTheClockOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "PunchTheClockTester");
        PunchTheClockOut out = new PunchTheClockOut();
        retrofit.addTestResponseBeans(out);
    }

}
