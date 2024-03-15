package com.first.frame.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.common.ANRequest;
import com.first.frame.R;
import com.first.frame.http.HttpUtils;
import com.first.frame.http.ResponseListener;
import com.first.frame.utils.DeviceUtils;
import com.first.frame.utils.DialogUtils;
import com.first.frame.utils.ErrorUtil;
import com.first.frame.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class BaseActivity extends FragmentActivity implements EasyPermissions.PermissionCallbacks {

    public TextView titleTv;
    public ImageView leftImageView;
    public ImageView rightImageView;
    public View topView;
    protected Dialog progressDialog;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initTopView();
        initView();
        initData();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initTopView();
        initView();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void initView() {
    }

    protected void initData() {
    }

    public void initTopView() {
        leftImageView = findViewById(R.id.left);
        titleTv = findViewById(R.id.title);
        rightImageView = findViewById(R.id.right);
        topView = findViewById(R.id.top_view);
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void initListener() {

        if (leftImageView != null) {
            leftImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle("");
        if (titleTv != null) {
            titleTv.setText(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        setTitle("");
        if (titleId != 0) {
            titleTv.setText(titleId);
        }
    }

    public void toast(CharSequence message) {
        ToastUtils.show(this, message);
    }

    public void toast(int resId) {
        ToastUtils.show(this, resId);
    }

    public void toastTop(CharSequence message) {
        ToastUtils.showTop(this, message);
    }

    public void toastTop(int resId) {
        ToastUtils.showTop(this, resId);
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showProgressDialog(String msg) {
        if (TextUtils.isEmpty(msg)) {
            showProgressDialog();
            return;
        }
        dismissProgressDialog();
        progressDialog = DialogUtils.progress(this, msg);
    }

    public void showProgressDialog() {
        dismissProgressDialog();
        progressDialog = DialogUtils.progress(this);
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void showPermissionDeniedDialog(String functionDes, DialogInterface.OnClickListener cancelListener) {
        String msg = "在设置-应用-" + getString(R.string.app_name) + "-权限中开启权限，以正常使用" + functionDes;
        DialogUtils.dialogBuilder(this, "权限申请", msg,
                "去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeviceUtils.startAppSettings(BaseActivity.this);
                    }
                }, "取消", cancelListener);
    }

    public void requestPermissions(String rationale, int requestCode, String... perms) {
        if (EasyPermissions.hasPermissions(this, perms)) {
            onPermissionsGranted(requestCode, perms == null ? new ArrayList<String>() : Arrays.asList(perms));
        } else {
            if (TextUtils.isEmpty(rationale)) {
                ActivityCompat.requestPermissions(this, perms, requestCode);
            } else {
                EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, requestCode, perms)
                        .setRationale(rationale)
                        .setPositiveButtonText("确定")
                        .setNegativeButtonText("取消")
                        .build());
            }
        }
    }

    protected ANRequest sendRequest(String url, JSONObject params, Object... extra) {
        return sendRequest(url, params, "", extra);
    }

    protected ANRequest sendRequest(String url, JSONObject params, String dialogMsg, Object... extra) {
        showProgressDialog(dialogMsg);
        return HttpUtils.post(this, url, params, responseListener, extra);
    }

    protected ANRequest sendRequestGet(String url, HashMap<String, String> params, String dialogMsg, Object... extra) {
        showProgressDialog(dialogMsg);
        return HttpUtils.get(this, url, params, responseListener, extra);
    }

    protected ANRequest sendRequestObj(String url, Object object, String dialogMsg, Object... extra) {
        showProgressDialog(dialogMsg);
        return HttpUtils.postObj(this, url, object, responseListener, extra);
    }

    protected ANRequest sendRequestJsonObj(String url, JSONObject object, String dialogMsg, Object... extra) {
        showProgressDialog(dialogMsg);
        return HttpUtils.postJsonObj(this, url, object, responseListener, extra);
    }

    protected ANRequest sendRequestUpload(String url, HashMap<String, File> files, HashMap<String, String> params, String dialogMsg, Object... extra) {
        showProgressDialog(dialogMsg);
        return HttpUtils.upload(this, url, files, params, responseListener, extra);
    }

    protected ResponseListener responseListener = new ResponseListener() {
        @Override
        public void onSuccess(String url, String result, Object... extra) {
            dismissProgressDialog();
            onRequestSuccess(url, result, extra);
        }

        @Override
        public void onFailure(String url, String errorCode, String error, String result, Object... extra) {
            dismissProgressDialog();
            onRequestFailure(url, errorCode, error, result, extra);
        }
    };

    /**
     * 网络请求成功回调
     */
    public void onRequestSuccess(String url, String result, Object... extra) {

    }

    /**
     * 网络请求失败回调
     */
    public void onRequestFailure(String url, String errorCode, String error, String result, Object... extra) {
        ErrorUtil.handleError(this, errorCode, error);
    }

}
