package com.lichkin.application.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;

/**
 * 介绍页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class IntroActivity extends AppIntro2 {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showSkipButton(false);

        Fragment[] fragmentArr = MainActivity.activity.getIntroFragmentArr();
        for (Fragment fragment : fragmentArr) {
            addSlide(fragment);
        }
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

}
