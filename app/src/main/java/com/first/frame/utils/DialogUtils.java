package com.first.frame.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import com.first.frame.FrameApplication;
import com.first.frame.R;


/**
 * 包含单选多选的弹窗
 */
public class DialogUtils {

    private static void showDialog(Context context, Dialog dialog) {
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            dialog.show();
            //show()之前设置无效
            dialog.setCanceledOnTouchOutside(false);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = DisplayUtils.dp2px(context, 286.5f);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }

    private static void showDialog(Context context, Dialog dialog, int gravity) {
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            dialog.show();
            //show()之前设置无效
            dialog.setCanceledOnTouchOutside(false);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = DisplayUtils.dp2px(context, 286.5f);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = gravity;
            window.setAttributes(params);
        }
    }

    private static void showDialogBottom(Context context, Dialog dialog) {
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            dialog.show();
            //show()之前设置无效
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        }
    }

    /**
     * 两个按钮的弹窗（确定，取消），不传postivetext则不显示，不传negativeText则不显示
     *
     * @param context
     * @param title
     * @param message
     * @param positiveText
     * @param positiveListener
     * @param negativeText
     * @param negativeListener
     * @param cancelListener
     * @return
     */
    public static Dialog dialogBuilder(final Context context,
                                       CharSequence title,
                                       CharSequence message,
                                       CharSequence positiveText, final OnClickListener positiveListener,
                                       CharSequence negativeText, final OnClickListener negativeListener,
                                       DialogInterface.OnCancelListener cancelListener) {
        if (context == null) {
            return null;
        }
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        dialog.setContentView(view);
        TextView tvContent = view.findViewById(R.id.tv_content);
        TextView tvSure = view.findViewById(R.id.tv_sure);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        Space spaceBtn = view.findViewById(R.id.space_btn);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        }
        tvTitle.setText(title);

        tvContent.setText(message);

        if (TextUtils.isEmpty(positiveText)) {
            tvSure.setVisibility(View.GONE);
            spaceBtn.setVisibility(View.GONE);
        }
        tvSure.setText(positiveText);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog(context, dialog);
                if (positiveListener != null) {
                    positiveListener.onClick(dialog, Dialog.BUTTON_POSITIVE);
                }
            }
        });

        if (TextUtils.isEmpty(negativeText)) {
            tvCancel.setVisibility(View.GONE);
            spaceBtn.setVisibility(View.GONE);
        }
        tvCancel.setText(negativeText);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog(context, dialog);
                if (negativeListener != null) {
                    negativeListener.onClick(dialog, Dialog.BUTTON_NEGATIVE);
                }
            }
        });
        if (cancelListener != null) {
            dialog.setOnCancelListener(cancelListener);
        }
        showDialog(context, dialog);
        return dialog;
    }

    /**
     * 两个按钮的弹窗（确定，取消），不传postivetext则不显示，不传negativeText则不显示
     *
     * @param context
     * @param title
     * @param message
     * @param positiveText
     * @param positiveListener
     * @param negativeText
     * @param negativeListener
     * @return
     */
    public static Dialog dialogBuilder(Context context,
                                       CharSequence title,
                                       CharSequence message,
                                       CharSequence positiveText, OnClickListener positiveListener,
                                       CharSequence negativeText, OnClickListener negativeListener) {
        return dialogBuilder(context, title, message, positiveText, positiveListener, negativeText, negativeListener, null);
    }

    /**
     * 两个按钮的弹窗（确定，取消），不传postivetext则不显示，不传negativeText则不显示
     * ，可自定义位于title和button之间的contentview，传null则不显示
     *
     * @param context
     * @param title
     * @param content
     * @param positiveText
     * @param positiveListener
     * @param negativeText
     * @param negativeListener
     * @param cancelListener
     * @return
     */
    public static Dialog dialogBuilder(final Context context,
                                       CharSequence title,
                                       View content,
                                       CharSequence positiveText, final OnClickListener positiveListener,
                                       CharSequence negativeText, final OnClickListener negativeListener,
                                       DialogInterface.OnCancelListener cancelListener) {
        if (context == null) {
            return null;
        }
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        dialog.setContentView(view);
        view.findViewById(R.id.tv_content).setVisibility(View.GONE);
        TextView tvSure = view.findViewById(R.id.tv_sure);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        Space spaceBtn = view.findViewById(R.id.space_btn);
        ViewGroup contentView = view.findViewById(R.id.layout_content);
        contentView.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        }
        tvTitle.setText(title);

        if (content != null) {
            contentView.addView(content, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (TextUtils.isEmpty(positiveText)) {
            tvSure.setVisibility(View.GONE);
            spaceBtn.setVisibility(View.GONE);
        }
        tvSure.setText(positiveText);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog(context, dialog);
                if (positiveListener != null) {
                    positiveListener.onClick(dialog, Dialog.BUTTON_POSITIVE);
                }
            }
        });

        if (TextUtils.isEmpty(negativeText)) {
            tvCancel.setVisibility(View.GONE);
            spaceBtn.setVisibility(View.GONE);
        }
        tvCancel.setText(negativeText);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog(context, dialog);
                if (negativeListener != null) {
                    negativeListener.onClick(dialog, Dialog.BUTTON_NEGATIVE);
                }
            }
        });
        if (cancelListener != null) {
            dialog.setOnCancelListener(cancelListener);
        }
        showDialog(context, dialog);
        return dialog;
    }

    /**
     * 两个按钮的弹窗（确定，取消），不传postivetext则不显示，不传negativeText则不显示
     * * ，可自定义位于title和button之间的contentview，传null则不显示
     *
     * @param context
     * @param title
     * @param content
     * @param positiveText
     * @param positiveListener
     * @param negativeText
     * @param negativeListener
     * @return
     */
    public static Dialog dialogBuilder(Context context,
                                       CharSequence title,
                                       View content,
                                       CharSequence positiveText, OnClickListener positiveListener,
                                       CharSequence negativeText, OnClickListener negativeListener) {
        return dialogBuilder(context, title, content, positiveText, positiveListener, negativeText, negativeListener, null);
    }


    /**
     * 只有一个按钮的简单弹窗，可用于退出应用，提示窗口
     *
     * @param context
     * @param title
     * @param content
     * @param positiveText
     * @param positiveListener
     * @return
     */
    public static Dialog dialogBuilder(final Context context
            , CharSequence title, boolean boldTitle, CharSequence content
            , CharSequence positiveText, boolean showClose, final OnClickListener positiveListener) {
        if (context == null) {
            return null;
        }
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.simple_button_dialog, null);
        dialog.setContentView(view);

        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView contentView = view.findViewById(R.id.tv_content);
        TextView tvSure = view.findViewById(R.id.tv_sure);
        TextView close = view.findViewById(R.id.close);
        close.setVisibility(showClose ? View.VISIBLE : View.GONE);

        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            if (boldTitle) {
                tvTitle.getPaint().setFakeBoldText(true);
            }
            tvTitle.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            contentView.setVisibility(View.GONE);
        } else {
            contentView.setVisibility(View.VISIBLE);
        }
        contentView.setText(content);

        if (!TextUtils.isEmpty(positiveText)) {
            tvSure.setText(positiveText);
        }
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog(context, dialog);
                if (positiveListener != null) {
                    positiveListener.onClick(dialog, Dialog.BUTTON_POSITIVE);
                }
            }
        });
        showDialog(context, dialog);
        return dialog;
    }

    /**
     * 只有一个按钮的简单弹窗，可用于退出应用，提示窗口
     *
     * @param context
     * @param title
     * @param content
     * @param positiveText
     * @param positiveListener
     * @return
     */
    public static Dialog dialogBuilder(final Context context
            , CharSequence title, boolean boldTitle, CharSequence content
            , CharSequence positiveText, int gravity, final OnClickListener positiveListener) {
        if (context == null) {
            return null;
        }
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.simple_button_dialog, null);
        dialog.setContentView(view);

        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView contentView = view.findViewById(R.id.tv_content);
        TextView tvSure = view.findViewById(R.id.tv_sure);

        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            if (boldTitle) {
                tvTitle.getPaint().setFakeBoldText(true);
            }
            tvTitle.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            contentView.setVisibility(View.GONE);
        } else {
            contentView.setVisibility(View.VISIBLE);
        }
        contentView.setText(content);

        if (!TextUtils.isEmpty(positiveText)) {
            tvSure.setText(positiveText);
        }
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog(context, dialog);
                if (positiveListener != null) {
                    positiveListener.onClick(dialog, Dialog.BUTTON_POSITIVE);
                }
            }
        });
        showDialog(context, dialog, gravity);
        return dialog;
    }



    /**
     * 只有一个按钮的简单弹窗，可用于退出应用，提示窗口
     *
     * @return
     */
    public static Dialog logOutDialog(Context context, View.OnClickListener listenner) {
        if (context == null) {
            return null;
        }
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_logout, null);
        dialog.setContentView(view);


        ImageView colose = view.findViewById(R.id.img_close);
        TextView tvLogout = view.findViewById(R.id.tv_confirm_logout);

        colose.setOnClickListener(listenner);
        tvLogout.setOnClickListener(listenner);

        showDialog(context, dialog);
        return dialog;
    }


    public static void dismissDialog(Context context, Dialog dialog) {
        try {
            if (context != null && dialog != null && dialog.isShowing()) {
                if (context instanceof Activity && !((Activity) context).isDestroyed() && !((Activity) context).isFinishing()) {
                    dialog.dismiss();
                } else if (context instanceof Activity) {
                    LogUtils.e("the activity is destoryed or finishing:" + context);
                } else {
                    dialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Dialog progress(Context context, String message) {
        if (context == null) {
            return null;
        }
        Dialog progress = new Dialog(context, R.style.DialogTheme);
        progress.setCancelable(true);
        progress.setCanceledOnTouchOutside(false);
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            progress.show();
        }
        progress.setContentView(R.layout.progress_layout);
        TextView tv = progress.findViewById(R.id.tv);
        if (tv != null) {
            if (!TextUtils.isEmpty(message)) {
                tv.setText(message);
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }
        }
        return progress;
    }

    public static Dialog progress(Context context) {
        return progress(context, FrameApplication.getContext().getResources().getString(R.string.loading));
    }

}
