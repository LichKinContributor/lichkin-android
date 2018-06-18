package com.lichkin.application.testers;

import com.lichkin.application.beans.in.impl.AccountLoginIn;
import com.lichkin.application.beans.out.impl.AccountLoginOut;
import com.lichkin.framework.app.android.beans.LKDynamicTab;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.utils.LKRandomUtils;

import java.util.Arrays;

/**
 * 测试用例
 */
public class AccountLoginTester {

    public static String loginName = "LunaDream";

    public static void test(LKRetrofit<AccountLoginIn, AccountLoginOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "AccountLoginTester");
        AccountLoginOut out = new AccountLoginOut();
        if (loginName.equals("18501531550") || loginName.equals("LunaDream")) {
            out.setLoginName("LunaDream");
            out.setLevel(63);
            out.setPhoto("photo");
            out.setListTab(Arrays.asList(
                    new LKDynamicTab("LichKin", "鑫宏利业", TesterStatics.LichKin_LOGO_BASE64)
            ));
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
