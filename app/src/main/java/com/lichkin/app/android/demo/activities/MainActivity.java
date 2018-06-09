package com.lichkin.app.android.demo.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lichkin.app.android.demo.R;
import com.lichkin.app.android.demo.fragments.IntroFragment1;
import com.lichkin.app.android.demo.fragments.IntroFragment2;
import com.lichkin.app.android.demo.fragments.IntroFragment3;

/**
 * 主页面
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class MainActivity extends com.lichkin.application.activities.MainActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static int MENU_1;

    @Override
    protected void initMenuPages() {
        MENU_1 = initMenuPage(R.string.title_navigation_menu_1, R.drawable.ic_navigation_menu_1, new IntroFragment3());
    }

    @Override
    protected Fragment[] initIntroFragmentArr() {
        return new Fragment[]{new IntroFragment1(), new IntroFragment2(), new IntroFragment3()};
    }

}
