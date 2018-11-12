package com.lichkin.application.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.in.impl.AccountLoginIn;
import com.lichkin.application.beans.out.impl.AccountLoginOut;
import com.lichkin.application.fragments.MyFragmentDefine;
import com.lichkin.application.invokers.impl.AccountLoginInvoker;
import com.lichkin.application.testers.AccountLoginTester;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.activities.LKWebViewActivity;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKMD5;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账号登录页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class AccountLoginActivity extends AppCompatActivity {

    /** 快速登录按钮 */
    @BindView(R.id.btn_fast_login)
    TextView btnFastLoginView;

    /** 快速登录按钮事件 */
    @OnClick(R.id.btn_fast_login)
    void btnFastLoginClick() {
        finish();
    }

    /** 是否显示密码 */
    private boolean eyeClosed = true;

    /** 用户名 */
    @BindView(R.id.loginName)
    EditText loginNameView;

    /** 密码 */
    @BindView(R.id.pwd)
    EditText pwdView;

    /** 密码状态控制按钮 */
    @BindView(R.id.btn_pwd_switcher)
    ImageView btnPwdSwicher;

    /** 密码状态控制按钮事件 */
    @OnClick(R.id.btn_pwd_switcher)
    void btnPasswordSwicherClick() {
        if (eyeClosed) {
            btnPwdSwicher.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.eye_opend));
            pwdView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            btnPwdSwicher.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.eye_closed));
            pwdView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        pwdView.setSelection(pwdView.getText().toString().length());
        eyeClosed = !eyeClosed;
    }

    /** 加载 */
    @BindView(R.id.loading)
    AVLoadingIndicatorView loadingView;

    /** 下一步按钮 */
    @BindView(R.id.btn_next)
    TextView btnNextView;

    /** 下一步按钮事件 */
    @OnClick(R.id.btn_next)
    void btnNextClick() {
        invokeAccountLogin();
    }

    /** 服务协议按钮 */
    @BindView(R.id.btn_agreement)
    TextView btnAgreementView;

    /** 服务协议按钮事件 */
    @OnClick(R.id.btn_agreement)
    void btnAgreementClick() {
        if (LKPropertiesLoader.pageTest) {
            LKWebViewActivity.open(this.getBaseContext(), "file:///android_asset/test/test.html");
        } else {
            LKWebViewActivity.open(this.getBaseContext(), LKPropertiesLoader.pageBaseUrl + "/serviceAgreement" + LKPropertiesLoader.pageSuffix);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_account_login);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //绑定页面
        ButterKnife.bind(this);

        if (LKPropertiesLoader.testForm) {
            loginNameView.setText("LunaDream");
            pwdView.setText("abcd1234");
        }
    }

    /**
     * 请求账号登录
     */
    private void invokeAccountLogin() {
        beforeInvokeAccountLogin();

        //校验用户名
        String loginName = loginNameView.getText().toString().trim();
        if ("".equals(loginName)) {
            LKToast.showTip(R.string.login_loginName_hint);
            afterInvokeAccountLogin();
            return;
        }

        //校验密码
        String pwd = pwdView.getText().toString().trim();
        if ("".equals(pwd)) {
            LKToast.showTip(R.string.pwd_hint);
            afterInvokeAccountLogin();
            return;
        }

        //请求参数
        AccountLoginIn in = new AccountLoginIn(loginName, LKMD5.md5(pwd));

        //创建请求对象
        final LKRetrofit<AccountLoginIn, AccountLoginOut> retrofit = new LKRetrofit<>(this, AccountLoginInvoker.class);

        //测试代码
        AccountLoginTester.loginName = loginName;
        AccountLoginTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<AccountLoginIn, AccountLoginOut>() {

            @Override
            protected void success(Context context, AccountLoginIn AccountLoginIn, AccountLoginOut responseDatas) {
                afterInvokeAccountLogin();
                LKAndroidStatics.saveLoginInfo(responseDatas);
                AccountLoginActivity.this.setResult(MyFragmentDefine.RESULT_CODE_LOGINED);
                AccountLoginActivity.this.finish();
            }

            @Override
            protected void busError(Context context, AccountLoginIn AccountLoginIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, AccountLoginIn, errorCode, errorType, errorBean);
                afterInvokeAccountLogin();
            }

            @Override
            public void connectError(Context context, String requestId, AccountLoginIn AccountLoginIn, DialogInterface dialog) {
                super.connectError(context, requestId, AccountLoginIn, dialog);
                afterInvokeAccountLogin();
            }

            @Override
            public void timeoutError(Context context, String requestId, AccountLoginIn AccountLoginIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, AccountLoginIn, dialog);
                afterInvokeAccountLogin();
            }

        });
    }

    /**
     * 开始请求账号登录
     */
    private void beforeInvokeAccountLogin() {
        btnNextView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求账号登录
     */
    private void afterInvokeAccountLogin() {
        btnNextView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

}
