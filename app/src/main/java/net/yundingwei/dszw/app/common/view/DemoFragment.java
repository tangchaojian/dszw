package net.yundingwei.dszw.app.common.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;


/**
 * 模板Fragment
 */

public class DemoFragment extends BaseFragment {

    private MBroadcastReceiver receiver = new MBroadcastReceiver();

    @Override
    protected int getContentViewId() {
        return 0;
    }

    @Override
    protected void initUI() {

    }

    //-------------------------------生命周期----------------------------------



    //--------------------------------私有方法---------------------------------



    //--------------------------------公开方法----------------------------------


    //--------------------------------监听回调----------------------------------


    private class OnViewClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    }


    //--------------------------------内部类-------------------------------------



    //--------------------------------广播---------------------------------------

    private class MBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String cmd = intent.getStringExtra("cmd");

        }
    }

}
