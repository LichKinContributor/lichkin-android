package com.lichkin.application.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.lichkin.application.beans.impl.in.GetLastAppVersionIn;
import com.lichkin.application.beans.impl.out.GetLastAppVersionOut;
import com.lichkin.application.invokers.impl.GetLastAppVersionInvoker;
import com.lichkin.demo.R;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKViewHelper;

/**
 * 基础MainAcitivity功能实现类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class MainActivity extends LKAppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    protected static final int NAVIGATION_MENU_GROUP_ID = Menu.NONE;
    protected static final int NAVIGATION_MENU_ITEM_ID_HOME = 0;
    protected static final int NAVIGATION_MENU_ITEM_ID_1 = 1;
    protected static final int NAVIGATION_MENU_ITEM_ID_2 = 2;
    protected static final int NAVIGATION_MENU_ITEM_ID_3 = 3;
    protected static final int NAVIGATION_MENU_ITEM_ID_MY = 9;
    private BottomNavigationView navigation;

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

    @Override
    protected void onRequestPermissionResultNotGranted(String permissionName) {
        super.onRequestPermissionResultNotGranted(permissionName);
        switch (permissionName) {
            case Manifest.permission.INTERNET:
                //无网络权限则重启
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
        }
    }


    protected abstract void onMenuHomeSelected();

    protected abstract void onMenuMySelected();

    protected abstract void onMenu1Selected();

    protected abstract void onMenu2Selected();

    protected abstract void onMenu3Selected();

    /**
     * 初始化导航栏
     */
    private void initBottomNavigationView() {
        navigation = findViewById(R.id.navigation);
        //动态创建导航菜单
        Menu menu = navigation.getMenu();
        //增加固定的主页菜单和我的菜单
        menu.add(NAVIGATION_MENU_GROUP_ID, NAVIGATION_MENU_ITEM_ID_HOME, NAVIGATION_MENU_ITEM_ID_HOME, LKAndroidUtils.getString(R.string.title_navigation_menu_home)).setIcon(R.drawable.ic_navigation_menu_home);
        menu.add(NAVIGATION_MENU_GROUP_ID, NAVIGATION_MENU_ITEM_ID_MY, NAVIGATION_MENU_ITEM_ID_MY, LKAndroidUtils.getString(R.string.title_navigation_menu_my)).setIcon(R.drawable.ic_navigation_menu_my);
        //增加其它菜单
        menu.add(NAVIGATION_MENU_GROUP_ID, NAVIGATION_MENU_ITEM_ID_1, NAVIGATION_MENU_ITEM_ID_1, LKAndroidUtils.getString(R.string.title_navigation_menu_1)).setIcon(R.drawable.ic_navigation_menu_1);
        menu.add(NAVIGATION_MENU_GROUP_ID, NAVIGATION_MENU_ITEM_ID_2, NAVIGATION_MENU_ITEM_ID_2, LKAndroidUtils.getString(R.string.title_navigation_menu_2)).setIcon(R.drawable.ic_navigation_menu_2);
        menu.add(NAVIGATION_MENU_GROUP_ID, NAVIGATION_MENU_ITEM_ID_3, NAVIGATION_MENU_ITEM_ID_3, LKAndroidUtils.getString(R.string.title_navigation_menu_3)).setIcon(R.drawable.ic_navigation_menu_3);
        //禁用mShiftingMode
        LKViewHelper.disableShiftingMode(navigation);
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
     * 获取最新客户端版本信息
     */
    private void getLastAppVersion() {
        //请求参数
        GetLastAppVersionIn in = new GetLastAppVersionIn();

        //创建请求对象
        LKRetrofit<GetLastAppVersionIn, GetLastAppVersionOut> retrofit = new LKRetrofit<>(this, GetLastAppVersionInvoker.class);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<GetLastAppVersionIn, GetLastAppVersionOut>() {


        });
    }

}
