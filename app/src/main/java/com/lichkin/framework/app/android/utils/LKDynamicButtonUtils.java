package com.lichkin.framework.app.android.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichkin.app.android.demo.R;
import com.lichkin.framework.app.android.activities.LKWebViewActivity;
import com.lichkin.framework.app.android.beans.LKDynamicButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态按钮工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKDynamicButtonUtils {

    /** 按钮链接信息 */
    private static final Map<Integer, String> btnUrls = new HashMap<>();

    /**
     * 初始化按钮栏
     * @param fragment 填充按钮的页面对象
     * @param container 填充按钮的具体布局对象
     * @param btns 按钮列表
     * @param divide 每行展现的按钮数量
     * @param aspectRatio 按钮的宽高比
     */
    public static void inflate(final Fragment fragment, final ViewGroup container, final List<LKDynamicButton> btns, final int divide, final float aspectRatio) {
        final Context context = container.getContext();
        final LayoutInflater inflater = fragment.getLayoutInflater();

        final int screenWidth = LKAndroidUtils.getScreenDispaly().getWidth(); // 取屏幕宽度
        final int leftRightMargin = screenWidth / 32; // 左右边距，将屏幕分成32份，留边。
        final int btnMargin = leftRightMargin / 2; // 按钮边距等于左右边距的四分之一
        final float lineWidth = (float) screenWidth - (leftRightMargin * 2); // 行宽需去掉左右边距
        final float btnWidth = (lineWidth - (btnMargin * (divide - 1))) / divide; // 行宽 = (按钮个数 * 按钮宽度) + ((按钮个数 - 1) * 按钮间距)
        final float btnHeight = btnWidth / aspectRatio; // 按钮高度等于按钮宽度除以按钮的宽高比
        int btnTitleHeight = (int) (btnHeight / 5); // 按钮标题高度
        final int btnImgHeight = (int) (btnHeight - btnTitleHeight); // 按钮图标高度

        // 行样式
        final LinearLayout.LayoutParams lineLayoutParams = new LinearLayout.LayoutParams((int) lineWidth, (int) btnHeight);
        lineLayoutParams.setMargins(leftRightMargin, btnMargin, 0, 0);

        // 动态添加按钮
        LinearLayout lineLayout = null;
        for (int i = 0; i < btns.size(); i++) {
            final LKDynamicButton button = btns.get(i);
            final int btnImgResId = button.getBtnImgResId();
            // 按钮样式
            final LinearLayout.LayoutParams btnLayoutParams = new LinearLayout.LayoutParams((int) btnWidth, (int) btnHeight);
            if ((i % divide) == 0) {// 每divide个创建一行
                lineLayout = new LinearLayout(context);
                lineLayout.setOrientation(LinearLayout.HORIZONTAL);
                lineLayout.setLayoutParams(lineLayoutParams);
                container.addView(lineLayout);
            } else {
                btnLayoutParams.setMargins(btnMargin, 0, 0, 0);
            }

            final FrameLayout btnListLayout = (FrameLayout) inflater.inflate(R.layout.dynamic_buttons, null);
            btnListLayout.setLayoutParams(btnLayoutParams);

            final ImageView btnImgView = btnListLayout.findViewById(R.id.dynamic_buttons_button_img);
            btnImgView.setImageResource(btnImgResId);
            final ViewGroup.LayoutParams imgLayoutParams = btnImgView.getLayoutParams();
            imgLayoutParams.width = imgLayoutParams.height = btnImgHeight;
            btnImgView.setLayoutParams(imgLayoutParams);

            final TextView btnTitleView = btnListLayout.findViewById(R.id.dynamic_buttons_button_text);
            btnTitleView.setText(button.getBtnTitleResId());
            final ViewGroup.LayoutParams btnTitleLayoutParams = btnTitleView.getLayoutParams();
            btnTitleLayoutParams.height = btnTitleHeight;
            btnTitleView.setLayoutParams(btnTitleLayoutParams);

            btnListLayout.setClickable(true);
            btnListLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Class<?> activityClass = button.getToActivityClass();
                    if (activityClass == null) {
                        LKToast.showTip(R.string.not_implemented);
                        return;
                    }
                    if (activityClass.equals(LKWebViewActivity.class)) {
                        if (!btnUrls.containsKey(btnImgResId)) {
                            LKToast.showTip(R.string.error_NOT_FOUND);
                            return;
                        }
                        LKWebViewActivity.open(context, btnUrls.get(btnImgResId));
                    } else {
                        context.startActivity(new Intent(context, activityClass));
                    }
                }
            });

            lineLayout.addView(btnListLayout);
        }
    }

}
