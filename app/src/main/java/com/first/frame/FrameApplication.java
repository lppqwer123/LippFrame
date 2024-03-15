package com.first.frame;

import android.app.Application;
import android.content.Context;
import android.graphics.BitmapFactory;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.first.frame.utils.HotFixDexUtil;
import com.first.frame.utils.language.LanguageUtil;

import okhttp3.OkHttpClient;


public class FrameApplication extends Application {

    private static FrameApplication instance;
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static FrameApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
//        HotFixDexUtil.startRepair(getApplicationContext());//开启热修复
        mContext = getApplicationContext();
        instance = this;
//        initLanguage();
        initNetworking();

    }

    private void initLanguage() {
        LanguageUtil.init(this);
        LanguageUtil.initLanguage();
        LanguageUtil.getInstance().setConfiguration();
    }

    private void initNetworking() {
        OkHttpClient okHttpClient =
                new OkHttpClient().newBuilder().retryOnConnectionFailure(true).addInterceptor(new HttpLoggingInterceptor() {

                }).build();
        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        AndroidNetworking.setBitmapDecodeOptions(options);
        if (Config.SHOW_LOG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }
    }
}
