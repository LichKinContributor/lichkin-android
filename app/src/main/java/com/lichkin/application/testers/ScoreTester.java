package com.lichkin.application.testers;

import com.lichkin.application.beans.impl.in.ScoreIn;
import com.lichkin.application.beans.impl.out.ScoreOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class ScoreTester {

    public static void test(LKRetrofit<ScoreIn, ScoreOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        retrofit.addTestResponseBeans(666, "ScoreTester");
        ScoreOut out = new ScoreOut();
        retrofit.addTestResponseBeans(out);
    }

}
