package com.first.frame.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.first.frame.FrameApplication;
import com.first.frame.R;
import com.first.frame.http.HttpUtils;
import com.first.frame.http.ResponseListener;
import com.first.frame.utils.DeviceUtils;
import com.first.frame.utils.DialogUtils;
import com.first.frame.utils.ToastUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;


public abstract class BaseFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    protected Context mContext;

    private ImageView leftImage;
    private ImageView rightImage;
    private TextView titleTv;
    protected Dialog progressDialog;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();

        View rootView;

        rootView = inflater.inflate(getLayoutResId(), container, false);

        return rootView;
    }

    public boolean requestButterKnife() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        leftImage = view.findViewById(R.id.left);
        rightImage = view.findViewById(R.id.right);
        titleTv = view.findViewById(R.id.tv_title);
        initView(view);
        initData();
    }

    public abstract void initView(View view);

    public abstract int getLayoutResId();

    public void initData() {

    }

    public void setTitle(CharSequence title) {
        if (titleTv != null) {
            titleTv.setText(title);
        }
    }

    public void setTitle(int titleId) {
        if (titleId != 0) {
            titleTv.setText(titleId);
        }
    }

    public void showProgressDialog() {
        if (isSafeRunning()) {
            dismissProgressDialog();
            progressDialog = DialogUtils.progress(mContext);
        }
    }

    public void showProgressDialog(String msg) {
        if (isSafeRunning()) {
            dismissProgressDialog();
            progressDialog = DialogUtils.progress(mContext, msg);
        }
    }

    protected void dismissProgressDialog() {
        if (isSafeRunning() && progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        DialogUtils.dialogBuilder(mContext, "权限申请", msg,
                "去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeviceUtils.startAppSettings(mContext);
                    }
                }, "取消", cancelListener);
    }

    public void requestPermissions(String rationale, int requestCode, String... perms) {
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            List<String> list = (perms != null && perms.length > 0) ? Arrays.asList(perms) : new ArrayList<String>();
            onPermissionsGranted(requestCode, list);
        } else {
            EasyPermissions.requestPermissions(
                    new PermissionRequest.Builder(this, requestCode, perms)
                            .setRationale(rationale)
                            .setPositiveButtonText("确定")
                            .setNegativeButtonText("取消")
                            .build());
        }
    }

    protected void sendRequest(String url, JSONObject params, Object... extra) {
        sendRequest(url, params, "", extra);
    }

    protected void sendRequest(String url, JSONObject params, String dialogMsg, Object... extra) {
        if (!TextUtils.isEmpty(dialogMsg)) {
            showProgressDialog(dialogMsg);
        }
        HttpUtils.post(getContext(), url, params, responseListener, extra);
    }

    protected void sendRequestGet(String url, HashMap<String, String> params, String dialogMsg, Object... extra) {
        if (!TextUtils.isEmpty(dialogMsg)) {
            showProgressDialog(dialogMsg);
        }
        HttpUtils.get(getContext(), url, params, responseListener, extra);
    }

    public boolean isSafeRunning() {
        return getActivity() != null && !getActivity().isFinishing() && !getActivity().isDestroyed();
    }

    public void toast(CharSequence message) {
        if (isSafeRunning()) {
            ToastUtils.show(getActivity(), message);
        }
    }

    public void toast(int resId) {
        if (isSafeRunning()) {
            ToastUtils.show(getActivity(), resId);
        }
    }

    public void toastTop(CharSequence message) {
        if (isSafeRunning()) {
            ToastUtils.showTop(getActivity(), message);
        }
    }

    public void toastTop(int resId) {
        if (isSafeRunning()) {
            ToastUtils.showTop(getActivity(), resId);
        }
    }

    ResponseListener responseListener = new ResponseListener() {
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
    }

    public String getResourceString(int resId) {
        return FrameApplication.getContext().getString(resId);
    }

    public String getResourceString(int resId, Object... formatArgs) {
        return FrameApplication.getContext().getString(resId, formatArgs);
    }

    public int getColor(int id) {
        return FrameApplication.getContext().getResources().getColor(id);
    }

    public Drawable getDrawable(int id) {
        return FrameApplication.getContext().getResources().getDrawable(id);
    }
}
