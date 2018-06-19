package com.lichkin.app.android.demo.activities;


import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lichkin.app.android.demo.R;
import com.lichkin.app.android.demo.fragments.CompFragment;
import com.lichkin.app.android.demo.fragments.IntroFragment1;
import com.lichkin.app.android.demo.fragments.IntroFragment2;
import com.lichkin.app.android.demo.fragments.IntroFragment3;
import com.lichkin.framework.app.android.utils.LKToast;

/**
 * 主页面
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class MainActivity extends com.lichkin.application.activities.MainActivity {

    /** 定位信息 */
    public static boolean locationAuth = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //请求定位权限
        dontNeedRequestPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        dontNeedRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    protected void onRequestPermissionResultGranted(String permissionName) {
        super.onRequestPermissionResultGranted(permissionName);
        switch (permissionName) {
            //定位权限授权通过后初始化定位信息
            case Manifest.permission.ACCESS_COARSE_LOCATION:
            case Manifest.permission.ACCESS_FINE_LOCATION:
                locationAuth = true;
                break;
        }
    }

    @Override
    protected void onRequestPermissionResultNotGranted(String permissionName) {
        super.onRequestPermissionResultNotGranted(permissionName);
        switch (permissionName) {
            //无定位权限
            case Manifest.permission.ACCESS_COARSE_LOCATION:
            case Manifest.permission.ACCESS_FINE_LOCATION:
                locationAuth = false;
                LKToast.showError(R.string.can_not_locate);
                break;
        }
    }

    @Override
    protected void initMenuPages() {
        initMenuPage(R.string.title_navigation_menu_1, R.drawable.ic_navigation_menu_1, new CompFragment(), false);
        initMenuPage(R.string.title_navigation_menu_2, R.drawable.ic_navigation_menu_2, new CompFragment(), false);
        initMenuPage(R.string.title_navigation_menu_3, R.drawable.ic_navigation_menu_3, new CompFragment(), false);
    }

    @Override
    protected Fragment[] initIntroFragmentArr() {
        return new Fragment[]{new IntroFragment1(), new IntroFragment2(), new IntroFragment3()};
    }

}
