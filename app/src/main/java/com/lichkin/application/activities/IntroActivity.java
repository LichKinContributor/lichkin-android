package com.lichkin.application.activities;

import android.content.pm.ActivityInfo;
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

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //禁用退出按钮
        showSkipButton(false);

        //加入介绍页
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
