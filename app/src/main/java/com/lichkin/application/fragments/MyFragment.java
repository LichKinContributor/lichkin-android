package com.lichkin.application.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.activities.AboutActivity;
import com.lichkin.application.activities.FastLoginActivity;
import com.lichkin.application.beans.impl.in.PhotoUploadIn;
import com.lichkin.application.beans.impl.in.SignInIn;
import com.lichkin.application.beans.impl.in.TokenLoginIn;
import com.lichkin.application.beans.impl.out.PhotoUploadOut;
import com.lichkin.application.beans.impl.out.SignInOut;
import com.lichkin.application.beans.impl.out.TokenLoginOut;
import com.lichkin.application.invokers.impl.PhotoUploadInvoker;
import com.lichkin.application.invokers.impl.SignInInvoker;
import com.lichkin.application.invokers.impl.TokenLoginInvoker;
import com.lichkin.application.testers.PhotoUploadTester;
import com.lichkin.application.testers.SignInTester;
import com.lichkin.application.testers.TokenLoginTester;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.beans.LKDynamicButton;
import com.lichkin.framework.app.android.callbacks.LKBtnCallback;
import com.lichkin.framework.app.android.callbacks.LKCallback;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKBase64;
import com.lichkin.framework.app.android.utils.LKDynamicButtonUtils;
import com.lichkin.framework.app.android.utils.LKImageLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKSharedPreferences;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.app.android.widgets.LKDialog;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.wang.avi.AVLoadingIndicatorView;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.model.TakePhotoOptions;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我的页面
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class MyFragment extends Fragment implements TakePhoto.TakeResultListener, InvokeListener {

    private Unbinder unbinder;

    /** 当前视图 */
    private View view;

    /** 登录按钮 */
    @BindView(R.id.btn_login)
    Button btnLogin;

    /** 登录按钮事件 */
    @OnClick(R.id.btn_login)
    void btnLoginClick() {
        startActivityForResult(new Intent(getContext(), FastLoginActivity.class), REQUEST_CODE);
        getActivity().overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
    }

    /** 登录后布局 */
    @BindView(R.id.logined)
    LinearLayout loginedLayout;

    /** 头像 */
    @BindView(R.id.photo)
    ImageView photoView;

    /** 头像事件 */
    @OnClick(R.id.photo)
    void imgClick() {
        new LKDialog(MyFragment.this.getContext(), R.string.photo_choose_title).setPositiveButton(R.string.btn_positive_name_gallery, new LKBtnCallback() {
            @Override
            public void call(Context context, DialogInterface dialog) {
                takePhoto(true);
            }
        }).setNegativeButton(R.string.btn_negative_name_camera, new LKBtnCallback() {
            @Override
            public void call(Context context, DialogInterface dialog) {
                takePhoto(false);
            }
        }).show();
    }

    /** 用户名 */
    @BindView(R.id.loginName)
    TextView loginNameView;

    /** 等级布局 */
    @BindView(R.id.level)
    LinearLayout levelLayout;

    /** 等级布局事件 */
    @OnClick(R.id.level)
    void levelLayoutClick() {
        DialogFragment dialogFragment = new LevelFragment();
        dialogFragment.show(getFragmentManager(), dialogFragment.getTag());
    }

    /** 签到按钮 */
    @BindView(R.id.btn_ssignIn)
    ImageView btnSignIn;

    /** 签到按钮事件 */
    @OnClick(R.id.btn_ssignIn)
    void btnSignInClick() {
        invokeSignIn();
    }

    /** 加载 */
    @BindView(R.id.loading)
    AVLoadingIndicatorView loadingView;

    /** 动态按钮 */
    @BindView(R.id.btns)
    LinearLayout btnsContainer;
    List<LKDynamicButton> btns = new ArrayList<>();
    private boolean loginedBtnAdded = false;
    private static int BTN_DIVIDE = 4;
    private static float BTN_ASPECT_RATIO = 1.0f;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //填充页面
        view = inflater.inflate(R.layout.my_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        //初始化按钮栏
        btns.add(new LKDynamicButton(R.drawable.btn_score, R.string.title_score, new ScoreFragment(), getFragmentManager()));
        btns.add(new LKDynamicButton(R.drawable.btn_feedback, R.string.title_feedback, new FeedbackFragment(), getFragmentManager()));
        btns.add(new LKDynamicButton(R.drawable.btn_about, R.string.title_about, AboutActivity.class));
        LKDynamicButtonUtils.inflate(btnsContainer, btns, BTN_DIVIDE, BTN_ASPECT_RATIO);

        //请求令牌登录
        String token = LKAndroidStatics.token();
        if (token != null && !"".equals(token)) {
            invokeTokenLogin();
        }

        //返回视图
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LKSharedPreferences.getBoolean("firstLaunchMy", true)) {
            LKSharedPreferences.putBoolean("firstLaunchMy", false);
            startActivityForResult(new Intent(getContext(), FastLoginActivity.class), REQUEST_CODE);
            getActivity().overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public static final int REQUEST_CODE = 0x00000001;
    public static final int RESULT_CODE_LOGINED = 0x00000001;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE_LOGINED) {
            initMyInfo();
        }
        takePhoto.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 请求令牌登录
     */
    private void invokeTokenLogin() {
        //请求参数
        TokenLoginIn in = new TokenLoginIn();

        //创建请求对象
        final LKRetrofit<TokenLoginIn, TokenLoginOut> retrofit = new LKRetrofit<>(this.getContext(), TokenLoginInvoker.class);

        //测试代码
        TokenLoginTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<TokenLoginIn, TokenLoginOut>() {

            @Override
            protected void success(Context context, TokenLoginIn TokenLoginIn, TokenLoginOut responseDatas) {
                LKAndroidStatics.level(responseDatas.getLevel());
                LKAndroidStatics.photo(responseDatas.getPhoto());
                LKAndroidStatics.securityCenterUrl(responseDatas.getSecurityCenterUrl());
                //初始化我的信息
                initMyInfo();
            }

            @Override
            protected void busError(Context context, TokenLoginIn TokenLoginIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, TokenLoginIn, errorCode, errorType, errorBean);
                clearMyInfo();
                startActivityForResult(new Intent(getContext(), FastLoginActivity.class), REQUEST_CODE);
                getActivity().overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
            }

            @Override
            public void connectError(Context context, String requestId, TokenLoginIn TokenLoginIn, DialogInterface dialog) {
                super.connectError(context, requestId, TokenLoginIn, dialog);
            }

            @Override
            public void timeoutError(Context context, String requestId, TokenLoginIn TokenLoginIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, TokenLoginIn, dialog);
            }

        });
    }

    /**
     * 初始化我的信息
     */
    private void initMyInfo() {
        String token = LKAndroidStatics.token();
        if (token == null || "".equals(token)) {
            return;
        }
        loginNameView.setText(LKAndroidStatics.loginName());
        levelLayout.removeAllViews();
        LevelFragment.inflateLevel(levelLayout, LKAndroidStatics.level());
        String photo = LKAndroidStatics.photo();
        if (photo != null && !"".equals(photo)) {
            if ("photo".equals(photo)) {
                photoView.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.photo));
            } else {
                photoView.setImageBitmap(LKBase64.toBitmap(photo));
            }
        }
        btnLogin.setVisibility(View.GONE);
        loginedLayout.setVisibility(View.VISIBLE);

        if (loginedBtnAdded) {
            return;
        }
        loginedBtnAdded = true;
        btnsContainer.removeAllViews();
        btns.add(new LKDynamicButton(R.drawable.btn_security_center, R.string.title_security_center, LKAndroidStatics.securityCenterUrl()));
        btns.add(new LKDynamicButton(R.drawable.btn_exit, R.string.title_exit, new LKCallback() {
            @Override
            public void call() {
                clearMyInfo();
            }
        }));
        LKDynamicButtonUtils.inflate(btnsContainer, btns, BTN_DIVIDE, BTN_ASPECT_RATIO);
    }

    /**
     * 清除我的信息
     */
    private void clearMyInfo() {
        LKAndroidStatics.token(null);
        LKAndroidStatics.loginName(null);
        loginNameView.setText("");
        LKAndroidStatics.level(1);
        levelLayout.removeAllViews();
        LKAndroidStatics.photo(null);
        photoView.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.no_photo));
        LKAndroidStatics.securityCenterUrl(null);
        loginedBtnAdded = false;
        btnLogin.setVisibility(View.VISIBLE);
        loginedLayout.setVisibility(View.GONE);
        loginedBtnAdded = false;
        btnsContainer.removeAllViews();
        for (int i = btns.size() - 1; i >= 0; i--) {
            LKDynamicButton btn = btns.get(i);
            if (btn.getBtnImgResId() == R.drawable.btn_security_center || btn.getBtnImgResId() == R.drawable.btn_exit) {
                btns.remove(i);
            }
        }
        LKDynamicButtonUtils.inflate(btnsContainer, btns, BTN_DIVIDE, BTN_ASPECT_RATIO);
    }

    /**
     * 请求签到
     */
    private void invokeSignIn() {
        beforeInvokeSignIn();

        //请求参数
        SignInIn in = new SignInIn();

        //创建请求对象
        final LKRetrofit<SignInIn, SignInOut> retrofit = new LKRetrofit<>(this.getContext(), SignInInvoker.class);

        //测试代码
        SignInTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<SignInIn, SignInOut>() {

            @Override
            protected void success(Context context, SignInIn SignInIn, SignInOut responseDatas) {
                afterInvokeSignIn();
                LKToast.showTip(R.string.sign_in_success);
            }

            @Override
            protected void busError(Context context, SignInIn SignInIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, SignInIn, errorCode, errorType, errorBean);
                afterInvokeSignIn();
            }

            @Override
            public void connectError(Context context, String requestId, SignInIn SignInIn, DialogInterface dialog) {
                super.connectError(context, requestId, SignInIn, dialog);
                afterInvokeSignIn();
            }

            @Override
            public void timeoutError(Context context, String requestId, SignInIn SignInIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, SignInIn, dialog);
                afterInvokeSignIn();
            }

        });
    }

    /**
     * 开始请求签到
     */
    private void beforeInvokeSignIn() {
        btnSignIn.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求签到
     */
    private void afterInvokeSignIn() {
        btnSignIn.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private String imgDir = Environment.getExternalStorageDirectory() + "/temp";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        takePhoto.onCreate(savedInstanceState);
        File imgDirFile = new File(imgDir);
        if (!imgDirFile.exists() && !imgDirFile.mkdirs()) {
            LKToast.showTip(R.string.can_not_use_storage);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        takePhoto.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {
        String imgPath = "file:///" + result.getImage().getCompressPath();
        LKAndroidStatics.photo(LKBase64.toBase64(imgPath));
        LKImageLoader.load(imgPath, photoView);
        invokePhotoUpload();
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    /**
     * 调用取照片
     * @param fromGralley true:相册;false:拍照.
     */
    private void takePhoto(boolean fromGralley) {
        Uri uri = Uri.fromFile(new File(imgDir, "/" + System.currentTimeMillis() + ".jpg"));
        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(102400)
                .setMaxPixel(256)
                .enableReserveRaw(false)
                .create(), false);
        takePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setWithOwnGallery(false).create());
        CropOptions cropOptions = new CropOptions.Builder().setOutputX(256).setOutputY(256).setWithOwnCrop(false).create();
        if (fromGralley) {
            takePhoto.onPickFromGalleryWithCrop(uri, cropOptions);
        } else {
            takePhoto.onPickFromCaptureWithCrop(uri, cropOptions);
        }
    }

    /**
     * 请求上传头像
     */
    private void invokePhotoUpload() {
        //请求参数
        PhotoUploadIn in = new PhotoUploadIn(LKAndroidStatics.photo());

        //创建请求对象
        final LKRetrofit<PhotoUploadIn, PhotoUploadOut> retrofit = new LKRetrofit<>(this.getActivity(), PhotoUploadInvoker.class);

        //测试代码
        PhotoUploadTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<PhotoUploadIn, PhotoUploadOut>() {

            @Override
            protected void success(Context context, PhotoUploadIn PhotoUploadIn, PhotoUploadOut responseDatas) {
            }

            @Override
            protected void busError(Context context, PhotoUploadIn PhotoUploadIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
            }

            @Override
            public void connectError(Context context, String requestId, PhotoUploadIn PhotoUploadIn, DialogInterface dialog) {
            }

            @Override
            public void timeoutError(Context context, String requestId, PhotoUploadIn PhotoUploadIn, DialogInterface dialog) {
            }

        });
    }

}