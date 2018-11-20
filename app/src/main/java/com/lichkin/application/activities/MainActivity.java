package com.lichkin.application.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
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
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.in.impl.GetLastAppVersionIn;
import com.lichkin.application.beans.out.impl.GetLastAppVersionOut;
import com.lichkin.application.fragments.HomeFragment;
import com.lichkin.application.fragments.MyListFragment;
import com.lichkin.application.invokers.impl.GetLastAppVersionInvoker;
import com.lichkin.application.testers.GetLastAppVersionTester;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.activities.LKAppCompatActivity;
import com.lichkin.framework.app.android.activities.LKWebViewActivity;
import com.lichkin.framework.app.android.beans.LKDynamicTab;
import com.lichkin.framework.app.android.callbacks.LKBtnCallback;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKBase64;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKSharedPreferences;
import com.lichkin.framework.app.android.utils.LKViewHelper;
import com.lichkin.framework.app.android.widgets.LKDialog;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.lichkin.framework.utils.LKRandomUtils;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 基础MainAcitivity功能实现类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class MainActivity extends LKAppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    /** 当前对象 */
    public static MainActivity activity;

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
            LKWebViewActivity.open(MainActivity.this.getBaseContext(), LKPropertiesLoader.testWebViewUrl);
            return;
        }

        if (dontNeedRequestPermission(Manifest.permission.INTERNET)) {
            //有权限请求接口
            getLastAppVersion();
        }

        String token = LKAndroidStatics.token();
        if (token == null || "".equals(token)) {
            return;
        }

        addDynamicTabs(LKAndroidStatics.dynamicTabs());
    }

    /**
     * 处理动态TAB页
     * @param dynamicTabs 动态TAB页
     */
    public void handleDynamicTabs(List<LKDynamicTab> dynamicTabs) {
        List<LKDynamicTab> savedDynamicTabs = LKAndroidStatics.dynamicTabs();
        if (dynamicTabs == null || dynamicTabs.isEmpty()) {
            if (savedDynamicTabs == null || savedDynamicTabs.isEmpty()) {
                LKLog.d("dynamicTabs -> handle null, saved null. nothing to do.");
            } else {
                LKLog.d("dynamicTabs -> handle null, saved not null. do clear.");
                LKAndroidStatics.dynamicTabs(null);
                hideMenus();
            }
        } else {
            if (savedDynamicTabs == null || savedDynamicTabs.isEmpty()) {
                LKLog.d("dynamicTabs -> handle not null, saved null. do add.");
                LKAndroidStatics.dynamicTabs(dynamicTabs);
                addDynamicTabs(dynamicTabs);
            } else {
                if (dynamicTabs.size() != savedDynamicTabs.size()) {
                    LKLog.d("dynamicTabs -> handle not null, saved not null. size not match, do clear and add.");
                    LKAndroidStatics.dynamicTabs(dynamicTabs);
                    hideMenus();
                    addDynamicTabs(dynamicTabs);
                    return;
                }

                out:
                for (LKDynamicTab dynamicTab : dynamicTabs) {
                    for (LKDynamicTab savedDynamicTab : savedDynamicTabs) {
                        if (dynamicTab.getTabId().equals(savedDynamicTab.getTabId())) {
                            continue out;
                        }
                    }
                    LKLog.d("dynamicTabs -> handle not null, saved not null. size match but tab not match, do clear and add.");
                    LKAndroidStatics.dynamicTabs(dynamicTabs);
                    hideMenus();
                    addDynamicTabs(dynamicTabs);
                    return;
                }
                LKLog.d("dynamicTabs -> handle not null, saved not null. size match and tab match, nothing to do.");
            }
        }
    }

    /**
     * 添加动态TAB页
     * @param dynamicTabs 动态TAB页
     */
    private void addDynamicTabs(List<LKDynamicTab> dynamicTabs) {
        if (dynamicTabs == null || dynamicTabs.isEmpty()) {
            return;
        }
        for (int i = 0; i < dynamicTabs.size(); i++) {
            if (i > 2) {
                break;
            }
            LKDynamicTab tabInfo = dynamicTabs.get(i);
            Bundle bundle = new Bundle();
            bundle.putString("tabId", tabInfo.getTabId());
            bundle.putString("tabName", tabInfo.getTabName());
            fragments.get(i).setArguments(bundle);
            displayMenu(i, tabInfo.getTabName(), LKBase64.toDrawable(tabInfo.getTabIcon()));
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

    public static final int REQUEST_CODE = 0x00000006;

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
        final LKRetrofit<GetLastAppVersionIn, GetLastAppVersionOut> retrofit = new LKRetrofit<>(this, GetLastAppVersionInvoker.class, "LichKin");

        //测试代码
        GetLastAppVersionTester.test(retrofit);

        //执行请求
        retrofit.callSync(in, new LKBaseInvokeCallback<GetLastAppVersionIn, GetLastAppVersionOut>() {

            @Override
            protected void success(Context context, GetLastAppVersionIn getLastAppVersionIn, final GetLastAppVersionOut responseDatas) {
                if (responseDatas == null) {
                    return;
                }
                final boolean forceUpdate = responseDatas.isForceUpdate();
                String tip = responseDatas.getTip();
                LKDialog dlg = new LKDialog(context, tip).setTitle(R.string.dlg_tip_title_update).setCancelable(false);
                dlg.setPositiveButton(R.string.btn_positive_name_update, new LKBtnCallback() {
                    @Override
                    public void call(Context context, DialogInterface dialog) {
                        dlgUpdateClosed = true;
                        if (forceUpdate) {
                            LKWebViewActivity.open(MainActivity.this, responseDatas.getUrl(), REQUEST_CODE);
                        } else {
                            LKWebViewActivity.open(MainActivity.this.getBaseContext(), responseDatas.getUrl());
                        }
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
                switch (errorCode) {
                    case 49999: {//应用已下架
                        String[] errorMessageArr = errorBean.getErrorMessageArr();
                        LKDialog dlg = new LKDialog(context, errorMessageArr[0]).setTitle(errorMessageArr[1]).setCancelable(false);
                        dlg.setPositiveButton(errorMessageArr[2], new LKBtnCallback() {
                            @Override
                            public void call(Context context, DialogInterface dialog) {
                                System.exit(0);
                            }
                        });
                        dlg.show();
                    }
                        break;
                    case 49998: {//版本非法，提示信息。
                        String[] errorMessageArr = errorBean.getErrorMessageArr();
                        LKDialog dlg = new LKDialog(context, errorMessageArr[0]).setTitle(errorMessageArr[1]).setCancelable(false);
                        dlg.setPositiveButton(errorMessageArr[2], new LKBtnCallback() {
                            @Override
                            public void call(Context context, DialogInterface dialog) {
                            }
                        });
                        dlg.show();
                    }
                        break;
                    case 40000://最新版本，不处理。
                        break;
                    case -1://服务器异常
                        break;
                    default:
                        // 其它错误，待约定与实现。
                        // restart();
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            System.exit(0);
        }
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
    private SparseArray<MenuPage> menuPageMap = new SparseArray<>();

    /**
     * 初始化主页
     */
    protected void initHomePage() {
        HOME_MENU_ID = initMenuPage(R.string.title_navigation_menu_home, R.drawable.ic_navigation_menu_home, new HomeFragment(), true);
    }

    /**
     * 初始化菜单页面。使用initMenuPage方法进行初始化。
     */
    protected abstract void initMenuPages();

    /**
     * 初始化我的页面
     */
    protected void initMyPage() {
        MY_MENU_ID = initMenuPage(R.string.title_navigation_menu_my, R.drawable.ic_navigation_menu_my, new MyListFragment(), true);
    }

    /** 自定义菜单 */
    public List<MenuPage> userDefinedMenus = new ArrayList<>();
    public List<Fragment> fragments = new ArrayList<>();

    /**
     * 初始化对象
     * @param titleId 标题ID
     * @param iconId 图标ID
     * @param fragment 页面
     * @param lock 锁定
     * @return 菜单ID
     */
    protected int initMenuPage(int titleId, int iconId, Fragment fragment, boolean lock) {
        int menuId = LKRandomUtils.randomInRange(10000, 99999);
        MenuPage menuPage = new MenuPage(menuId, titleId, iconId, orderId++, fragment);
        menuPageMap.put(menuId, menuPage);
        if (!lock) {
            userDefinedMenus.add(menuPage);
            fragments.add(fragment);
        }
        return menuId;
    }

    /** 适配器位置与菜单页面映射关系 */
    public List<MenuPage> menuPageList = new ArrayList<>();
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

    /** 导航栏高度 */
    public int navigationHeight;

    /**
     * 初始化导航栏
     */
    private void initBottomNavigationView() {
        navigation = findViewById(R.id.navigation);

        //设置导航栏高度
        navigation.measure(0, 0);
        navigationHeight = navigation.getMeasuredHeight();

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
        //禁止销毁数量
        viewPager.setOffscreenPageLimit(5);
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
        showMenu(menuId, LKAndroidUtils.getString(menuPage.titleId), LKAndroidUtils.getDrawable(menuPage.iconId));
    }

    /**
     * 显示菜单
     * @param menuId 菜单ID
     * @param title 标题
     * @param icon 图标
     */
    protected void showMenu(int menuId, String title, Drawable icon) {
        MenuPage menuPage = menuPageMap.get(menuId);
        if (menuPageList.contains(menuPage)) {
            return;
        }

        //增加菜单
        navigation.getMenu().add(NAVIGATION_MENU_GROUP_ID, menuId, menuPage.orderId, title).setIcon(icon);
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
     * @param idx 自定义菜单索引值。从0开始。
     * @param title 标题
     * @param icon 图标
     */
    protected void displayMenu(int idx, String title, Drawable icon) {
        if (idx > userDefinedMenus.size()) {
            return;
        }
        showMenu(userDefinedMenus.get(idx).menuId, title, icon);
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
     * 销毁菜单
     * @param idx 自定义菜单索引值。从0开始。
     */
    private void destoryMenu(int idx) {
        //第0个是主页；最后一个是我的页面。
        if (idx > userDefinedMenus.size()) {
            return;
        }
        hideMenu(userDefinedMenus.get(idx).menuId);
    }

    /**
     * 销毁菜单
     */
    public void destoryMenus() {
        for (int i = 0; i < userDefinedMenus.size(); i++) {
            destoryMenu(i);
        }
    }

    /**
     * 销毁菜单
     */
    public static void hideMenus() {
        activity.destoryMenus();
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
