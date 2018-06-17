package com.lichkin.application.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.in.impl.ScoreIn;
import com.lichkin.application.beans.out.impl.ScoreOut;
import com.lichkin.application.invokers.impl.ScoreInvoker;
import com.lichkin.application.testers.ScoreTester;
import com.lichkin.framework.app.android.beans.LKScreen;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 评分
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class ScoreFragment extends DialogFragment {

    private Unbinder unbinder;
    /** 评分 */
    @BindView(R.id.score)
    MaterialRatingBar scoreView;
    /** 标题 */
    @BindView(R.id.title)
    EditText titleView;
    /** 内容 */
    @BindView(R.id.content)
    EditText contentView;
    /** 加载 */
    @BindView(R.id.loading)
    AVLoadingIndicatorView loadingView;
    /** 按钮 */
    @BindView(R.id.btn)
    Button buttonView;

    /** 按钮事件 */
    @OnClick(R.id.btn)
    void btnClick() {
        invokeScore();
    }

    @Override
    public void onStart() {
        super.onStart();
        //点击外部不消失
        getDialog().setCanceledOnTouchOutside(false);
        //设置宽高
        Window window = getDialog().getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        LKScreen screen = LKAndroidUtils.getScreenDispaly();
        layoutParams.width = screen.getWidth() * 85 / 100;
        layoutParams.height = LKAndroidUtils.getPxValueByDpValue(330);
        window.setAttributes(layoutParams);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_fragment, container);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        scoreView.setRating(5.0f);
        titleView.setText("");
        contentView.setText("");
        buttonView.setEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 请求评分
     */
    private void invokeScore() {
        beforeInvokeScore();

        //请求参数
        ScoreIn in = new ScoreIn((byte) scoreView.getRating(), titleView.getText().toString(), contentView.getText().toString());

        //创建请求对象
        final LKRetrofit<ScoreIn, ScoreOut> retrofit = new LKRetrofit<>(this.getActivity(), ScoreInvoker.class);

        //测试代码
        ScoreTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<ScoreIn, ScoreOut>() {

            @Override
            protected void success(Context context, ScoreIn scoreIn, ScoreOut responseDatas) {
                LKToast.showTip(R.string.score_result);
                afterInvokeScore();
                ScoreFragment.this.dismiss();
            }

            @Override
            protected void busError(Context context, ScoreIn scoreIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, scoreIn, errorCode, errorType, errorBean);
                afterInvokeScore();
            }

            @Override
            public void connectError(Context context, String requestId, ScoreIn scoreIn, DialogInterface dialog) {
                super.connectError(context, requestId, scoreIn, dialog);
                afterInvokeScore();
            }

            @Override
            public void timeoutError(Context context, String requestId, ScoreIn scoreIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, scoreIn, dialog);
                afterInvokeScore();
            }

        });
    }

    /**
     * 开始请求评分
     */
    private void beforeInvokeScore() {
        buttonView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求评分
     */
    private void afterInvokeScore() {
        buttonView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

}
