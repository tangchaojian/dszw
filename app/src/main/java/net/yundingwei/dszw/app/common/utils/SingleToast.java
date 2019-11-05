package net.yundingwei.dszw.app.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class SingleToast {

    private static Toast mToast;


    public static void show(Context context, String text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String text, int length) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        }
        mToast.setText(text);
        mToast.setDuration(length);
        mToast.show();
    }
}
