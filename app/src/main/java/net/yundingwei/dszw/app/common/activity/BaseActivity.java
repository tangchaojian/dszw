package net.yundingwei.dszw.app.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;

import net.yundingwei.dszw.R;
import net.yundingwei.dszw.app.common.annotation.Content;
import net.yundingwei.dszw.app.common.annotation.StatusBar;
import net.yundingwei.dszw.app.common.utils.DisplayUtils;
import net.yundingwei.dszw.app.common.view.BaseFragment;


/**
 * 基础Activity
 */
public class BaseActivity extends FragmentActivity {

    protected Context context = this;

    protected FragmentManager manager;

    private BaseFragment mBaseFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class<?> clazz = this.getClass();
        boolean isStatusBarTransparent = true;
        if(clazz.isAnnotationPresent(StatusBar.class)) {
            StatusBar statusBar = clazz.getAnnotation(StatusBar.class);
            if (statusBar != null) {
                isStatusBarTransparent = statusBar.transparent();
            }
        }


        if(isStatusBarTransparent) {
            DisplayUtils.fullWindow(this);
        }


        setContentView(R.layout.activity_common);



        this.manager = this.getSupportFragmentManager();
        FragmentTransaction transaction = this.manager.beginTransaction();
        transaction.addToBackStack(null);

        if(clazz.isAnnotationPresent(Content.class)){
            Content content = clazz.getAnnotation(Content.class);
            if(content != null){
                String name = content.name();
                String title = content.title();

                this.getIntent().putExtra("title", title);

                try{
                    Class<?> contentClazz = Class.forName(name);
                    mBaseFragment = (BaseFragment) contentClazz.newInstance();
                    mBaseFragment.setArguments(this.getIntent().getExtras());
                    transaction.replace(R.id.mLayoutFragment, mBaseFragment);

                }catch (Exception e){
                    Log.i("TAG", "异常:" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        transaction.commitAllowingStateLoss();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(this.mBaseFragment != null){
            this.mBaseFragment.onRestart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(this.mBaseFragment != null){
            this.mBaseFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.mBaseFragment != null){
            this.mBaseFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if(this.mBaseFragment != null){
            return this.mBaseFragment.onKeyDown(keyCode, event);
        }
        return true;
    }

}
