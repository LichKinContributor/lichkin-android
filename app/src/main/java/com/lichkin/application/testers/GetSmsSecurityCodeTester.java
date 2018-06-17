package com.lichkin.application.testers;

import com.lichkin.application.beans.in.impl.GetSmsSecurityCodeIn;
import com.lichkin.application.beans.out.impl.GetSmsSecurityCodeOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class GetSmsSecurityCodeTester {

    public static void test(LKRetrofit<GetSmsSecurityCodeIn, GetSmsSecurityCodeOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        retrofit.addTestResponseBeans(666, "GetSmsSecurityCodeTester");
        GetSmsSecurityCodeOut out = new GetSmsSecurityCodeOut();
        retrofit.addTestResponseBeans(out);
    }

}
