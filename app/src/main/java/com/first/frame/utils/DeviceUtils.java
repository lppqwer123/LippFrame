package com.first.frame.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;

import androidx.core.app.NotificationManagerCompat;

public class DeviceUtils {

    private DeviceUtils() {
        throw new AssertionError();
    }

    /**
     * 获取屏幕分辨率宽
     *
     * @param context
     * @return {宽，高}
     */
    public static int[] getResolution(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int mobileWidth = dm.widthPixels;
        int mobileHeight = dm.heightPixels;
        return new int[]{mobileWidth, mobileHeight};
    }

    private static final String FACEBOOK_PKGNAME = "com.facebook.katana";

    public static boolean checkFacebookInstalled(Context context) {
        return checkAppInstalled(context, FACEBOOK_PKGNAME);
    }

    private static boolean checkAppInstalled(Context context, String pkgName) {
        if (pkgName== null || pkgName.isEmpty()) {
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if(packageInfo == null) {
            return false;
        } else {
            return true;//true为安装了，false为未安装
        }
    }

    /**
     * 状态栏高度
     *
     * @return statusBarHeight 状态栏高
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);// 状态栏高度
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取标题栏高
     *
     * @return titleHeight:标题栏高
     */
    public static int getTitleHeight(Context context) {
        int titleHeight = 0;
        int contentTop = ((Activity) context).getWindow()
                .findViewById(Window.ID_ANDROID_CONTENT).getTop();
        titleHeight = contentTop - getStatusBarHeight(context);
        return titleHeight;
    }

    /**
     * 获取系统版本
     *
     * @return
     */
    public static String getDeviceName() {
        return "android" + Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 检查应用通知是否打开
     *
     * @param context
     * @return
     */
    public static boolean isNotificationEnabled(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //sdk 24.0.0 添加的api
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        }
        return false;
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    public static void hideSoftInput(Context context, View view) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        hideSoftInput(activity, view);
    }


    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setWindowStatusBarColor(Dialog dialog, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = dialog.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(dialog.getContext().getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  void setListViewHeight(ListView listView, BaseAdapter adapter){
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight() + 5;
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
            listView.setLayoutParams(params);
    }
}
