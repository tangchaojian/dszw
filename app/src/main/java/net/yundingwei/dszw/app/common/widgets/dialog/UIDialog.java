package net.yundingwei.dszw.app.common.widgets.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import net.yundingwei.dszw.R;


public class UIDialog extends Dialog {

	private UIDialog mDialog = this;
	private Context context;
	public View rootView;
	protected View mAnimView;
	private int gravity;

	public int width = 0;
	public int height = 0;
	public int x;
	public int y;
	
	public Object obj1;
	public Object obj2;
	private Bundle data;
	
	public boolean showed = false;//是否已经显示过了

	public UIDialog(Context context){
		super(context, R.style.mdailog);
		this.context = context;
	}

	protected UIDialog(Context context, int styleId){
		super(context, styleId);
		this.context = context;
	}
	
	public UIDialog(Context context, View view) {
		super(context, R.style.mdailog);
		this.context = context;
		this.rootView = view;
		if(view instanceof ViewGroup){
			ViewGroup mViewGroup = (ViewGroup)view;
			this.mAnimView = mViewGroup.getChildAt(0);
		}else{
			this.mAnimView = view;
		}
		this.setContentView(view);
	}
	
	public UIDialog(Context context, int styleId, View view) {
		super(context, styleId);
		this.context = context;
		this.rootView = view;
		if(view instanceof ViewGroup){
			ViewGroup mViewGroup = (ViewGroup)view;
			this.mAnimView = mViewGroup.getChildAt(0);
		}else{
			this.mAnimView = view;
		}
		this.setContentView(view);
	}
	
	public void setGravity(int gravity){
		this.gravity = gravity;
	}
	
	/**
	 * 得到窗体内容view
	 * @return
	 */
	public View getContentView(){
		return this.mAnimView;
	}
	
	public void setData(Bundle data){
		this.data = data;
	}
	
	public Bundle getData(){
		return this.data;
	}
	
	@Override
	public void show() {
		Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width != 0 ? width : WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = height != 0 ? height: WindowManager.LayoutParams.WRAP_CONTENT;
        if(this.gravity == 0){
        	this.gravity = Gravity.CENTER;
        }
        window.setGravity(this.gravity);
        window.setAttributes(lp);
        showed = true;
		super.show();
	}
}
