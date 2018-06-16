package com.lichkin.application.testers;

import com.lichkin.application.beans.impl.in.SupplementRegisterInfoIn;
import com.lichkin.application.beans.impl.out.SupplementRegisterInfoOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class SupplementRegisterInfoTester {

    public static void test(LKRetrofit<SupplementRegisterInfoIn, SupplementRegisterInfoOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        SupplementRegisterInfoOut out = new SupplementRegisterInfoOut();
        out.setLoginName("LunaDream");
        retrofit.addTestResponseBeans(out);
    }

}
