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
import com.lichkin.application.beans.impl.in.FeedbackIn;
import com.lichkin.application.beans.impl.out.FeedbackOut;
import com.lichkin.application.invokers.impl.FeedbackInvoker;
import com.lichkin.application.testers.FeedbackTester;
import com.lichkin.framework.app.android.beans.LKScreen;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;

/**
 * 反馈
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class FeedbackFragment extends DialogFragment {

    /** 内容 */
    private EditText contentView;
    /** 按钮 */
    private Button buttonView;

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
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_fragment, container);

        contentView = view.findViewById(R.id.content);
        buttonView = view.findViewById(R.id.btn);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonView.setEnabled(false);
                if ("".equals(contentView.getText().toString().trim())) {
                    LKToast.showTip(R.string.not_empty);
                    contentView.setFocusable(true);
                    buttonView.setEnabled(true);
                    return;
                }
                invokeFeedback();
            }
        });

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        contentView.setText("");
        buttonView.setEnabled(true);
    }

    /**
     * 请求反馈
     */
    private void invokeFeedback() {
        //请求参数
        FeedbackIn in = new FeedbackIn(contentView.getText().toString());

        //创建请求对象
        final LKRetrofit<FeedbackIn, FeedbackOut> retrofit = new LKRetrofit<>(this.getActivity(), FeedbackInvoker.class);

        //测试代码
        FeedbackTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<FeedbackIn, FeedbackOut>() {

            @Override
            protected void success(Context context, FeedbackIn FeedbackIn, FeedbackOut responseDatas) {
                LKToast.showTip(R.string.feedback_result);
                FeedbackFragment.this.dismiss();
            }

            @Override
            protected void busError(Context context, FeedbackIn feedbackIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, feedbackIn, errorCode, errorType, errorBean);
                buttonView.setEnabled(true);
            }

            @Override
            public void connectError(Context context, String requestId, FeedbackIn feedbackIn, DialogInterface dialog) {
                super.connectError(context, requestId, feedbackIn, dialog);
                buttonView.setEnabled(true);
            }

            @Override
            public void timeoutError(Context context, String requestId, FeedbackIn feedbackIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, feedbackIn, dialog);
                buttonView.setEnabled(true);
            }

        });
    }

}
