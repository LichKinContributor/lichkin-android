package com.lichkin.application.fragments;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;

import com.lichkin.app.android.demo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MyListFragment extends MyFragmentDefine {

    @Override
    protected int getFragmentId() {
        return R.layout.my_list_fragment;
    }

    @BindView(R.id.logined_btns)
    LinearLayout loginedBtnsContainer;

    @Override
    protected void initMyInfoExtends() {
        loginedBtnsContainer.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initMyInfoExtendsWhenNoToken() {
        loginedBtnsContainer.setVisibility(View.GONE);
    }

    @Override
    protected void clearMyInfoExtends() {
        loginedBtnsContainer.setVisibility(View.GONE);
    }

    /** 评分按钮事件 */
    @OnClick(R.id.btn_score)
    void btnScoreClick() {
        DialogFragment dialogFragment = new ScoreFragment();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) {
            return;
        }
        dialogFragment.show(fragmentManager, dialogFragment.getTag());
    }

    /** 反馈按钮事件 */
    @OnClick(R.id.btn_feedback)
    void btnFeedbackClick() {
        FeedbackFragment dialogFragment = new FeedbackFragment();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) {
            return;
        }
        dialogFragment.show(fragmentManager, dialogFragment.getTag());
    }

//    /** 关于按钮事件 */
//    @OnClick(R.id.btn_about)
//    void btnAboutClick() {
//        Context context = getContext();
//        if (context == null) {
//            return;
//        }
//        Intent intent = new Intent(context, AboutActivity.class);
//        context.startActivity(intent);
//    }

//    /** 安全中心按钮事件 */
//    @OnClick(R.id.btn_security_center)
//    void btnSecurityCenterClick() {
//        LKWebViewActivity.open(getContext(), LKAndroidStatics.securityCenterUrl());
//    }

    /** 退出登录按钮事件 */
    @OnClick(R.id.btn_exit)
    void btnExitClick() {
        btnExitCallback.call();
    }

}
