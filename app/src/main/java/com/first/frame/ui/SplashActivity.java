package com.first.frame.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;

import com.first.frame.base.BaseActivity;
import com.first.frame.databinding.ActivitySplashBinding;

public class SplashActivity extends Activity {

    public ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        initView();
    }

    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (SPHttpUtils.getInstance().getBoolean(Constants.SP_KEY.IS_FIRST_ENTER_LOGIN, true)) {
//                    startActivity(new Intent(SplashActivity.this, GuildPageActivity.class));
//                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                }
                finish();
            }
        }, 3000);
    }

}
