package com.lichkin.app.android.demo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.activities.CompNewsActivity;
import com.lichkin.framework.app.android.beans.LKDynamicButton;
import com.lichkin.framework.app.android.callbacks.LKCallback;
import com.lichkin.framework.app.android.fragments.LKTabFragment;
import com.lichkin.framework.app.android.utils.LKDynamicButtonUtils;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 公司页面
 */
public class CompFragment extends LKTabFragment {

    private Unbinder unbinder;

    /** 当前视图 */
    private View view;

    /** 动态按钮 */
    @BindView(R.id.btns)
    LinearLayout btnsContainer;

    /** 按钮列表 */
    List<LKDynamicButton> btns;

    private static int BTN_DIVIDE = 1;
    private static float BTN_ASPECT_RATIO = 16 / 6f;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        btns = Arrays.asList(
                new LKDynamicButton(R.drawable.btn_comp_news, R.string.title_btn_comp_news, CompNewsActivity.class).addParam("compId", tabId),
                new LKDynamicButton(R.drawable.btn_approval, R.string.title_btn_approval, LKPropertiesLoader.pageTest ? "file:///android_asset/test/test.html" : LKPropertiesLoader.pageBaseUrl + "/workflow" + LKPropertiesLoader.pageSuffix).addParam("compId", tabId),
                new LKDynamicButton(R.drawable.btn_punch_the_clock, R.string.title_btn_punch_the_clock, new LKCallback() {
                    @Override
                    public void call() {
                    }
                })
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //填充页面
        view = inflater.inflate(R.layout.comp_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        btnsContainer.removeAllViews();
        LKDynamicButtonUtils.inflate(btnsContainer, btns, BTN_DIVIDE, BTN_ASPECT_RATIO);

        //返回视图
        return view;
    }

}
