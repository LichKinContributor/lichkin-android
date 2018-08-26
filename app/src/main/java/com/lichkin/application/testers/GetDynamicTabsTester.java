package com.lichkin.application.testers;

import com.lichkin.application.beans.in.impl.GetDynamicTabsIn;
import com.lichkin.framework.app.android.beans.LKDynamicTab;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用例
 */
public class GetDynamicTabsTester {

    public static void test(LKRetrofit<GetDynamicTabsIn, List<LKDynamicTab>> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        List<LKDynamicTab> out = new ArrayList<>();
        out.add(new LKDynamicTab("LichKin", "鑫宏利业", TesterStatics.LichKin_LOGO_BASE64));
        retrofit.addTestResponseBeans(out);
    }

}
