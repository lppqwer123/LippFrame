package com.first.frame.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.first.frame.base.BaseActivity;
import com.first.frame.base.EventMessage;
import com.first.frame.databinding.ActivityMainBinding;
import com.first.frame.utils.FileDownLoadUtil;
import com.first.frame.utils.HotFixDexUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

public class MainActivity extends BaseActivity {

    public ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();
        binding.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,VideoPlayerActivity.class));
            }
        });

        binding.btnHotdex.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TestHotDexActivity.class));
            }
        });

//       checkUpdata();
    }

    public void checkUpdata(){

        String path = this.getFilesDir().getAbsolutePath() + File.separator + HotFixDexUtil.APK_SUFFIX;

        FileDownLoadUtil.get().download("downloadUrl", path, new FileDownLoadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                //成功
                Log.i("注意","下载成功");
            }

            @Override
            public void onDownloading(int progress) {
                //进度
                Log.i("注意",progress+"%");
            }

            @Override
            public void onDownloadFailed() {
                //失败
                Log.i("注意","下载失败");
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventMessage message) {
        toast(message.getData().toString());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
