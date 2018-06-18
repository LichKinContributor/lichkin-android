package com.lichkin.app.android.demo.activities;


import android.support.v4.app.Fragment;

import com.lichkin.app.android.demo.R;
import com.lichkin.app.android.demo.fragments.CompFragment;
import com.lichkin.app.android.demo.fragments.IntroFragment1;
import com.lichkin.app.android.demo.fragments.IntroFragment2;
import com.lichkin.app.android.demo.fragments.IntroFragment3;

/**
 * 主页面
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class MainActivity extends com.lichkin.application.activities.MainActivity {

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
