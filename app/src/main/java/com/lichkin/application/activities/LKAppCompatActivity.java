package com.lichkin.application.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKSharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础Acitivity功能实现类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class LKAppCompatActivity extends AppCompatActivity {

    /** 权限列表 */
    private List<String> permissionNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化静态值
        initStatics();
    }

    @Override
    @Deprecated
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //获取权限名
        String permissionName = null;
        if (requestCode < permissionNames.size()) {
            permissionName = permissionNames.get(requestCode);
        }

        if (permissionName == null) {
            throw new IllegalArgumentException("can't find permission by code " + requestCode + ", you must invoke dontNeedRequestPermission to judge permissions.");
        }

        LKLog.d("onRequestPermissionsResult -> permissionName=" + permissionName);

        if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            LKLog.d("onRequestPermissionsResult -> requestPermissionName=" + permissionName + " granted.");
            //授权通过
            onRequestPermissionResultGranted(permissionName);
        } else {
            LKLog.d("onRequestPermissionsResult -> requestPermissionName=" + permissionName + " not granted.");
            //授权未通过
            onRequestPermissionResultNotGranted(permissionName);
        }
    }

    /**
     * 不需要申请权限
     * @param permissionName 权限名。Manifest.permission.XXX。
     * @return 不需要申请权限返回true，判断后直接实现具体内容即可；需要申请权限返回false，需要在回调方法中实现具体内容。
     */
    @SuppressLint("Range")
    protected boolean dontNeedRequestPermission(String permissionName) {
        //获取权限索引值
        int idx = -1;
        for (int i = 0; i < permissionNames.size(); i++) {
            if (permissionNames.get(i).equals(permissionName)) {
                idx = i;
                break;
            }
        }

        //没有该权限则增加该权限
        if (idx == -1) {
            permissionNames.add(permissionName);
            idx = permissionNames.size() - 1;
        }

        //校验权限
        boolean checked = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (LKAndroidStatics.targetSdkVersion() >= Build.VERSION_CODES.M) {
                checked = this.checkSelfPermission(permissionName) == PackageManager.PERMISSION_GRANTED;
            } else {
                checked = PermissionChecker.checkSelfPermission(this, permissionName) == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        if (!checked) {
            LKLog.w("need requestPermission -> " + permissionName);
            //权限没有被授权，则申请该权限。
            ActivityCompat.requestPermissions(this, new String[]{permissionName}, idx);
            return false;
        }

        //不需要申请权限
        LKLog.i("don't need requestPermission -> " + permissionName);
        return true;
    }

    /**
     * 授权通过
     * @param permissionName 权限名
     */
    protected void onRequestPermissionResultGranted(String permissionName) {
        LKLog.i("onRequestPermissionResultGranted -> " + permissionName);
    }

    /**
     * 授权不通过
     * @param permissionName 权限名
     */
    protected void onRequestPermissionResultNotGranted(String permissionName) {
        LKLog.w("onRequestPermissionResultNotGranted -> " + permissionName);
    }

    /**
     * 初始化静态值
     */
    @SuppressWarnings("deprecation")
    private void initStatics() {
        String packageName = this.getPackageName();
        //客户端唯一标识
        LKAndroidStatics.appKey(packageName);

        PackageManager packageManager = this.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);

            //客户端SDK版本
            LKAndroidStatics.targetSdkVersion(packageInfo.applicationInfo.targetSdkVersion);

            //客户端版本号
            String[] versionArr = packageInfo.versionName.split("\\.");
            LKAndroidStatics.versionX(Byte.parseByte(versionArr[0]));
            LKAndroidStatics.versionY(Byte.parseByte(versionArr[1]));
            LKAndroidStatics.versionZ(Short.parseShort(versionArr[2]));
        } catch (PackageManager.NameNotFoundException e) {
            //客户端SDK版本
            LKAndroidStatics.targetSdkVersion(-1);

            //客户端版本号
            LKAndroidStatics.versionX((byte) 1);
            LKAndroidStatics.versionY((byte) 0);
            LKAndroidStatics.versionZ((short) 0);
        }

        //令牌
        LKAndroidStatics.token(LKSharedPreferences.getString(LKSharedPreferences.TOKEN, ""));

        //屏幕宽高
        Object wm = this.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) wm).getDefaultDisplay().getMetrics(dm);
            LKAndroidStatics.screenWidth((short) dm.widthPixels);
            LKAndroidStatics.screenHeight((short) dm.heightPixels);
        } else {
            LKAndroidStatics.screenWidth((short) -1);
            LKAndroidStatics.screenHeight((short) -1);
        }
    }

}
