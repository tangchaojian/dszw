package net.yundingwei.dszw.app.common.widgets.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class TouchRelativeLayout extends RelativeLayout {

	public TouchRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public TouchRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public TouchRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return true;
	}

}
