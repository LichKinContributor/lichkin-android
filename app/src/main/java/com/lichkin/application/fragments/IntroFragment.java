package com.lichkin.application.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 介绍页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class IntroFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(), container, false);
    }

    /**
     * 获取介绍页资源ID
     * @return 介绍页资源ID
     */
    protected abstract int getLayoutResId();

}