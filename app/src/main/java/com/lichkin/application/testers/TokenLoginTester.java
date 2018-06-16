package com.lichkin.application.testers;

import com.lichkin.application.beans.impl.in.TokenLoginIn;
import com.lichkin.application.beans.impl.out.TokenLoginOut;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class TokenLoginTester {

    public static void test(LKRetrofit<TokenLoginIn, TokenLoginOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        retrofit.addTestResponseBeans(666, "TokenLoginTester");
        TokenLoginOut out = new TokenLoginOut();
        out.setLevel(LKAndroidStatics.level());
        out.setPhoto(LKAndroidStatics.photo());
        out.setSecurityCenterUrl(LKAndroidStatics.securityCenterUrl());
        retrofit.addTestResponseBeans(out);
    }

}
