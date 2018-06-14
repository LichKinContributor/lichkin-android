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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lichkin.app.android.demo.R;
import com.lichkin.application.beans.impl.in.FeedbackIn;
import com.lichkin.application.beans.impl.out.FeedbackOut;
import com.lichkin.application.invokers.impl.FeedbackInvoker;
import com.lichkin.application.testers.FeedbackTester;
import com.lichkin.framework.app.android.beans.LKScreen;
import com.lichkin.framework.app.android.callbacks.LKBtnCallback;
import com.lichkin.framework.app.android.callbacks.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKImageLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.app.android.widgets.LKDialog;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 反馈
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class FeedbackFragment extends DialogFragment implements TakePhoto.TakeResultListener, InvokeListener {

    private Unbinder unbinder;
    /** 内容 */
    @BindView(R.id.content)
    EditText contentView;
    /** 图片 */
    @BindView(R.id.img)
    ImageView imageView;

    @OnClick(R.id.img)
    void imgClick() {
        new LKDialog(FeedbackFragment.this.getContext(), R.string.feedback_choose_title).setPositiveButton(R.string.btn_positive_name_gallery, new LKBtnCallback() {
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

    /** 按钮 */
    @BindView(R.id.btn)
    Button buttonView;

    @OnClick(R.id.btn)
    void btnClick() {
        buttonView.setEnabled(false);
        if ("".equals(contentView.getText().toString().trim())) {
            LKToast.showTip(R.string.not_empty);
            contentView.setFocusable(true);
            buttonView.setEnabled(true);
            return;
        }
        invokeFeedback();
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
        layoutParams.height = LKAndroidUtils.getPxValueByDpValue(445);
        window.setAttributes(layoutParams);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_fragment, container);

        unbinder = ButterKnife.bind(this, view);

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
            this.dismiss();
            LKToast.showTip(R.string.can_not_use_storage);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        takePhoto.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePhoto.onActivityResult(requestCode, resultCode, data);
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
        LKImageLoader.load("file:///" + result.getImages().get(0).getCompressPath(), imageView);
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

}
