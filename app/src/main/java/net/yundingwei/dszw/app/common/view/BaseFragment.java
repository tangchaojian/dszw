package net.yundingwei.dszw.app.common.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lx on 2017/4/24.
 */

public abstract class BaseFragment extends Fragment {

    protected View view;
    protected Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.context = getActivity();
        int viewId = this.getContentViewId();
        if(viewId == 0){

            return null;
        }

        this.view = inflater.inflate(viewId, container, false);
        return this.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.context = getActivity();
        this.initUI();
    }

    protected abstract @LayoutRes int getContentViewId();

    protected abstract void initUI();

    public void onRestart() {

    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            getActivity().finish();
            return true;
        }
        return false;
    }


}
