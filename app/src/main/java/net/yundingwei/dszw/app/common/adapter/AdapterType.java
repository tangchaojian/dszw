package net.yundingwei.dszw.app.common.adapter;

import android.databinding.BaseObservable;

/**
 * Created by lx on 2017/2/8.
 */

public class AdapterType extends BaseObservable {

    protected int viewType;


    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
