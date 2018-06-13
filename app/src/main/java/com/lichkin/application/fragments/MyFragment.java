package com.lichkin.application.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.activities.AboutActivity;
import com.lichkin.framework.app.android.beans.LKDynamicButton;
import com.lichkin.framework.app.android.utils.LKDynamicButtonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的页面
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class MyFragment extends Fragment {

    /** 当前视图 */
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //填充页面
        view = inflater.inflate(R.layout.my_fragment, container, false);

        //初始化按钮栏
        List<LKDynamicButton> btns = new ArrayList<>();
        btns.add(new LKDynamicButton(R.drawable.btn_security_center, R.string.title_security_center));
        btns.add(new LKDynamicButton(R.drawable.btn_score, R.string.title_score, new ScoreFragment(), getFragmentManager()));
        btns.add(new LKDynamicButton(R.drawable.btn_feedback, R.string.title_feedback, new FeedbackFragment(), getFragmentManager()));
        btns.add(new LKDynamicButton(R.drawable.btn_about, R.string.title_about, AboutActivity.class));
        LKDynamicButtonUtils.inflate((LinearLayout) view.findViewById(R.id.btns), btns, 4, 1);

        //返回视图
        return view;
    }

}