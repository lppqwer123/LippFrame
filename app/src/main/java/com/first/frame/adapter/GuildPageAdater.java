package com.first.frame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.first.frame.FrameApplication;
import com.first.frame.R;
import com.first.frame.utils.GlideUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class GuildPageAdater extends PagerAdapter {

    public Context context;
    public List<String> imgs;

    public GuildPageAdater(Context context,List<String> imgs){
        this.context = context;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_img_vpage,null);
        ImageView guildImg =  view.findViewById(R.id.img_viewpager);
        GlideUtils.loadImage(FrameApplication.getContext(),imgs.get(position),guildImg);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        (container).removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
         return view==object;
    }

}
