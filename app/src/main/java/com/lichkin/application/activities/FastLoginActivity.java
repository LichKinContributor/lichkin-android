package com.lichkin.application.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.in.impl.GetSmsSecurityCodeIn;
import com.lichkin.application.beans.out.impl.GetSmsSecurityCodeOut;
import com.lichkin.application.fragments.MyFragment;
import com.lichkin.application.invokers.impl.GetSmsSecurityCodeInvoker;
import com.lichkin.application.testers.GetSmsSecurityCodeTester;
import com.lichkin.framework.app.android.activities.LKWebViewActivity;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 快速登录页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class FastLoginActivity extends AppCompatActivity {

    /** 登录按钮 */
    @BindView(R.id.btn_login)
    TextView btnLoginView;

    /** 登录按钮事件 */
    @OnClick(R.id.btn_login)
    void btnLoginClick() {
        startActivityForResult(new Intent(FastLoginActivity.this, AccountLoginActivity.class), MyFragment.REQUEST_CODE);
    }

    /** 下一步按钮 */
    @BindView(R.id.btn_next)
    TextView btnNextView;

    /** 下一步按钮事件 */
    @OnClick(R.id.btn_next)
    void btnNextClick() {
        invokeGetSmsSecurityCode();
    }

    /** 手机号码 */
    @BindView(R.id.cellphone)
    EditText cellphoneView;

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
            LKWebViewActivity.open(this.getBaseContext(), "file:///android_asset/test/test.html");
        } else {
            LKWebViewActivity.open(this.getBaseContext(), LKPropertiesLoader.pageBaseUrl + "/service_agreement" + LKPropertiesLoader.pageSuffix);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //引用布局文件
        setContentView(R.layout.activity_fast_login);

        //只使用竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //绑定页面
        ButterKnife.bind(this);

        //增加下划线
        btnAgreementView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        if (LKPropertiesLoader.testForm) {
            cellphoneView.setText("18501531550");
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
    }

    /**
     * 请求获取短信验证码
     */
    private void invokeGetSmsSecurityCode() {
        beforeInvokeGetSmsSecurityCode();

        //校验手机号码
        final String cellphone = cellphoneView.getText().toString();
        if (cellphone.length() != 11) {
            LKToast.showTip(R.string.login_cellphone_hint);
            afterInvokeGetSmsSecurityCode();
            return;
        }

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
                afterInvokeGetSmsSecurityCode();
                Intent intent = new Intent(FastLoginActivity.this, SmsValidateActivity.class);
                intent.putExtra("cellphone", cellphone);
                startActivityForResult(intent, MyFragment.REQUEST_CODE);
            }

            @Override
            protected void busError(Context context, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, GetSmsSecurityCodeIn, errorCode, errorType, errorBean);
                afterInvokeGetSmsSecurityCode();
            }

            @Override
            public void connectError(Context context, String requestId, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, DialogInterface dialog) {
                super.connectError(context, requestId, GetSmsSecurityCodeIn, dialog);
                afterInvokeGetSmsSecurityCode();
            }

            @Override
            public void timeoutError(Context context, String requestId, GetSmsSecurityCodeIn GetSmsSecurityCodeIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, GetSmsSecurityCodeIn, dialog);
                afterInvokeGetSmsSecurityCode();
            }

        });
    }

    /**
     * 开始请求获取短信验证码
     */
    private void beforeInvokeGetSmsSecurityCode() {
        btnNextView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求获取短信验证码
     */
    private void afterInvokeGetSmsSecurityCode() {
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
