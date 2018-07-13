package com.lichkin.application.testers;

import com.lichkin.application.beans.in.impl.FastLoginIn;
import com.lichkin.application.beans.out.impl.FastLoginOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.utils.LKRandomUtils;

/**
 * 测试用例
 */
public class FastLoginTester {

    public static String cellphone = "18501531550";

    public static void test(LKRetrofit<FastLoginIn, FastLoginOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
//        retrofit.addTestResponseBeans(666, "FastLoginTester");
        FastLoginOut out = new FastLoginOut();
        if (cellphone.equals("18501531550")) {
            out.setLogin(true);
            out.setLoginName("LunaDream");
            out.setLevel(63);
            out.setPhoto("photo");
        } else {
            out.setLogin(false);
            out.setLoginName(cellphone);
            out.setLevel(1);
            out.setPhoto(null);
        }
        out.setToken(LKRandomUtils.create(32));
        out.setSecurityCenterUrl("file:///android_asset/test/test.html");
        retrofit.addTestResponseBeans(out);
    }

}
