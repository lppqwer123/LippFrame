package com.first.frame.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

import com.first.frame.base.BaseActivity;
import com.first.frame.databinding.ActivityVideoPlayerBinding;
import com.first.frame.utils.video.Player;


public class VideoPlayerActivity extends BaseActivity {

    public ActivityVideoPlayerBinding binding;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoPlayerBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        binding.btnPlayUrl.setOnClickListener(new ClickEvent());

        binding.btnPause.setOnClickListener(new ClickEvent());

        binding.btnStop.setOnClickListener(new ClickEvent());

        binding.skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());

        player = new Player(binding.surfaceView, binding.skbProgress);

    }

    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            if (arg0 == binding.btnPause) {
                Log.i("LIPPPPP","binding.btnPause");
                toast("LIPP /// binding.btnPause");
                player.pause();
            } else if (arg0 == binding.btnPlayUrl) {
                Log.i("LIPPPPP","binding.btnPlayUrl");
                toast("LIPP /// binding.btnPlayUrl");
                String url = "https://v6.huanqiucdn.cn/4394989evodtranscq1500012236/73ba06d51397757887063447712/v.f100830.mp4";
                player.playUrl(url);
            } else if (arg0 == binding.btnStop) {
                Log.i("LIPPPPP","binding.btnStop");
                toast("LIPP /// binding.btnStop");
                player.stop();
            }

        }
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration() / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }
    }


}
