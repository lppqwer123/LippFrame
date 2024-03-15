package com.first.frame.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.first.frame.base.BaseActivity;
import com.first.frame.base.EventMessage;
import com.first.frame.databinding.ActivityTestHotdexBinding;

import org.greenrobot.eventbus.EventBus;

import okhttp3.FormBody;

public class TestHotDexActivity extends BaseActivity {

    public ActivityTestHotdexBinding binding;
    static String str1 = "bug已修复，优秀！";
    static String str2 = "一个完美的bug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestHotdexBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        Bug(this);
        binding.textView.setText(Bug(this));

    }

    public static String Bug(Context context){
        Toast.makeText(context,str2,Toast.LENGTH_SHORT).show();

        return str2;
    }



}
