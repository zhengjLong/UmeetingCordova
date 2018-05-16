package io.cordova.hellocordova;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;


/**
 * 普通弹出基类
 * @Author: jerome
 * @Date: 2017-08-15
 */
public class AppDialog extends Dialog {

    public AppDialog(Context context) {
        this(context, R.style.AppDialog);
    }

    public AppDialog(Context context, int themeResId) {
        super(context, themeResId);
        if (themeResId == R.style.AppDialog)
            init();
    }

    protected void init() {
        WindowManager.LayoutParams attr = getWindow().getAttributes();
        attr.gravity = Gravity.LEFT | Gravity.RIGHT | Gravity.CENTER;
        attr.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attr);
    }

}
