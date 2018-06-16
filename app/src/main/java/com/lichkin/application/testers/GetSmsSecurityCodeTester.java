package com.lichkin.application.testers;

import com.lichkin.application.beans.impl.in.GetSmsSecurityCodeIn;
import com.lichkin.application.beans.impl.out.GetSmsSecurityCodeOut;
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
        GetSmsSecurityCodeOut out = new GetSmsSecurityCodeOut();
        retrofit.addTestResponseBeans(out);
    }

}
