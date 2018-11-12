package com.lichkin.application.fragments;

import android.content.Context;
import android.widget.LinearLayout;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.activities.AboutActivity;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.beans.LKDynamicButton;
import com.lichkin.framework.app.android.utils.LKDynamicButtonUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 我的页面
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class MyFragment extends MyFragmentDefine {

    @Override
    protected int getFragmentId() {
        return R.layout.my_fragment;
    }

    /** 动态按钮 */
    @BindView(R.id.btns)
    LinearLayout btnsContainer;

    /** 登录前按钮列表 */
    List<LKDynamicButton> btns;

    /** 登录后按钮列表 */
    List<LKDynamicButton> btnsAfterLogin;

    private static int BTN_DIVIDE = 4;
    private static float BTN_ASPECT_RATIO = 1.0f;

    @Override
    protected void initMyInfoExtendsWhenNoToken() {
        btnsContainer.removeAllViews();
        LKDynamicButtonUtils.inflate(btnsContainer, btns, BTN_DIVIDE, BTN_ASPECT_RATIO);
    }

    @Override
    protected void initMyInfoExtends() {
        btnsContainer.removeAllViews();
        for (LKDynamicButton btn : btnsAfterLogin) {
            if (btn.getBtnImgResId() == R.drawable.btn_security_center) {
                btn.setUrl(LKAndroidStatics.securityCenterUrl());
            }
        }
        LKDynamicButtonUtils.inflate(btnsContainer, btnsAfterLogin, BTN_DIVIDE, BTN_ASPECT_RATIO);
    }

    @Override
    protected void clearMyInfoExtends() {
        btnsContainer.removeAllViews();
        LKDynamicButtonUtils.inflate(btnsContainer, btns, BTN_DIVIDE, BTN_ASPECT_RATIO);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        btns = Arrays.asList(
                new LKDynamicButton(R.drawable.btn_score, R.string.title_score, new ScoreFragment(), getFragmentManager()),
                new LKDynamicButton(R.drawable.btn_feedback, R.string.title_feedback, new FeedbackFragment(), getFragmentManager()),
                new LKDynamicButton(R.drawable.btn_about, R.string.title_about, AboutActivity.class)
        );
        btnsAfterLogin = Arrays.asList(
                new LKDynamicButton(R.drawable.btn_score, R.string.title_score, new ScoreFragment(), getFragmentManager()),
                new LKDynamicButton(R.drawable.btn_feedback, R.string.title_feedback, new FeedbackFragment(), getFragmentManager()),
                new LKDynamicButton(R.drawable.btn_about, R.string.title_about, AboutActivity.class),
                new LKDynamicButton(R.drawable.btn_security_center, R.string.title_security_center, LKAndroidStatics.securityCenterUrl()),
                new LKDynamicButton(R.drawable.btn_exit, R.string.title_exit, btnExitCallback)
        );
    }

}