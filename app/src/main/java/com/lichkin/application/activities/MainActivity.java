package com.lichkin.application.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.impl.in.GetLastAppVersionIn;
import com.lichkin.application.beans.impl.out.GetLastAppVersionOut;
import com.lichkin.application.fragments.HomeFragment;
import com.lichkin.application.fragments.MyFragment;
import com.lichkin.application.invokers.impl.GetLastAppVersionInvoker;
import com.lichkin.application.testers.GetLastAppVersionTester;
import com.lichkin.framework.app.android.activities.LKAppCompatActivity;
import com.lichkin.framework.app.android.activities.LKWebViewActivity;
import com.lichkin.framework.app.android.callbacks.LKBtnCallback;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKSharedPreferences;
import com.lichkin.framework.app.android.utils.LKViewHelper;
import com.lichkin.framework.app.android.widgets.LKDialog;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.lichkin.framework.utils.LKRandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

/**
 * 基础MainAcitivity功能实现类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class MainActivity extends LKAppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    /** 当前对象 */
    static MainActivity activity;

    /** 主页菜单ID */
    public static int HOME_MENU_ID;
    /** 我的页面菜单ID */
    public static int MY_MENU_ID;
    /** 首次创建 */
    private boolean create = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //当前对象赋值
        activity = this;

        //启动介绍页
        if (LKSharedPreferences.getBoolean("firstLaunch", true)) {
            LKSharedPreferences.putBoolean("firstLaunch", false);
            introFragmentArr = initIntroFragmentArr();
            if (introFragmentArr != null && introFragmentArr.length != 0) {
                Intent intent = new Intent(this, IntroActivity.class);
                startActivity(intent);
            }
        }

        //引用布局文件
        setContentView(R.layout.activity_main);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //初始化菜单页面
        initHomePage();
        initMenuPages();
        initMyPage();

        //初始化导航栏
        initBottomNavigationView();

        //初始化滑动页面
        initViewPager();

        showMenu(HOME_MENU_ID);
        showMenu(MY_MENU_ID);
    }

    @Override
    protected void onStart() {
        super.onStart();

        create = false;

        if (LKPropertiesLoader.testWebView) {
            Intent intent = new Intent(MainActivity.this, LKWebViewActivity.class);
            intent.putExtra("url", LKPropertiesLoader.testWebViewUrl);
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
        if (!LKPropertiesLoader.testRetrofit) {
            GetLastAppVersionTester.test(retrofit);
        }

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
     * 菜单页面对象
     */
    @RequiredArgsConstructor
    private class MenuPage {
        /** 菜单ID */
        private final int menuId;
        /** 标题ID */
        private final int titleId;
        /** 图标ID */
        private final int iconId;
        /** 排序ID */
        private final int orderId;
        /** 页面 */
        private final Fragment fragment;
    }

    /** 排序ID */
    private int orderId = 0;
    /** 菜单页面对象映射集合 */
    private Map<Integer, MenuPage> menuPageMap = new HashMap<>();

    /**
     * 初始化主页
     */
    protected void initHomePage() {
        HOME_MENU_ID = initMenuPage(R.string.title_navigation_menu_home, R.drawable.ic_navigation_menu_home, new HomeFragment());
    }

    /**
     * 初始化菜单页面。使用initMenuPage方法进行初始化。
     */
    protected abstract void initMenuPages();

    /**
     * 初始化我的页面
     */
    protected void initMyPage() {
        MY_MENU_ID = initMenuPage(R.string.title_navigation_menu_my, R.drawable.ic_navigation_menu_my, new MyFragment());
    }

    /**
     * 初始化对象
     * @param titleId 标题ID
     * @param iconId 图标ID
     * @param fragment 页面
     * @return 菜单ID
     */
    protected int initMenuPage(int titleId, int iconId, Fragment fragment) {
        int menuId = LKRandomUtils.randomInRange(10000, 99999);
        menuPageMap.put(menuId, new MenuPage(menuId, titleId, iconId, orderId++, fragment));
        return menuId;
    }

    /** 适配器位置与菜单页面映射关系 */
    private List<MenuPage> menuPageList = new ArrayList<>();
    /** 适配器 */
    private FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        @Override
        public Fragment getItem(int position) {
            return menuPageList.get(position).fragment;
        }

        @Override
        public int getCount() {
            return menuPageList.size();
        }

        @Override
        public long getItemId(int position) {
            return menuPageList.get(position).hashCode();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return PagerAdapter.POSITION_NONE;
        }

    };

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
                int menuId = item.getItemId();
                viewPager.setCurrentItem(menuPageList.indexOf(menuPageMap.get(menuId)));
                onMenuSelected(menuId);
                return true;
            }

        });
    }

    /**
     * 菜单点击事件
     * @param menuId 菜单ID
     */
    protected void onMenuSelected(int menuId) {
    }

    /** 滑动页面 */
    private ViewPager viewPager;

    /**
     * 初始化滑动页面
     */
    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);
        //添加适配器
        viewPager.setAdapter(adapter);
        //为滑动页面增加监听事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().findItem(menuPageList.get(position).menuId).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }

    /** 导航栏菜单ID */
    private static final int NAVIGATION_MENU_GROUP_ID = Menu.NONE;

    /**
     * 显示菜单
     * @param menuId 菜单ID
     */
    protected void showMenu(int menuId) {
        MenuPage menuPage = menuPageMap.get(menuId);
        if (menuPageList.contains(menuPage)) {
            return;
        }

        //增加菜单
        navigation.getMenu().add(NAVIGATION_MENU_GROUP_ID, menuId, menuPage.orderId, LKAndroidUtils.getString(menuPage.titleId)).setIcon(menuPage.iconId);
        //禁用mShiftingMode
        LKViewHelper.disableShiftingMode(navigation);

        //增加页面
        if (menuPageList.isEmpty()) {
            //直接增加
            menuPageList.add(menuPage);
        } else {
            //按照序号选择应该存入的位置
            //通常都是在后面动态增加，倒序处理。
            boolean added = false;
            for (int i = menuPageList.size() - 1; i >= 0; i--) {
                if (menuPage.orderId > menuPageList.get(i).orderId) {
                    menuPageList.add(i + 1, menuPage);
                    added = true;
                    break;
                }
            }
            if (!added) {
                menuPageList.add(menuPage);
            }
        }
        //更新滑动页面状态
        adapter.notifyDataSetChanged();

        //将页面滑动到刚打开的页面
        switchMenuPage(create ? 0 : menuPageList.indexOf(menuPage));
    }

    /**
     * 显示菜单
     * @param menuId 菜单ID
     */
    public static void show(int menuId) {
        activity.showMenu(menuId);
    }

    /**
     * 隐藏菜单
     * @param menuId 菜单ID
     */
    protected void hideMenu(int menuId) {
        MenuPage menuPage = menuPageMap.get(menuId);
        if (!menuPageList.contains(menuPage)) {
            return;
        }

        //移除菜单
        navigation.getMenu().removeItem(menuId);
        //禁用mShiftingMode
        LKViewHelper.disableShiftingMode(navigation);

        //移除页面
        menuPageList.remove(menuPage);
        //更新滑动页面状态
        adapter.notifyDataSetChanged();

        //将页面滑动到第一个页面
        if (menuPageList.isEmpty()) {
            return;
        }
        switchMenuPage(0);
    }

    /**
     * 隐藏菜单
     * @param menuId 菜单ID
     */
    public static void hide(int menuId) {
        activity.hideMenu(menuId);
    }

    /**
     * 将页面滑动到指定位置
     * @param position 位置
     */
    private void switchMenuPage(final int position) {
        viewPager.setCurrentItem(position);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                navigation.getMenu().getItem(position).setChecked(true);
            }
        }, 0);
    }

    /** 介绍页列表 */
    private Fragment[] introFragmentArr;

    /**
     * 初始化介绍页
     * @return 介绍页
     */
    protected abstract Fragment[] initIntroFragmentArr();

    /**
     * 获取介绍页列表
     * @return 介绍页列表
     */
    public Fragment[] getIntroFragmentArr() {
        return introFragmentArr;
    }

}
