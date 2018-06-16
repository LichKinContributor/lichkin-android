package com.lichkin.application.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichkin.app.android.demo.R;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 等级
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LevelFragment extends DialogFragment {

    private Unbinder unbinder;

    @BindView(R.id.container)
    LinearLayout containerLayout;

    @Override
    public void onStart() {
        super.onStart();
        //设置宽高
        Window window = getDialog().getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = LKAndroidUtils.getPxValueByDpValue(300);
        layoutParams.height = LKAndroidUtils.getPxValueByDpValue(350);
        window.setAttributes(layoutParams);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.level_fragment, container);

        unbinder = ButterKnife.bind(this, view);

        Context context = getContext();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LKAndroidUtils.getPxValueByDpValue(300), ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 1; i <= 63; i++) {
            LinearLayout starContainer = new LinearLayout(context);
            starContainer.setOrientation(LinearLayout.HORIZONTAL);
            starContainer.setLayoutParams(layoutParams);
            TextView textView = new TextView(context);
            textView.setWidth(LKAndroidUtils.getPxValueByDpValue(48));
            textView.setHeight(LKAndroidUtils.getPxValueByDpValue(20));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextColor(Color.WHITE);
            textView.setText(LKAndroidUtils.getString(R.string.level) + i);
            starContainer.addView(textView);
            inflateLevel(starContainer, i);
            containerLayout.addView(starContainer);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 填充等级
     * @param levelLayout 容器对象
     * @param level 等级
     */
    public static void inflateLevel(LinearLayout levelLayout, int level) {
        Context context = levelLayout.getContext();
        int red = level / 16;
        int mod = level % 16;
        int green = mod / 4;
        int yellow = mod % 4;
        for (int i = 0; i < red; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LKAndroidUtils.getPxValueByDpValue(20), LKAndroidUtils.getPxValueByDpValue(20)));
            imageView.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.star_red));
            levelLayout.addView(imageView);
        }
        for (int i = 0; i < green; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LKAndroidUtils.getPxValueByDpValue(20), LKAndroidUtils.getPxValueByDpValue(20)));
            imageView.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.star_green));
            levelLayout.addView(imageView);
        }
        for (int i = 0; i < yellow; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LKAndroidUtils.getPxValueByDpValue(20), LKAndroidUtils.getPxValueByDpValue(20)));
            imageView.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.star_yellow));
            levelLayout.addView(imageView);
        }
    }

}
