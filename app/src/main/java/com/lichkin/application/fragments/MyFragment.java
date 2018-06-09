package com.lichkin.application.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lichkin.app.android.demo.R;

/**
 * 我的页面
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class MyFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_fragment, container, false);
    }

}