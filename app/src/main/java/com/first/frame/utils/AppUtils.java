package com.first.frame.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import androidx.core.content.FileProvider;

public class AppUtils {

    /**
     * 获取版本名称
     *
     * @param context 上下文
     * @return 版本名称
     */
    public static String getVersionName(Context context) {

        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static int getVersionCode(Context context) {

        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;

    }


    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 判断手机是否安装某个应用
     *
     * @param context
     * @param appPackageName 应用包名
     * @return true：安装，false：未安装
     */
    public static boolean isApplicationAvilible(Context context, String appPackageName) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (appPackageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param downloadUrl
     * @param savePath
     * @下载APK新版本
     */
    public static void downloadApp(final String downloadUrl, final String savePath) {

        new Thread() {
            public void run() {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + savePath);//记得加扩展名
                file.getParentFile().mkdir();
                try {
                    file.createNewFile();
                    URL url2 = new URL(downloadUrl);
                    HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                    conn.connect();
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream ips = conn.getInputStream();
                        FileOutputStream fops = new FileOutputStream(file);

                        byte[] buf = new byte[1024];
                        int read = ips.read(buf);
                        while (read != -1) {
                            fops.write(buf, 0, read);
                            fops.flush();
                            read = ips.read(buf);
                        }
                        fops.close();
                        ips.close();
                        conn.disconnect();
                    }
                } catch (Exception e) {
                }
            }
        }.start();

    }

    /**
     * @param apkPath
     * @安装新版本
     */
    public static void installNewApk(Activity activity, String apkPath) {
        String url = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + apkPath;
        Uri uri;
        Intent intent = new Intent(Intent.ACTION_VIEW);

        //支持7.0
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", new File(url));
        } else {
            uri = Uri.fromFile(new File(url));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        intent.setDataAndType(uri, "application/vnd.android.package-archive"); // 对应apk类型
        activity.getApplication().startActivity(intent);
    }

}
