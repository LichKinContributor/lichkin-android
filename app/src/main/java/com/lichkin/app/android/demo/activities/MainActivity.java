package com.lichkin.app.android.demo.activities;


import android.os.Bundle;

/**
 * 主页面
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class MainActivity extends com.lichkin.application.activities.MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showMenuHome();
        showMenuMy();
    }

    @Override
    protected void onMenuHomeSelected() {
    }

    @Override
    protected void onMenuMySelected() {
    }

    @Override
    protected void onMenu1Selected() {
    }

}
