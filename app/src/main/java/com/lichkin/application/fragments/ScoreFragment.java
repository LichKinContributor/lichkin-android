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
import android.widget.EditText;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.impl.in.ScoreIn;
import com.lichkin.application.beans.impl.out.ScoreOut;
import com.lichkin.application.invokers.impl.ScoreInvoker;
import com.lichkin.application.testers.ScoreTester;
import com.lichkin.framework.app.android.beans.LKScreen;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ScoreFragment extends DialogFragment {

    /** 评分 */
    private MaterialRatingBar scoreView;
    /** 标题 */
    private EditText titleView;
    /** 内容 */
    private EditText contentView;

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

        scoreView = view.findViewById(R.id.score);
        titleView = view.findViewById(R.id.title);
        contentView = view.findViewById(R.id.content);
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeScore();
            }
        });

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        scoreView.setRating(5.0f);
        titleView.setText("");
        contentView.setText("");
    }

    /**
     * 请求评分
     */
    private void invokeScore() {
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
                ScoreFragment.this.dismiss();
            }

        });
    }

}