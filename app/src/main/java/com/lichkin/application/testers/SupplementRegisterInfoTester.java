package com.lichkin.application.testers;

import com.lichkin.application.beans.in.impl.SupplementRegisterInfoIn;
import com.lichkin.application.beans.out.impl.SupplementRegisterInfoOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.utils.LKRandomUtils;

/**
 * 测试用例
 */
public class SupplementRegisterInfoTester {

    public static String loginName = "LunaDream";

    public static void test(LKRetrofit<SupplementRegisterInfoIn, SupplementRegisterInfoOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "SupplementRegisterInfoTester");
        SupplementRegisterInfoOut out = new SupplementRegisterInfoOut();
        if (loginName.equals("LunaDream")) {
            out.setLevel(63);
            out.setPhoto("photo");
        } else {
            out.setLevel(1);
            out.setPhoto(null);
        }
        out.setLoginName(loginName);
        out.setToken(LKRandomUtils.create(32));
        out.setSecurityCenterUrl("file:///android_asset/test/test.html");
        retrofit.addTestResponseBeans(out);
    }

}
