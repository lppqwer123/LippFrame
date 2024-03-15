package com.first.frame.ui;

import android.os.Bundle;
import android.view.View;

import com.first.frame.databinding.ActivityLoginBinding;
import com.first.frame.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    public ActivityLoginBinding binding;
    public String name, pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    protected void initView() {
        super.initView();
        name = binding.etName.getText().toString();
        pwd = binding.etOperPwd.getText().toString();
        binding.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }


    @Override
    public void onRequestSuccess(String url, String result, Object... extra) {
        super.onRequestSuccess(url, result, extra);

    }
}