package com.first.frame.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.first.frame.R;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;

/**
 * ToastUtils
 */
public class ToastUtils {

    private static final String TOAST_TYPEFACE = "sans-serif-condensed";

    private static Toast currentToast;
    //*******************************************在顶部显示********************************************

    public static void showTop(@NonNull Context context, int resId) {
        top(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showTop(@NonNull Context context, @NonNull CharSequence message) {
        top(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showTop(@NonNull Context context, int resId, int duration) {
        top(context, resId, duration).show();
    }

    public static void showTop(@NonNull Context context, @NonNull CharSequence message, int duration) {
        top(context, message, duration).show();
    }

    @CheckResult
    protected static Toast top(@NonNull Context context, int resId, int duration) {
        return top(context, context.getString(resId), duration);
    }

    @CheckResult
    protected static Toast top(@NonNull Context context, @NonNull CharSequence message, int duration) {
        if (currentToast == null) {
            currentToast = new Toast(context);
        } else {
            currentToast.cancel();
            currentToast = new Toast(context);
        }
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast, null);
        final TextView toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);

        toastTextView.setText(message);
        toastTextView.setTypeface(Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));

        currentToast.setView(toastLayout);
        currentToast.setGravity(Gravity.TOP, 0, DisplayUtils.dp2px(20));
        currentToast.setDuration(duration);
        return currentToast;
    }

    //******************************************系统 Toast 替代方法***************************************

    /**
     * Toast 替代方法 ：立即显示无需等待
     */
    private static Toast mToast;

    /**
     * 封装了Toast的方法 :需要等待
     *
     * @param context Context
     * @param str     要显示的字符串
     * @param isLong  Toast.LENGTH_LONG / Toast.LENGTH_SHORT
     */
    public static void showToast(Context context, String str, boolean isLong) {
        if (isLong) {
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Toast 替代方法 ：立即显示无需等待
     *
     * @param context  实体
     * @param resId    String资源ID
     * @param duration 显示时长
     */
    public static void show(Context context, int resId, int duration) {
        show(context, context.getString(resId), duration);
    }

    public static void show(Context context, int resId) {
        show(context, context.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * Toast 替代方法 ：立即显示无需等待
     *
     * @param context  实体
     * @param msg      要显示的字符串
     * @param duration 显示时长
     */
    public static void show(Context context, CharSequence msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

}
