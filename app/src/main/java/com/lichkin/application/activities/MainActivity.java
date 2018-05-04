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

/**
 * 基础MainAcitivity功能实现类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class MainActivity extends LKAppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

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
            intent.putExtra("url", LKPropertiesLoader.baseUrl + "/test/app/index.html");
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

        //执行请求
        retrofit.callSync(in, new LKBaseInvokeCallback<GetLastAppVersionIn, GetLastAppVersionOut>() {

            @Override
            protected void success(Context context, GetLastAppVersionIn getLastAppVersionIn, final GetLastAppVersionOut responseDatas) {
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
            protected void busError(Context context, GetLastAppVersionIn getLastAppVersionIn, int errorCode, String errorMessage) {
                switch (errorCode) {
                    case 10000://没有可用版本，不处理。
                        break;
                    default:
                        // 版本获取失败重启
                        restart();
                        break;
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
