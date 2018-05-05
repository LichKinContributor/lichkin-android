package com.lichkin.framework.app.android.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.lichkin.app.android.demo.R;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;

/**
 * 为交互webView增加进度条
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKProgressBridgeWebView extends BridgeWebView {

    /** 进度条 */
    private ProgressBar progressbar;

    /**
     * 构造方法
     * @param context 上下文环境
     * @param attrs 属性集合
     */
    public LKProgressBridgeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 创建进度条
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 10, 0, 0));
        progressbar.setProgressDrawable(LKAndroidUtils.getDrawable(R.drawable.lk_layer_list_progress_bar));
        addView(progressbar);

        // 设置进度条状态
        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressbar.setVisibility(GONE);
                } else {
                    if (progressbar.getVisibility() == GONE) {
                        progressbar.setVisibility(VISIBLE);
                    }
                    progressbar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

}
