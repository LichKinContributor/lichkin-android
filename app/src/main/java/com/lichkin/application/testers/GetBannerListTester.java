package com.lichkin.application.testers;

import com.lichkin.application.beans.in.impl.GetBannerListIn;
import com.lichkin.application.beans.out.impl.GetBannerListOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用例
 */
public class GetBannerListTester {

    public static void test(LKRetrofit<GetBannerListIn, List<GetBannerListOut>> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        retrofit.addTestResponseBeans(666, "GetBannerListTester");
        final List<GetBannerListOut> bannerList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            GetBannerListOut banner = new GetBannerListOut();
            banner.setTitle("Banner" + i);
            banner.setPageUrl("file:///android_asset/banners/banner" + i + ".html");
            banner.setImageUrl("file:///android_asset/banners/banner" + i + ".jpeg");
            bannerList.add(banner);
        }
        retrofit.addTestResponseBeans(bannerList);
    }

}
