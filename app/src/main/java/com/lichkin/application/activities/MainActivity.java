package com.lichkin.application.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.impl.in.GetLastAppVersionIn;
import com.lichkin.application.beans.impl.out.GetLastAppVersionOut;
import com.lichkin.application.invokers.impl.GetLastAppVersionInvoker;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.activities.LKAppCompatActivity;
import com.lichkin.framework.app.android.activities.LKWebViewActivity;
import com.lichkin.framework.app.android.callbacks.LKBtnCallback;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKViewHelper;
import com.lichkin.framework.app.android.widgets.LKDialog;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;

/**
 * 基础MainAcitivity功能实现类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class MainActivity extends LKAppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    /** 交互测试页面 */
    private static final String APP_TEST_BRIDGE_PAGE_URL = "/test/app/index" + LKFrameworkStatics.WEB_MAPPING_PAGES;

    /** 客户端版本信息页面 */
    private static final String APP_VERSION_PAGE_URL = "/app/version" + LKFrameworkStatics.WEB_MAPPING_PAGES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_main);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //初始化导航栏
        initBottomNavigationView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (LKPropertiesLoader.testWebView) {
            Intent intent = new Intent(MainActivity.this, LKWebViewActivity.class);
            intent.putExtra("url", LKPropertiesLoader.baseUrl + APP_TEST_BRIDGE_PAGE_URL);
            startActivity(intent);
            return;
        }

        if (dontNeedRequestPermission(Manifest.permission.INTERNET)) {
            //有权限请求接口
            getLastAppVersion();
        }
    }

    @Override
    protected void onRequestPermissionResultGranted(String permissionName) {
        super.onRequestPermissionResultGranted(permissionName);
        switch (permissionName) {
            case Manifest.permission.INTERNET:
                //授权通过后请求接口
                getLastAppVersion();
                break;
        }
    }

    /** 更新对话框开启过 */
    private boolean dlgUpdateOpened = false;
    /** 更新对话框关闭过 */
    private boolean dlgUpdateClosed = false;

    @Override
    protected void onRequestPermissionResultNotGranted(String permissionName) {
        super.onRequestPermissionResultNotGranted(permissionName);
        switch (permissionName) {
            case Manifest.permission.INTERNET:
                //无网络权限则重启
                restart();
                break;
        }
    }

    /**
     * 重启
     */
    private void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    /**
     * 获取最新客户端版本信息
     */
    private void getLastAppVersion() {
        //开启过或者关闭过都不再执行版本更新操作
        if (dlgUpdateOpened || dlgUpdateClosed) {
            return;
        }

        //强制使用主线程
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //请求参数
        GetLastAppVersionIn in = new GetLastAppVersionIn();

        //创建请求对象
        final LKRetrofit<GetLastAppVersionIn, GetLastAppVersionOut> retrofit = new LKRetrofit<>(this, GetLastAppVersionInvoker.class);

        //测试代码
        addGetLastAppVersionTests(retrofit);

        //执行请求
        retrofit.callSync(in, new LKBaseInvokeCallback<GetLastAppVersionIn, GetLastAppVersionOut>() {

            @Override
            protected void success(Context context, GetLastAppVersionIn getLastAppVersionIn, final GetLastAppVersionOut responseDatas) {
                if (responseDatas == null) {
                    return;
                }
                boolean forceUpdate = responseDatas.isForceUpdate();
                String tip = responseDatas.getTip();
                LKDialog dlg = new LKDialog(context, tip).setTitle(R.string.dlg_tip_title_update).setCancelable(false);
                dlg.setPositiveButton(R.string.btn_positive_name_update, new LKBtnCallback() {
                    @Override
                    public void call(Context context, DialogInterface dialog) {
                        dlgUpdateClosed = true;
                        Intent intent = new Intent(MainActivity.this, LKWebViewActivity.class);
                        intent.putExtra("url", responseDatas.getUrl());
                        startActivity(intent);
                    }
                });
                if (forceUpdate) {
                    LKLog.w(tip);
                    dlg.show();
                } else {
                    LKLog.i(tip);
                    dlg.setNegativeButton(new LKBtnCallback() {
                        @Override
                        public void call(Context context, DialogInterface dialog) {
                            dlgUpdateClosed = true;
                        }
                    }).show();
                }
                dlgUpdateOpened = true;
            }

            @Override
            protected void busError(Context context, GetLastAppVersionIn getLastAppVersionIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                if (errorCode < LKFrameworkStatics.MIN_BUS_ERROR_CODE) {
                    if (errorCode == 9999) {//应用已下架
                        String[] errorMessageArr = errorBean.getErrorMessageArr();
                        LKDialog dlg = new LKDialog(context, errorMessageArr[0]).setTitle(errorMessageArr[1]).setCancelable(false);
                        dlg.setPositiveButton(errorMessageArr[2], new LKBtnCallback() {
                            @Override
                            public void call(Context context, DialogInterface dialog) {
                                System.exit(0);
                            }
                        });
                        dlg.show();
                        return;
                    }
                    super.busError(context, getLastAppVersionIn, errorCode, errorType, errorBean);
                } else {
                    // 自定义业务错误处理
                    switch (errorCode) {
                        case 10000://没有可用版本，不处理。
                            break;
                        default:
                            // 其它错误，待约定与实现。
                            break;
                    }
                }
            }


            @Override
            public void connectError(Context context, String requestId, GetLastAppVersionIn getLastAppVersionIn, DialogInterface dialog) {
                if (dlgUpdateOpened || dlgUpdateClosed) {
                    return;
                }
                restart();
            }

            @Override
            public void timeoutError(Context context, String requestId, GetLastAppVersionIn getLastAppVersionIn, DialogInterface dialog) {
                if (dlgUpdateOpened || dlgUpdateClosed) {
                    return;
                }
                restart();
            }

        });
    }

    /**
     * 增加测试用例
     * @param retrofit 请求对象
     */
    protected void addGetLastAppVersionTests(LKRetrofit<GetLastAppVersionIn, GetLastAppVersionOut> retrofit) {
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
//        retrofit.addTestResponseBeans(777, "简单错误提示");
//        retrofit.addTestResponseBeans(888, String.format("多个错误提示%s第1个%s第2个%s等等等等%s", LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR));
//        retrofit.addTestResponseBeans(999, String.format("[msg]%s[多个带字段信息的错误提示]%s[field1]%s[字段1]%s[field2]%s[字段2]%s", LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR_FIELDS, LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR_FIELDS, LKFrameworkStatics.SPLITOR, LKFrameworkStatics.SPLITOR_FIELDS));
        //应用已下架
//        retrofit.addTestResponseBeans(9999, "应用已下架" + LKFrameworkStatics.SPLITOR + "重要提示" + LKFrameworkStatics.SPLITOR + "心灰意冷的离去");
        //无新版本
//        retrofit.addTestResponseBeans(10000, "无新版本");
        //其它错误
//        retrofit.addTestResponseBeans(99999, "其它错误");
        //有新版本，且强制升级。
//        addGetLastAppVersionTest1(retrofit);
        //有新版本，不强制升级。
//        addGetLastAppVersionTest2(retrofit);
    }

    private void addGetLastAppVersionTest1(LKRetrofit<GetLastAppVersionIn, GetLastAppVersionOut> retrofit) {
        GetLastAppVersionOut out = new GetLastAppVersionOut();
        out.setForceUpdate(true);
        out.setTip("有最新版本");
        out.setUrl(LKPropertiesLoader.baseUrl + APP_VERSION_PAGE_URL);
        out.setVersionX(LKAndroidStatics.versionX());
        out.setVersionY(LKAndroidStatics.versionY());
        out.setVersionZ((short) (LKAndroidStatics.versionZ() + 1));
        retrofit.addTestResponseBeans(out);
    }

    private void addGetLastAppVersionTest2(LKRetrofit<GetLastAppVersionIn, GetLastAppVersionOut> retrofit) {
        GetLastAppVersionOut out = new GetLastAppVersionOut();
        out.setForceUpdate(false);
        out.setTip("有最新版本");
        out.setUrl(LKPropertiesLoader.baseUrl + APP_VERSION_PAGE_URL);
        out.setVersionX(LKAndroidStatics.versionX());
        out.setVersionY(LKAndroidStatics.versionY());
        out.setVersionZ((short) (LKAndroidStatics.versionZ() + 1));
        retrofit.addTestResponseBeans(out);
    }


    /** 导航栏菜单ID */
    private static final int NAVIGATION_MENU_GROUP_ID = Menu.NONE;
    /** 主页菜单ID */
    private static final int NAVIGATION_MENU_ITEM_ID_HOME = 0;
    /** 菜单1菜单ID */
    private static final int NAVIGATION_MENU_ITEM_ID_1 = 1;
    /** 菜单2菜单ID */
    private static final int NAVIGATION_MENU_ITEM_ID_2 = 2;
    /** 菜单3菜单ID */
    private static final int NAVIGATION_MENU_ITEM_ID_3 = 3;
    /** 我的菜单ID */
    private static final int NAVIGATION_MENU_ITEM_ID_MY = 9;
    /** 导航栏对象 */
    private BottomNavigationView navigation;


    /**
     * 初始化导航栏
     */
    private void initBottomNavigationView() {
        navigation = findViewById(R.id.navigation);
        //为导航栏增加监听事件
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                LKLog.d("onNavigationItemSelected -> " + item.getTitle());
                switch (item.getItemId()) {
                    case NAVIGATION_MENU_ITEM_ID_HOME:
                        onMenuHomeSelected();
                        return true;
                    case NAVIGATION_MENU_ITEM_ID_MY:
                        onMenuMySelected();
                        return true;
                    case NAVIGATION_MENU_ITEM_ID_1:
                        onMenu1Selected();
                        return true;
                    case NAVIGATION_MENU_ITEM_ID_2:
                        onMenu2Selected();
                        return true;
                    case NAVIGATION_MENU_ITEM_ID_3:
                        onMenu3Selected();
                        return true;
                }
                return false;
            }

        });
    }

    /**
     * 主页菜单点击事件
     */
    protected void onMenuHomeSelected() {
    }

    /**
     * 我的菜单点击事件
     */
    protected void onMenuMySelected() {
    }

    /**
     * 菜单1菜单点击事件
     */
    protected void onMenu1Selected() {
    }

    /**
     * 菜单2菜单点击事件
     */
    protected void onMenu2Selected() {
    }

    /**
     * 菜单3菜单点击事件
     */
    protected void onMenu3Selected() {
    }

    /**
     * 显示菜单
     * @param menuId 菜单ID
     * @param titleId 标题ID
     * @param iconId 图标ID
     */
    private void showMenu(int menuId, int titleId, int iconId) {
        Menu menu = navigation.getMenu();
        MenuItem item = menu.findItem(menuId);
        if (item == null) {
            //增加菜单
            menu.add(NAVIGATION_MENU_GROUP_ID, menuId, menuId, LKAndroidUtils.getString(titleId)).setIcon(iconId);
            //禁用mShiftingMode
            LKViewHelper.disableShiftingMode(navigation);
        }
    }

    /**
     * 显示主页菜单
     */
    protected void showMenuHome() {
        showMenu(NAVIGATION_MENU_ITEM_ID_HOME, R.string.title_navigation_menu_home, R.drawable.ic_navigation_menu_home);
    }

    /**
     * 显示我的菜单
     */
    protected void showMenuMy() {
        showMenu(NAVIGATION_MENU_ITEM_ID_MY, R.string.title_navigation_menu_my, R.drawable.ic_navigation_menu_my);
    }

    /**
     * 显示菜单1菜单
     */
    protected void showMenu1() {
        showMenu(NAVIGATION_MENU_ITEM_ID_1, R.string.title_navigation_menu_1, R.drawable.ic_navigation_menu_1);
    }

    /**
     * 显示菜单2菜单
     */
    protected void showMenu2() {
        showMenu(NAVIGATION_MENU_ITEM_ID_2, R.string.title_navigation_menu_2, R.drawable.ic_navigation_menu_2);
    }

    /**
     * 显示菜单3菜单
     */
    protected void showMenu3() {
        showMenu(NAVIGATION_MENU_ITEM_ID_3, R.string.title_navigation_menu_3, R.drawable.ic_navigation_menu_3);
    }

    /**
     * 隐藏菜单
     * @param menuId 菜单ID
     */
    private void hideMenu(int menuId) {
        Menu menu = navigation.getMenu();
        MenuItem item = menu.findItem(menuId);
        if (item != null) {
            //移除菜单
            menu.removeItem(menuId);
            //禁用mShiftingMode
            LKViewHelper.disableShiftingMode(navigation);
        }
    }

    /**
     * 隐藏主页菜单
     */
    protected void hideMenuHome() {
        hideMenu(NAVIGATION_MENU_ITEM_ID_HOME);
    }

    /**
     * 隐藏我的菜单
     */
    protected void hideMenuMy() {
        hideMenu(NAVIGATION_MENU_ITEM_ID_MY);
    }

    /**
     * 隐藏菜单1菜单
     */
    protected void hideMenu1() {
        hideMenu(NAVIGATION_MENU_ITEM_ID_1);
    }

    /**
     * 隐藏菜单2菜单
     */
    protected void hideMenu2() {
        hideMenu(NAVIGATION_MENU_ITEM_ID_2);
    }

    /**
     * 隐藏菜单3菜单
     */
    protected void hideMenu3() {
        hideMenu(NAVIGATION_MENU_ITEM_ID_3);
    }

}
