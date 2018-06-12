package com.lichkin.application.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lichkin.app.android.demo.R;
import com.lichkin.framework.app.android.activities.LKWebViewActivity;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;

/**
 * 关于页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_about);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //点击事件
        bindClickAction(R.id.version_info, "/version_info");
        bindClickAction(R.id.about_us, "/about_us");
        bindClickAction(R.id.copyright, "/copyright");
        bindClickAction(R.id.licensing, "/licensing");
        bindClickAction(R.id.service_agreement, "/service_agreement");
        bindClickAction(R.id.privacy_right_policy, "/privacy_right_policy");
        bindClickAction(R.id.instruction, "/instruction");
    }

    /**
     * 绑定点击事件
     * @param resId 资源ID
     * @param subUrl 子路径
     */
    private void bindClickAction(int resId, final String subUrl) {
        findViewById(resId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LKPropertiesLoader.pageTest) {
                    LKWebViewActivity.open(AboutActivity.this, "file:///android_asset/test/test.html");
                } else {
                    LKWebViewActivity.open(AboutActivity.this, LKPropertiesLoader.pageBaseUrl + subUrl + LKPropertiesLoader.pageSuffix);
                }
            }
        });
    }

}
