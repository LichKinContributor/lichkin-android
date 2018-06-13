package com.lichkin.application.testers;

import com.lichkin.application.beans.impl.in.FeedbackIn;
import com.lichkin.application.beans.impl.out.FeedbackOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class FeedbackTester {

    public static void test(LKRetrofit<FeedbackIn, FeedbackOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        FeedbackOut out = new FeedbackOut();
        retrofit.addTestResponseBeans(out);
    }

}
