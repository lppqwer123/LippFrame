package com.first.frame.ui;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.first.frame.adapter.GuildPageAdater;
import com.first.frame.base.BaseActivity;
import com.first.frame.databinding.ActivityGuildPageBinding;
import com.first.frame.utils.Constants;
import com.first.frame.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class GuildPageActivity extends BaseActivity {

    public ActivityGuildPageBinding binding;
    public GuildPageAdater pageAdater;
    public List<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuildPageBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
    }

    @Override
    protected void initView() {
        super.initView();
        images = new ArrayList<>();
        pageAdater = new GuildPageAdater(this,images);
    }

    @Override
    protected void initData() {
        super.initData();
        binding.viewpager.setAdapter(pageAdater);
        SPUtils.getInstance().put(Constants.SP_KEY.IS_FIRST_ENTER_LOGIN,false);

    }
}
