package com.lichkin.application.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.in.impl.SupplementRegisterInfoIn;
import com.lichkin.application.beans.out.impl.SupplementRegisterInfoOut;
import com.lichkin.application.fragments.MyFragment;
import com.lichkin.application.invokers.impl.SupplementRegisterInfoInvoker;
import com.lichkin.application.testers.SupplementRegisterInfoTester;
import com.lichkin.framework.app.android.LKAndroidStatics;
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
 * 补充注册信息
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class SupplementRegisterInfoActivity extends AppCompatActivity {

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

    /** 提交按钮 */
    @BindView(R.id.btn)
    Button btnSubmit;

    /** 提交按钮事件 */
    @OnClick(R.id.btn)
    void btnSubmitClick() {
        invokeSupplementRegisterInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_supplement_register);

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
     * 请求补充注册信息
     */
    private void invokeSupplementRegisterInfo() {
        beforeInvokeSupplementRegisterInfo();

        //校验用户名
        String loginName = loginNameView.getText().toString().trim();
        if ("".equals(loginName)) {
            LKToast.showTip(R.string.register_loginName_hint);
            afterInvokeSupplementRegisterInfo();
            return;
        }

        //校验密码
        String pwd = pwdView.getText().toString().trim();
        if ("".equals(pwd)) {
            LKToast.showTip(R.string.pwd_hint);
            afterInvokeSupplementRegisterInfo();
            return;
        }

        //请求参数
        final SupplementRegisterInfoIn in = new SupplementRegisterInfoIn(loginName, LKMD5.md5(pwd));

        //创建请求对象
        final LKRetrofit<SupplementRegisterInfoIn, SupplementRegisterInfoOut> retrofit = new LKRetrofit<>(this, SupplementRegisterInfoInvoker.class);

        //测试代码
        SupplementRegisterInfoTester.loginName = loginName;
        SupplementRegisterInfoTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<SupplementRegisterInfoIn, SupplementRegisterInfoOut>() {

            @Override
            protected void success(Context context, SupplementRegisterInfoIn SupplementRegisterInfoIn, SupplementRegisterInfoOut responseDatas) {
                afterInvokeSupplementRegisterInfo();
                LKAndroidStatics.saveLoginInfo(responseDatas);
                SupplementRegisterInfoActivity.this.setResult(MyFragment.RESULT_CODE_LOGINED);
                SupplementRegisterInfoActivity.this.finish();
            }

            @Override
            protected void busError(Context context, SupplementRegisterInfoIn SupplementRegisterInfoIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, SupplementRegisterInfoIn, errorCode, errorType, errorBean);
                afterInvokeSupplementRegisterInfo();
            }

            @Override
            public void connectError(Context context, String requestId, SupplementRegisterInfoIn SupplementRegisterInfoIn, DialogInterface dialog) {
                super.connectError(context, requestId, SupplementRegisterInfoIn, dialog);
                afterInvokeSupplementRegisterInfo();
            }

            @Override
            public void timeoutError(Context context, String requestId, SupplementRegisterInfoIn SupplementRegisterInfoIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, SupplementRegisterInfoIn, dialog);
                afterInvokeSupplementRegisterInfo();
            }

        });
    }

    /**
     * 开始请求补充注册信息
     */
    private void beforeInvokeSupplementRegisterInfo() {
        btnSubmit.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求补充注册信息
     */
    private void afterInvokeSupplementRegisterInfo() {
        btnSubmit.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

}
