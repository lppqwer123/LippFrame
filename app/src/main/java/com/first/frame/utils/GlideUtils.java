package com.first.frame.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;

public class GlideUtils {

    public static RequestOptions getOptions(Context context, int round) {
        RequestOptions options = new RequestOptions();
        options.skipMemoryCache(true);
        options.diskCacheStrategy(DiskCacheStrategy.DATA);
        if (round > 0) {
            options.transform(new GlideRoundTransform(context, round));
        }
        return options;
    }

    public static void loadImage(Context context, String path, ImageView iv) {
        RequestOptions options = getOptions(context, 0);
        Glide.with(context).load(path).apply(options).thumbnail(0.1f).into(iv);
    }
    public static void loadOssImage(Context context, String path, ImageView iv) {
        RequestOptions options = getOptions(context, 0);
        if (!TextUtils.isEmpty(path)) {
            if (path.startsWith("http")) {
                Glide.with(context).load(path)
                        .apply(options).thumbnail(1.0f).into(iv);
            } else if (path.startsWith("/storage") || path.startsWith("storage") || path.startsWith(Environment.getRootDirectory().getAbsolutePath())) {
                Glide.with(context).load(path)
                        .apply(options).thumbnail(1.0f).into(iv);
            } else {
                String baseUrl = SPUtils.getInstance().getString(Constants.SP_KEY.KEY_IMAGE_BASE);
                if (!TextUtils.isEmpty(baseUrl)) {
                    String imageUrl = "";
                    if (baseUrl.endsWith("/") && path.startsWith("/")) {
                        imageUrl = baseUrl + path.substring(1);
                    } else if (!baseUrl.endsWith("/") && !path.startsWith("/")) {
                        imageUrl = baseUrl + "/" + path;
                    } else {
                        imageUrl = baseUrl + path;
                    }
                    Glide.with(context).load(imageUrl)
                            .apply(options).thumbnail(1.0f).into(iv);
                } else {
                    Glide.with(context).load(path)
                            .apply(options).thumbnail(1.0f).into(iv);
                }
            }
        }
    }
    public static void loadOssImage(Context context, String path, ImageView iv, int size) {
        RequestOptions options = getOptions(context, 0);

        if (!TextUtils.isEmpty(path)) {
            if (path.startsWith("http")) {
                path = path + "?x-oss-process=style/thumb_" + size;
                Glide.with(context).load(path)
                        .apply(options).thumbnail(1.0f).into(iv);
            } else if (path.startsWith("/storage") || path.startsWith("storage") || path.startsWith(Environment.getRootDirectory().getAbsolutePath())) {
                Glide.with(context).load(path)
                        .apply(options).thumbnail(1.0f).into(iv);
            } else {
                path = path + "?x-oss-process=style/thumb_" + size;

                String baseUrl = SPUtils.getInstance().getString(Constants.SP_KEY.KEY_IMAGE_BASE);
                if (!TextUtils.isEmpty(baseUrl)) {
                    String imageUrl = "";
                    if (baseUrl.endsWith("/") && path.startsWith("/")) {
                        imageUrl = baseUrl + path.substring(1);
                    } else if (!baseUrl.endsWith("/") && !path.startsWith("/")) {
                        imageUrl = baseUrl + "/" + path;
                    } else {
                        imageUrl = baseUrl + path;
                    }
                    LogUtils.d("imagepath:" + imageUrl);
                    Glide.with(context).load(imageUrl)
                            .apply(options).thumbnail(1.0f).into(iv);
                } else {
                    Glide.with(context).load(path)
                            .apply(options).thumbnail(1.0f).into(iv);
                }
            }
        }
    }
    public static void loadOssImage(Context context, String path, CustomTarget<Bitmap> target) {
        RequestOptions options = getOptions(context, 0);
        if (!TextUtils.isEmpty(path)) {
            if (path.startsWith("http")) {
                Glide.with(context).asBitmap().load(path)
                        .apply(options).thumbnail(1.0f).into(target);
            } else if (path.startsWith("/storage") || path.startsWith("storage") || path.startsWith(Environment.getRootDirectory().getAbsolutePath())) {
                Glide.with(context).asBitmap().load(path)
                        .apply(options).thumbnail(1.0f).into(target);
            } else {
                String baseUrl = SPUtils.getInstance().getString(Constants.SP_KEY.KEY_IMAGE_BASE);
                if (!TextUtils.isEmpty(baseUrl)) {
                    String imageUrl = "";
                    if (baseUrl.endsWith("/") && path.startsWith("/")) {
                        imageUrl = baseUrl + path.substring(1);
                    } else if (!baseUrl.endsWith("/") && !path.startsWith("/")) {
                        imageUrl = baseUrl + "/" + path;
                    } else {
                        imageUrl = baseUrl + path;
                    }
//                    imageUrl = imageUrl + "?x-oss-process=style/original";
                    LogUtils.d("imagepath:" + imageUrl);
                    Glide.with(context).asBitmap().load(imageUrl)
                            .apply(options).thumbnail(1.0f).into(target);
                } else {
                    Glide.with(context).asBitmap().load(path)
                            .apply(options).thumbnail(1.0f).into(target);
                }
            }
        }
    }

    public static void loadImage(Context context, String path, ImageView iv, int placeResId) {
        RequestOptions options = getOptions(context, 0);
        if (placeResId > 0) {
            options.placeholder(placeResId);
        }
        Glide.with(context).load(path).apply(options).thumbnail(0.1f).into(iv);
    }

    public static void loadImageRound(Context context, String path, ImageView iv, int round, int placeResId) {
        RequestOptions options = getOptions(context, round);
        if (placeResId > 0) {
            options.placeholder(placeResId);
        }
        Glide.with(context).load(path).apply(options).into(iv);
    }

    public static void loadImageCircle(Context context, String path, ImageView iv, int placeResId) {
        RequestOptions options = getOptions(context, 0);
        options.circleCrop();
        if (placeResId > 0) {
            options.placeholder(placeResId);
        }
        Glide.with(context).load(path).apply(options).into(iv);
    }
}
