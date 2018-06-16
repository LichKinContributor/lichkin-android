package com.lichkin.application.testers;

import com.lichkin.application.beans.impl.in.AccountLoginIn;
import com.lichkin.application.beans.impl.out.AccountLoginOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.utils.LKRandomUtils;

/**
 * 测试用例
 */
public class AccountLoginTester {

    public static String loginName = "LunaDream";

    public static void test(LKRetrofit<AccountLoginIn, AccountLoginOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        retrofit.addTestResponseBeans(666, "AccountLoginTester");
        AccountLoginOut out = new AccountLoginOut();
        if (loginName.equals("18501531550") || loginName.equals("LunaDream")) {
            out.setLoginName("LunaDream");
            out.setLevel(63);
            out.setPhoto("photo");
        } else {
            out.setLoginName(loginName);
            out.setLevel(1);
            out.setPhoto(null);
        }
        out.setToken(LKRandomUtils.create(32));
        out.setSecurityCenterUrl("file:///android_asset/test/test.html");
        retrofit.addTestResponseBeans(out);
    }

}
