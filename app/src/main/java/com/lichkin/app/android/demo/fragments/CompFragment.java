package com.lichkin.app.android.demo.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.lichkin.app.android.demo.R;
import com.lichkin.app.android.demo.activities.MainActivity;
import com.lichkin.application.activities.CompNewsActivity;
import com.lichkin.application.beans.in.impl.PunchTheClockIn;
import com.lichkin.application.beans.out.impl.PunchTheClockOut;
import com.lichkin.application.invokers.impl.PunchTheClockInvoker;
import com.lichkin.application.testers.PunchTheClockTester;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.beans.LKAMapLocation;
import com.lichkin.framework.app.android.beans.LKDynamicButton;
import com.lichkin.framework.app.android.callbacks.LKCallback;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.fragments.LKTabFragment;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKDynamicButtonUtils;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.app.android.widgets.LKDialog;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 公司页面
 */
public class CompFragment extends LKTabFragment {

    private Unbinder unbinder;

    /** 当前视图 */
    private View view;

    /** 加载遮罩 */
    @BindView(R.id.loading_mask)
    TextView loadingMaskView;

    /** 加载 */
    @BindView(R.id.loading)
    AVLoadingIndicatorView loadingView;

    /** 动态按钮 */
    @BindView(R.id.btns)
    LinearLayout btnsContainer;

    /** 按钮列表 */
    List<LKDynamicButton> btns;

    private static final int LOCATION_MAX_ACCURACY = 60;
    private AMapLocationClient aMapLocationClient;
    private LKAMapLocation location;
    private static int BTN_DIVIDE = 1;
    private static float BTN_ASPECT_RATIO = 16 / 6f;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //初始化定位
        aMapLocationClient = new AMapLocationClient(CompFragment.this.getContext());
        //监听定位
        aMapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation == null) {
                    return;
                }
                if (aMapLocation.getErrorCode() == 0) {
                    LKLog.i(String.format("location -> accuracy:[%s], longitude:[%s], latitude:[%s], altitude:[%s]", aMapLocation.getAccuracy(), aMapLocation.getLongitude(), aMapLocation.getLatitude(), aMapLocation.getAltitude()));
                    if (aMapLocation.getAccuracy() <= LOCATION_MAX_ACCURACY) {
                        aMapLocationClient.stopLocation();
                        location = new LKAMapLocation(aMapLocation);
                        LKLog.w(location.toString());
                        invokePunchTheClock();
                    }
                }
                location = null;
            }
        });
        btns = Arrays.asList(
                new LKDynamicButton(R.drawable.btn_comp_news, R.string.title_btn_comp_news, CompNewsActivity.class).addParam("compId", tabId),
                new LKDynamicButton(R.drawable.btn_approval, R.string.title_btn_approval, LKPropertiesLoader.pageTest ? "file:///android_asset/test/test.html" : LKAndroidStatics.activitiUrl()).addParam("compId", tabId),
                new LKDynamicButton(R.drawable.btn_punch_the_clock, R.string.title_btn_punch_the_clock, new LKCallback() {
                    @Override
                    public void call() {
                        if (!MainActivity.locationAuth) {
                            new LKDialog(CompFragment.this.getContext(), R.string.can_not_locate).show();
                            return;
                        }
                        new LKDialog(CompFragment.this.getContext(), String.format(LKAndroidUtils.getString(R.string.invoke_punch_the_clock_wait), LOCATION_MAX_ACCURACY)).show();
                        view.findViewWithTag("btn_punch_the_clock").setClickable(false);
                        loadingMaskView.setVisibility(View.VISIBLE);
                        loadingView.setVisibility(View.VISIBLE);
                        //启动定位
                        aMapLocationClient.startLocation();
                    }
                }).tag("btn_punch_the_clock")
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //填充页面
        view = inflater.inflate(R.layout.comp_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        btnsContainer.removeAllViews();
        LKDynamicButtonUtils.inflate(btnsContainer, btns, BTN_DIVIDE, BTN_ASPECT_RATIO);

        loadingMaskView.setHeight((int) (LKAndroidUtils.getScreenDispaly().getHeight() - LKAndroidUtils.getStatusBarHeight()));

        //返回视图
        return view;
    }

    /**
     * 请求打卡
     */
    private void invokePunchTheClock() {
        beforeInvokePunchTheClock();

        //请求参数
        PunchTheClockIn in = new PunchTheClockIn(tabId, location);

        //创建请求对象
        final LKRetrofit<PunchTheClockIn, PunchTheClockOut> retrofit = new LKRetrofit<>(this.getContext(), PunchTheClockInvoker.class);

        //测试代码
        PunchTheClockTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<PunchTheClockIn, PunchTheClockOut>() {

            @Override
            protected void success(Context context, PunchTheClockIn PunchTheClockIn, PunchTheClockOut responseDatas) {
                afterInvokePunchTheClock();
                LKToast.showTip(R.string.invoke_punch_the_clock_success);
            }

            @Override
            protected void busError(Context context, PunchTheClockIn PunchTheClockIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, PunchTheClockIn, errorCode, errorType, errorBean);
                afterInvokePunchTheClock();
                LKDialog.alert(CompFragment.this.getContext(), R.string.invoke_punch_the_clock_failed);
            }

            @Override
            public void connectError(Context context, String requestId, PunchTheClockIn PunchTheClockIn, DialogInterface dialog) {
                super.connectError(context, requestId, PunchTheClockIn, dialog);
                afterInvokePunchTheClock();
                LKDialog.alert(CompFragment.this.getContext(), R.string.invoke_punch_the_clock_failed);
            }

            @Override
            public void timeoutError(Context context, String requestId, PunchTheClockIn PunchTheClockIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, PunchTheClockIn, dialog);
                afterInvokePunchTheClock();
                LKDialog.alert(CompFragment.this.getContext(), R.string.invoke_punch_the_clock_failed);
            }

        });
    }

    /**
     * 开始请求打卡
     */
    private void beforeInvokePunchTheClock() {
    }

    /**
     * 结束请求打卡
     */
    private void afterInvokePunchTheClock() {
        view.findViewWithTag("btn_punch_the_clock").setClickable(true);
        loadingMaskView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }

}
