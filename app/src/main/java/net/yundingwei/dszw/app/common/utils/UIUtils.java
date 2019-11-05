package net.yundingwei.dszw.app.common.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lx on 2017/4/1.
 */

public class UIUtils {

    public static void measureView(View view) {

        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, params.width);
        int height = 0;
        if (params.height > 0) {
            height = View.MeasureSpec.makeMeasureSpec(params.height, View.MeasureSpec.EXACTLY);
        } else {
            height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }

        view.measure(width, height);
    }

    public static int measureTextViewHeight(TextView textView, int width) {

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }
}
