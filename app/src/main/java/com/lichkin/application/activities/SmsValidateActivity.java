package com.lichkin.application.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.in.impl.FastLoginIn;
import com.lichkin.application.beans.in.impl.GetSmsSecurityCodeIn;
import com.lichkin.application.beans.out.impl.FastLoginOut;
import com.lichkin.application.beans.out.impl.GetSmsSecurityCodeOut;
import com.lichkin.application.fragments.MyFragment;
import com.lichkin.application.invokers.impl.FastLoginInvoker;
import com.lichkin.application.invokers.impl.GetSmsSecurityCodeInvoker;
import com.lichkin.application.testers.FastLoginTester;
import com.lichkin.application.testers.GetSmsSecurityCodeTester;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.activities.LKWebViewActivity;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 短信验证页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class SmsValidateActivity extends AppCompatActivity {

    /** 重新发送按钮 */
    @BindView(R.id.btn_resend)
    TextView btnResend;

    /** 重新发送按钮事件 */
    @OnClick(R.id.btn_resend)
    void btnResendClick() {
        invokeGetSmsSecurityCode();
    }

    /** 下一步按钮 */
    @BindView(R.id.btn_next)
    TextView btnNextView;

    /** 下一步按钮事件 */
    @OnClick(R.id.btn_next)
    void btnNextClick() {
        invokeFastLogin();
    }

    /** 手机号码 */
    @BindView(R.id.cellphone)
    TextView cellphoneView;

    /** 验证码 */
    @BindView(R.id.security_code)
    TextView securityCodeView;

    /** 加载 */
    @BindView(R.id.loading)
    AVLoadingIndicatorView loadingView;

    /** 服务协议按钮 */
    @BindView(R.id.btn_agreement)
    TextView btnAgreementView;

    /** 服务协议按钮事件 */
    @OnClick(R.id.btn_agreement)
    void btnAgreementClick() {
        if (LKPropertiesLoader.pageTest) {
            LKWebViewActivity.open(this, "file:///android_asset/test/test.html");
        } else {
            LKWebViewActivity.open(this, LKPropertiesLoader.pageBaseUrl + "/service_agreement" + LKPropertiesLoader.pageSuffix);
        }
    }

    /** 手机号码 */
    private String cellphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_sms_validate);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //绑定页面
        ButterKnife.bind(this);

        //增加下划线
        btnAgreementView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        //填充手机号码
        cellphone = getIntent().getStringExtra("cellphone");
        cellphoneView.setText(cellphone);

        if (LKPropertiesLoader.testForm) {
            securityCodeView.setText("123456");
        }
    }

    /**
     * 请求获取短信验证码
     */
    private void invokeGetSmsSecurityCode() {
        beforeInvokeGetSmsSecurityCode();

        //请求参数
        GetSmsSecurityCodeIn in = new GetSmsSecurityCodeIn(cellphone);

        //创建请求对象
        final LKRetrofit<GetSmsSecurityCodeIn, GetSmsSecurityCodeOut> retrofit = new LKRetrofit<>(this, GetSmsSecurityCodeInvoker.class);

        //测试代码
        GetSmsSecurityCodeTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<GetSmsSecurityCodeIn, GetSmsSecurityCodeOut>() {

            @Override
            protected void success(Context context, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, GetSmsSecurityCodeOut responseDatas) {
                afterInvokeGetSmsSecurityCode(false);
            }

            @Override
            protected void busError(Context context, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, GetSmsSecurityCodeIn, errorCode, errorType, errorBean);
                afterInvokeGetSmsSecurityCode(true);
            }

            @Override
            public void connectError(Context context, String requestId, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, DialogInterface dialog) {
                super.connectError(context, requestId, GetSmsSecurityCodeIn, dialog);
                afterInvokeGetSmsSecurityCode(true);
            }

            @Override
            public void timeoutError(Context context, String requestId, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, GetSmsSecurityCodeIn, dialog);
                afterInvokeGetSmsSecurityCode(true);
            }

        });
    }

    /**
     * 开始请求获取短信验证码
     */
    private void beforeInvokeGetSmsSecurityCode() {
        btnResend.setClickable(false);
        btnResend.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    /** 验证码超时时长 */
    private static final int TIMEOUT = 60;
    int timeout = TIMEOUT;

    /**
     * 结束请求获取短信验证码
     */
    private void afterInvokeGetSmsSecurityCode(boolean error) {
        if (error) {
            btnResend.setClickable(true);
            btnResend.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);
            return;
        }
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnResend.setText(String.valueOf(timeout) + "s");
                        if (timeout == TIMEOUT) {
                            btnResend.setVisibility(View.VISIBLE);
                        }
                        if (timeout == 0) {
                            timer.cancel();
                            btnResend.setClickable(true);
                            btnResend.setText(R.string.sms_resend);
                            timeout = TIMEOUT;
                        }
                        timeout--;
                    }
                });
            }
        }, 0, 1000);
        loadingView.setVisibility(View.GONE);
    }

    /**
     * 请求快速登录
     */
    private void invokeFastLogin() {
        beforeInvokeFastLogin();

        //校验验证码
        String securityCode = securityCodeView.getText().toString();
        if (securityCode.length() != 6) {
            LKToast.showTip(R.string.login_sms_hint);
            afterInvokeFastLogin();
            return;
        }

        //请求参数
        final FastLoginIn in = new FastLoginIn(cellphone, securityCode);

        //创建请求对象
        final LKRetrofit<FastLoginIn, FastLoginOut> retrofit = new LKRetrofit<>(this, FastLoginInvoker.class);

        //测试代码
        FastLoginTester.cellphone = cellphone;
        FastLoginTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<FastLoginIn, FastLoginOut>() {

            @Override
            protected void success(Context context, FastLoginIn FastLoginIn, FastLoginOut responseDatas) {
                afterInvokeFastLogin();
                LKAndroidStatics.saveLoginInfo(responseDatas);
                if (responseDatas.isLogin()) {
                    //登录
                    SmsValidateActivity.this.setResult(MyFragment.RESULT_CODE_LOGINED);
                    SmsValidateActivity.this.finish();
                } else {
                    //注册
                    Intent intent = new Intent(SmsValidateActivity.this, SupplementRegisterInfoActivity.class);
                    intent.putExtra("cellphone", cellphone);
                    startActivityForResult(intent, MyFragment.REQUEST_CODE);
                }
            }

            @Override
            protected void busError(Context context, FastLoginIn FastLoginIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, FastLoginIn, errorCode, errorType, errorBean);
                afterInvokeFastLogin();
            }

            @Override
            public void connectError(Context context, String requestId, FastLoginIn FastLoginIn, DialogInterface dialog) {
                super.connectError(context, requestId, FastLoginIn, dialog);
                afterInvokeFastLogin();
            }

            @Override
            public void timeoutError(Context context, String requestId, FastLoginIn FastLoginIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, FastLoginIn, dialog);
                afterInvokeFastLogin();
            }

        });
    }

    /**
     * 开始请求快速登录
     */
    private void beforeInvokeFastLogin() {
        btnNextView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求快速登录
     */
    private void afterInvokeFastLogin() {
        btnNextView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MyFragment.REQUEST_CODE && resultCode == MyFragment.RESULT_CODE_LOGINED) {
            setResult(MyFragment.RESULT_CODE_LOGINED, data);
            finish();
        }
    }

}
