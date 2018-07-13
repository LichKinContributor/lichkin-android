package com.lichkin.application.testers;

import com.lichkin.application.beans.in.impl.GetLastAppVersionIn;
import com.lichkin.application.beans.out.impl.GetLastAppVersionOut;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;

/**
 * 测试用例
 */
public class GetLastAppVersionTester {

    public static void test(LKRetrofit<GetLastAppVersionIn, GetLastAppVersionOut> retrofit) {
        if (!LKPropertiesLoader.testRetrofit) {
            return;
        }
        //模拟服务器异常
//        retrofit.addTest_INTERNAL_SERVER_ERROR();
        //模拟地址错误
//        retrofit.addTest_NOT_FOUND();
        //模拟配置错误
//        retrofit.addTest_CONFIG_ERROR();
        //模拟参数错误
//        retrofit.addTest_PARAM_ERROR();
        //模拟存表参数错误
//        retrofit.addTest_DB_VALIDATE_ERROR();

        //错误提示模拟
//        retrofit.addTestResponseBeans(666, "GetLastAppVersionTester");
//        retrofit.addTestResponseBeans(777, "简单错误提示");
//        retrofit.addTestResponseBeans(888, String.format("多个错误提示%s第1个%s第2个%s等等等等%s", LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR));
//        retrofit.addTestResponseBeans(999, String.format("[msg]%s[多个带字段信息的错误提示]%s[field1]%s[字段1]%s[field2]%s[字段2]%s", LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR_FIELDS, LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR_FIELDS, LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR_FIELDS));
        //应用已下架
//        retrofit.addTestResponseBeans(100000, "应用已下架" + LKFrameworkStatics.SPLITOR + "重要提示" + LKFrameworkStatics.SPLITOR + "心灰意冷的离去");
        //无新版本
        retrofit.addTestResponseBeans(100001, "无新版本");
        //其它错误
//        retrofit.addTestResponseBeans(999999, "其它错误");

        //正常返回结果模拟
//        GetLastAppVersionOut out = new GetLastAppVersionOut();

        //1===>>>有新版本，且强制升级。
//        out.setForceUpdate(true);
//        out.setTip("有最新版本");
//        out.setUrl("file:///android_asset/version/version.html");
//        out.setVersionX(LKAndroidStatics.versionX());
//        out.setVersionY(LKAndroidStatics.versionY());
//        out.setVersionZ((short) (LKAndroidStatics.versionZ() + 1));
        //2===>>>有新版本，不强制升级。
//        out.setForceUpdate(false);
//        out.setTip("有最新版本");
//        out.setUrl("file:///android_asset/version/version.html");
//        out.setVersionX(LKAndroidStatics.versionX());
//        out.setVersionY(LKAndroidStatics.versionY());
//        out.setVersionZ((short) (LKAndroidStatics.versionZ() + 1));

        //正常返回结果模拟
//        retrofit.addTestResponseBeans(out);
    }

}
