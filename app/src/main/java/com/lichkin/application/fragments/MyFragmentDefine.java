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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.activities.FastLoginActivity;
import com.lichkin.application.activities.MainActivity;
import com.lichkin.application.beans.in.impl.GetDynamicTabsIn;
import com.lichkin.application.beans.in.impl.PhotoUploadIn;
import com.lichkin.application.beans.in.impl.SignInIn;
import com.lichkin.application.beans.in.impl.TokenLoginIn;
import com.lichkin.application.beans.out.impl.PhotoUploadOut;
import com.lichkin.application.beans.out.impl.SignInOut;
import com.lichkin.application.beans.out.impl.TokenLoginOut;
import com.lichkin.application.invokers.impl.GetDynamicTabsInvoker;
import com.lichkin.application.invokers.impl.SignInInvoker;
import com.lichkin.application.invokers.impl.TokenLoginInvoker;
import com.lichkin.application.invokers.impl.UploadPhotoInvoker;
import com.lichkin.application.testers.GetDynamicTabsTester;
import com.lichkin.application.testers.PhotoUploadTester;
import com.lichkin.application.testers.SignInTester;
import com.lichkin.application.testers.TokenLoginTester;
import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.beans.LKDynamicTab;
import com.lichkin.framework.app.android.callbacks.LKBtnCallback;
import com.lichkin.framework.app.android.callbacks.LKCallback;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKBase64;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我的页面
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class MyFragmentDefine extends Fragment implements TakePhoto.TakeResultListener, InvokeListener {

    private Unbinder unbinder;

    /** 当前视图 */
    private View view;

    /** 登录按钮 */
    @BindView(R.id.btn_login)
    Button btn_login;

    /** 登录按钮事件 */
    @OnClick(R.id.btn_login)
    void btnLoginClick() {
        startActivityForResult(new Intent(getContext(), FastLoginActivity.class), REQUEST_CODE);
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        activity.overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
    }

    /** 登录后布局 */
    @BindView(R.id.afterLoginLayout)
    LinearLayout layout_afterLogin;

    /** 头像 */
    @BindView(R.id.photo)
    ImageView view_photo;

    /** 头像事件 */
    @OnClick(R.id.photo)
    void imgClick() {
        new LKDialog(MyFragmentDefine.this.getContext(), R.string.photo_choose_title).setPositiveButton(R.string.btn_positive_name_gallery, new LKBtnCallback() {
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
    TextView tx_loginName;

    /** 等级布局 */
    @BindView(R.id.layout_level)
    LinearLayout levelLayout;

    /** 等级布局事件 */
    @OnClick(R.id.layout_level)
    void levelLayoutClick() {
        DialogFragment dialogFragment = new LevelFragment();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) {
            return;
        }
        dialogFragment.show(fragmentManager, dialogFragment.getTag());
    }

    /** 签到按钮 */
    @BindView(R.id.btn_signIn)
    ImageView btn_signIn;

    /** 签到按钮事件 */
    @OnClick(R.id.btn_signIn)
    void btnSignInClick() {
        invokeSignIn();
    }

    /** 加载 */
    @BindView(R.id.loading)
    AVLoadingIndicatorView btn_signIn_loading;

    private boolean fromActivity = false;

    protected abstract int getFragmentId();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //填充页面
        view = inflater.inflate(getFragmentId(), container, false);

        unbinder = ButterKnife.bind(this, view);

        //初始化我的信息
        initMyInfo();

        //更新个人信息
        String token = LKAndroidStatics.token();
        if (token != null && !"".equals(token) && !fromActivity) {
            invokeTokenLogin();
        }

        fromActivity = false;

        //返回视图
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LKSharedPreferences.getBoolean("firstLaunchMy", true)) {
            LKSharedPreferences.putBoolean("firstLaunchMy", false);
            startActivityForResult(new Intent(getContext(), FastLoginActivity.class), REQUEST_CODE);
            FragmentActivity activity = getActivity();
            if (activity == null) {
                return;
            }
            activity.overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
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
        fromActivity = true;
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE_LOGINED) {
            initMyInfo();
            invokeGetDynamicTabs();
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
                LKAndroidStatics.saveLoginInfo(responseDatas);
                //初始化我的信息
                initMyInfo();
                invokeGetDynamicTabs();
            }

            @Override
            protected void busError(Context context, TokenLoginIn TokenLoginIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, TokenLoginIn, errorCode, errorType, errorBean);
                clearMyInfo();
                startActivityForResult(new Intent(getContext(), FastLoginActivity.class), REQUEST_CODE);
                FragmentActivity activity = getActivity();
                if (activity == null) {
                    return;
                }
                activity.overridePendingTransition(R.anim.pop_in, R.anim.pop_out);
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
            initMyInfoExtendsWhenNoToken();
            return;
        }

        initMyInfoExtends();

        levelLayout.removeAllViews();
        LevelFragment.inflateLevel(levelLayout, LKAndroidStatics.level());
        tx_loginName.setText(LKAndroidStatics.loginName());
        String photo = LKAndroidStatics.photo();
        if (photo != null && !"".equals(photo)) {
            if ("photo".equals(photo)) {
                view_photo.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.photo));
            } else {
                view_photo.setImageBitmap(LKBase64.toBitmap(photo));
            }
        }
        btn_login.setVisibility(View.GONE);
        layout_afterLogin.setVisibility(View.VISIBLE);
    }

    protected abstract void initMyInfoExtends();

    protected abstract void initMyInfoExtendsWhenNoToken();


    /**
     * 请求获取动态TAB页
     */
    private void invokeGetDynamicTabs() {
        //请求参数
        GetDynamicTabsIn in = new GetDynamicTabsIn();

        //创建请求对象
        final LKRetrofit<GetDynamicTabsIn, List<LKDynamicTab>> retrofit = new LKRetrofit<>(this.getContext(), GetDynamicTabsInvoker.class);

        //测试代码
        GetDynamicTabsTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<GetDynamicTabsIn, List<LKDynamicTab>>() {

            @Override
            protected void success(Context context, GetDynamicTabsIn GetDynamicTabsIn, List<LKDynamicTab> responseDatas) {
                MainActivity.activity.handleDynamicTabs(responseDatas);
            }

            @Override
            protected void busError(Context context, GetDynamicTabsIn GetDynamicTabsIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, GetDynamicTabsIn, errorCode, errorType, errorBean);
                clearMyInfo();
            }

            @Override
            public void connectError(Context context, String requestId, GetDynamicTabsIn GetDynamicTabsIn, DialogInterface dialog) {
                super.connectError(context, requestId, GetDynamicTabsIn, dialog);
                MainActivity.activity.handleDynamicTabs(null);
            }

            @Override
            public void timeoutError(Context context, String requestId, GetDynamicTabsIn GetDynamicTabsIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, GetDynamicTabsIn, dialog);
                MainActivity.activity.handleDynamicTabs(null);
            }

        });
    }

    protected LKCallback btnExitCallback = new LKCallback() {
        @Override
        public void call() {
            new LKDialog(MyFragmentDefine.this.getContext(), LKAndroidUtils.getString(R.string.title_exit) + "?").setPositiveButton(new LKBtnCallback() {
                @Override
                public void call(Context context, DialogInterface dialog) {
                    clearMyInfo();
                }
            }).setNegativeButton().show();
        }
    };

    /**
     * 清除我的信息
     */
    private void clearMyInfo() {
        LKAndroidStatics.saveLoginInfo(null);
        clearMyInfoExtends();
        levelLayout.removeAllViews();
        tx_loginName.setText("");
        view_photo.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.no_photo));
        btn_login.setVisibility(View.VISIBLE);
        layout_afterLogin.setVisibility(View.GONE);
        MainActivity.activity.handleDynamicTabs(null);
    }

    protected abstract void clearMyInfoExtends();

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
        btn_signIn.setVisibility(View.GONE);
        btn_signIn_loading.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求签到
     */
    private void afterInvokeSignIn() {
        btn_signIn.setVisibility(View.VISIBLE);
        btn_signIn_loading.setVisibility(View.GONE);
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
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
        //noinspection deprecation
        LKAndroidStatics.photo(LKBase64.toBase64(imgPath));
        LKImageLoader.load(imgPath, view_photo);
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
                .setMaxPixel(512)
                .enableReserveRaw(false)
                .create(), false);
        takePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setWithOwnGallery(false).create());
        CropOptions cropOptions = new CropOptions.Builder().setAspectX(512).setAspectY(512).setOutputX(512).setOutputY(512).setWithOwnCrop(false).create();
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
        final LKRetrofit<PhotoUploadIn, PhotoUploadOut> retrofit = new LKRetrofit<>(this.getActivity(), UploadPhotoInvoker.class);

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