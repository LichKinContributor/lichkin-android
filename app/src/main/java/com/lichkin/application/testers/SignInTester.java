package com.lichkin.application.testers;

import com.lichkin.application.beans.impl.in.SignInIn;
import com.lichkin.application.beans.impl.out.SignInOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class SignInTester {

    public static void test(LKRetrofit<SignInIn, SignInOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        SignInOut out = new SignInOut();
        retrofit.addTestResponseBeans(out);
    }

}
