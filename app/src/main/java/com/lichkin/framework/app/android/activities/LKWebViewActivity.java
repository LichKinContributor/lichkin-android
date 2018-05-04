package com.lichkin.framework.app.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.lichkin.app.android.demo.R;
import com.lichkin.framework.app.android.callbacks.LKBtnCallback;
import com.lichkin.framework.app.android.callbacks.LKCallJsFuncCallback;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.app.android.widgets.LKDialog;
import com.lichkin.framework.defines.beans.LKCallJsFuncBean;
import com.lichkin.framework.defines.beans.LKCallJsFuncCallbackBean;
import com.lichkin.framework.defines.beans.LKLogBean;
import com.lichkin.framework.defines.beans.LKToastBean;
import com.lichkin.framework.json.LKJsonUtils;

/**
 * HTML交互实现类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKWebViewActivity extends Activity {

    /** JsBridge对象 */
    private BridgeWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_webview);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //引用webview控件
        webView = findViewById(R.id.webview_container);

        //禁用缓存
        //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        //读取页面地址并加载页面
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        LKLog.d("load url: " + url);
        webView.loadUrl(url);

        //增加自定义实现方法
        addHandleMethods();
    }

    /**
     * 调用JavaScript方法
     * @param data 调用方法所需对象
     * @param callback 回调方法
     */
    public void callJsFunc(LKCallJsFuncBean data, final LKCallJsFuncCallback callback) {
        webView.callHandler("callJsFunc", LKJsonUtils.toJson(data), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                if (callback != null) {
                    callback.call(LKJsonUtils.toObj(data, LKCallJsFuncCallbackBean.class));
                }
            }
        });
    }

    /**
     * 增加自定义实现方法
     */
    private void addHandleMethods() {
        webView.registerHandler("log", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LKLogBean bean = LKJsonUtils.toObj(data, LKLogBean.class);

                String msg = bean.getMsg();
                String info = "WebViewJavascriptBridge -> log -> msg: ";
                if (bean.isJsonMsg()) {
                    info += msg.replaceAll("\\\"", "\"");
                } else {
                    info += msg;
                }

                switch (bean.getType()) {
                    case "verbose":
                        LKLog.v(info);
                        break;
                    case "debug":
                        LKLog.d(info);
                        break;
                    case "info":
                        LKLog.i(info);
                        break;
                    case "warn":
                        LKLog.w(info);
                        break;
                    case "error":
                    case "assert":
                    default:
                        LKLog.e(info);
                        break;
                }
            }
        });

        webView.registerHandler("toast", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LKToastBean bean = LKJsonUtils.toObj(data, LKToastBean.class);

                String msg = bean.getMsg();
                if (bean.isJsonMsg()) {
                    msg = msg.replaceAll("\\\"", "\"");
                }

                //noinspection deprecation
                LKToast.makeTextAndShow(LKWebViewActivity.this, msg, bean.getTimeout());
            }
        });

        webView.registerHandler("alert", new BridgeHandler() {
            @Override
            public void handler(final String data, final CallBackFunction function) {
                LKDialog dlg = new LKDialog(LKWebViewActivity.this, data).setCancelable(false);
                dlg.setPositiveButton(new LKBtnCallback() {
                    @Override
                    public void call(Context context, DialogInterface dialog) {
                        function.onCallBack(data);
                    }
                });
                dlg.show();
            }
        });
    }

}
