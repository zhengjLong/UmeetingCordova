package io.cordova.hellocordova;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * 加载中对话框快速创建
 * @author : jerome
 */
public final class LoadingDialog {

    // 使用弱引用
    private static WeakReference<AppLoadingDialog> sInstance;

    private static AppLoadingDialog build(Context context) {
        AppLoadingDialog dialog;// 同一个处理
        if (sInstance != null && sInstance.get() != null) {
            dialog = sInstance.get();

            if (!((ContextWrapper) sInstance.get().getContext()).getBaseContext().equals(context)) {
                sInstance.get().dismiss();
                sInstance.clear();
                sInstance = null;
                dialog = new AppLoadingDialog(context);
                sInstance = new WeakReference<AppLoadingDialog>(dialog);
            }

        } else {
            dialog = new AppLoadingDialog(context);
            sInstance = new WeakReference<AppLoadingDialog>(dialog);
        }
        return dialog;
    }

    public static AppLoadingDialog show(Context context, String msg) {
        AppLoadingDialog dialog = build(context);
        if (!TextUtils.isEmpty(msg)) {
            dialog.setMessage(msg);
        }
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static AppLoadingDialog show(Context context) {
        AppLoadingDialog dialog = show(context, "正在加载中...");
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.loading();
        }
        return dialog;
    }


    public static Toast success(Context context, String msg) {
        return toast(context.getApplicationContext(), msg, R.mipmap.ic_success);
    }

    public static Toast failed(Context context, String msg) {
        return toast(context.getApplicationContext(), msg, R.mipmap.ic_failed);
    }

    public static Toast toast(Context context, String msg) {
        Toast toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.getView().setBackgroundResource(R.drawable.bg_default_toast);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.WHITE);
        toast.show();
        return toast;
    }

    public static Toast toast(Context context, String msg, int icon) {
        Toast toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_dialog_loading, null);
        view.findViewById(R.id.pb_loading_dialog).setVisibility(View.GONE);
        ImageView imgView = (ImageView) view.findViewById(R.id.img_loading_dialog_icon);
        imgView.setVisibility(View.VISIBLE);
        TextView msgView = (TextView) view.findViewById(R.id.tv_loading);
        imgView.setImageResource(icon);
        msgView.setText(msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
        return toast;
    }


    /**
     * 弹出对话框
     *
     * @param context
     * @param content         内容
     * @param confirmListener 确认回调
     * @return
     */
    public static PromptDialog alert(Context context, String content, final DialogInterface.OnClickListener confirmListener) {
        final PromptDialog dialog = new PromptDialog(context);
        dialog.setOkText("确定");
        dialog.setCancelBtn("取消");
        dialog.setContent(content);
        dialog.setTitleVisibility(View.GONE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCallBack(new BaseDialog.CallBack() {
            @Override
            public void callBack(Object returnData) {
                confirmListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
            }

            @Override
            public void cancel(Object returnData) {
                confirmListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
            }
        });
        dialog.show();
        return dialog;
    }


    /**
     * 没有取消按钮的对话框
     *
     * @param context
     * @param content
     * @param confirmListener
     * @return
     */
    public static Dialog alertWithoutCancel(Context context, String content, final View.OnClickListener confirmListener) {
        PromptDialog dialog = alert(context, content, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (confirmListener != null)
                    confirmListener.onClick(null);
            }
        });
        dialog.setCancelButtonVisible(false);
        return dialog;
    }

    public static PromptDialog alert(Context context, String title, String content, String confirmText, String cancleText, boolean visibleConfirm,
                                     final DialogInterface.OnClickListener clickListener) {
        final PromptDialog dialog = new PromptDialog(context);
        dialog.setTitle(title);
        dialog.setContent(content);
        dialog.setOkText(confirmText);
        dialog.setOKVisible(visibleConfirm);
        dialog.setCancelBtn(cancleText);
        dialog.setCallBack(new BaseDialog.CallBack() {
            @Override
            public void callBack(Object returnData) {
                clickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
            }

            @Override
            public void cancel(Object returnData) {
                clickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);

            }
        });
        dialog.show();
        return dialog;
    }



    public static void dismiss() {
        if (sInstance != null && sInstance.get() != null) {
            sInstance.get().dismiss();
            sInstance.clear();
        }
        sInstance = null;
    }
}
