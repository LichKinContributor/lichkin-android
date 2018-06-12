package com.lichkin.framework.app.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.lichkin.app.android.demo.R;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.callbacks.LKBtnCallback;
import com.lichkin.framework.app.android.callbacks.LKCallJsFuncCallback;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.app.android.widgets.LKDialog;
import com.lichkin.framework.app.android.widgets.LKProgressBridgeWebView;
import com.lichkin.framework.defines.beans.LKAlertBean;
import com.lichkin.framework.defines.beans.LKCallJsFuncBean;
import com.lichkin.framework.defines.beans.LKCallJsFuncCallbackBean;
import com.lichkin.framework.defines.beans.LKLogBean;
import com.lichkin.framework.defines.beans.LKToastBean;
import com.lichkin.framework.json.LKJsonUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;

/**
 * HTML交互实现类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKWebViewActivity extends Activity {

    /** 页面地址键 */
    private static final String KEY_URL = "url";

    /** JsBridge对象 */
    private LKProgressBridgeWebView webView;

    /** loading遮罩对象 */
    private TextView loadingMask;

    /** loading对象 */
    private AVLoadingIndicatorView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_webview);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //引用webview控件
        webView = findViewById(R.id.webview_container);

        //引用loading遮罩控件
        loadingMask = findViewById(R.id.loading_mask);

        //引用loading控件
        loading = findViewById(R.id.loading);

        //禁用缓存
        //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        //读取页面地址并加载页面
        Intent intent = getIntent();
        String url = intent.getStringExtra(KEY_URL);
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
                LKAlertBean bean = LKJsonUtils.toObj(data, LKAlertBean.class);

                String msg = bean.getMsg();
                if (bean.isJsonMsg()) {
                    msg = msg.replaceAll("\\\"", "\"");
                }

                LKDialog dlg = new LKDialog(LKWebViewActivity.this, msg).setCancelable(false);
                dlg.setPositiveButton(new LKBtnCallback() {
                    @Override
                    public void call(Context context, DialogInterface dialog) {
                        function.onCallBack(null);
                    }
                });
                dlg.show();
            }
        });

        webView.registerHandler("showLoading", new BridgeHandler() {
            @Override
            public void handler(final String data, final CallBackFunction function) {
                loadingMask.setVisibility(View.VISIBLE);
                loading.show();
            }
        });

        webView.registerHandler("closeLoading", new BridgeHandler() {
            @Override
            public void handler(final String data, final CallBackFunction function) {
                loading.hide();
                loadingMask.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 打开页面
     * @param from 发起环境上下文
     * @param to 页面地址
     */
    public static void open(Context from, String to) {
        Map<String, Object> params = new HashMap<>();
        params.put("appKey", LKAndroidStatics.appKey());
        params.put("clientType", "ANDROID");
        params.put("versionX", LKAndroidStatics.versionX());
        params.put("versionY", LKAndroidStatics.versionY());
        params.put("versionZ", LKAndroidStatics.versionZ());
        params.put("token", LKAndroidStatics.token());
        params.put("uuid", LKAndroidStatics.uuid());
        params.put("screenWidth", LKAndroidStatics.screenWidth());
        params.put("screenHeight", LKAndroidStatics.screenHeight());
        open(from, to, params);
    }

    /**
     * 打开页面
     * @param from 发起环境上下文
     * @param to 页面地址
     * @param params 参数
     */
    public static void open(Context from, String to, Map<String, Object> params) {
        Intent intent = new Intent(from, LKWebViewActivity.class);
        StringBuilder sb = new StringBuilder(to);
        if (params != null) {
            boolean first = !to.contains("?");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (first) {
                    sb.append("?");
                    first = false;
                } else {
                    sb.append("&");
                }
                sb.append(key).append("=").append(value == null ? "" : value.toString());
            }
        }
        intent.putExtra(KEY_URL, sb.toString());
        from.startActivity(intent);
    }

}
