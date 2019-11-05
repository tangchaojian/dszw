package net.yundingwei.dszw.app.common.widgets.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * =========================
 *		完全展开的GridView
 * =========================
 */
public class FullGridView extends GridView {

    public FullGridView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public FullGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public FullGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }

}
