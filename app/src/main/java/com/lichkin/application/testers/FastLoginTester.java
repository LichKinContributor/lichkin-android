package com.lichkin.application.testers;

import com.lichkin.application.beans.impl.in.FastLoginIn;
import com.lichkin.application.beans.impl.out.FastLoginOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.utils.LKRandomUtils;

/**
 * 测试用例
 */
public class FastLoginTester {

    public static void test(LKRetrofit<FastLoginIn, FastLoginOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }

//        FastLoginOut out_login = new FastLoginOut();
//        out_login.setLogin(true);
//        out_login.setLoginName("18621118733");
//        out_login.setToken(LKRandomUtils.create(32));
//        out_login.setLevel(5);
//        retrofit.addTestResponseBeans(out_login);

        FastLoginOut out_register = new FastLoginOut();
        out_register.setLogin(false);
        out_register.setLoginName("18621118733");
        out_register.setToken(LKRandomUtils.create(32));
        out_register.setLevel(7);
        retrofit.addTestResponseBeans(out_register);
    }

}
