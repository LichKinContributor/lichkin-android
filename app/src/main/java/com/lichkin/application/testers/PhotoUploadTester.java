package com.lichkin.application.testers;

import com.lichkin.application.beans.impl.in.PhotoUploadIn;
import com.lichkin.application.beans.impl.out.PhotoUploadOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class PhotoUploadTester {

    public static void test(LKRetrofit<PhotoUploadIn, PhotoUploadOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        retrofit.addTestResponseBeans(666, "PhotoUploadTester");
        PhotoUploadOut out = new PhotoUploadOut();
        retrofit.addTestResponseBeans(out);
    }

}
