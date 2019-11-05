package net.yundingwei.dszw.app.home.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import net.yundingwei.dszw.R;
import net.yundingwei.dszw.app.common.constants.Constants;
import net.yundingwei.dszw.app.common.utils.DeviceUtils;
import net.yundingwei.dszw.app.common.utils.SingleToast;
import net.yundingwei.dszw.app.common.view.BaseFragment;
import net.yundingwei.dszw.app.home.model.ResEntity;
import net.yundingwei.dszw.app.net.HttpCallback;
import net.yundingwei.dszw.app.net.HttpService;
import net.yundingwei.dszw.app.net.MessageApi;


public class AuthorizeFragment extends BaseFragment {

    private FrameLayout mBackView;
    private EditText mEditPassword;
    private Button mBtnAuthorize;


    @Override
    protected int getContentViewId() {
        return R.layout.home_authorize;
    }

    @Override
    protected void initUI() {
        this.mBackView = (FrameLayout) this.view.findViewById(R.id.mBackView);
        this.mEditPassword = (EditText) this.view.findViewById(R.id.mEditPassword);
        this.mBtnAuthorize = (Button) this.view.findViewById(R.id.mBtnAuthorize);

        this.mBackView.setOnClickListener(new OnViewClickListener());
        this.mBtnAuthorize.setOnClickListener(new OnViewClickListener());

    }

    private boolean check(){
        String password = mEditPassword.getText().toString();
        if(TextUtils.isEmpty(password)) {
            SingleToast.show(context, "授权码不能为空");
            return false;
        }
        return true;
    }


    private class OnViewClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mBackView:
                    getActivity().finish();
                    break;
                case R.id.mBtnAuthorize:
                    if(check()) {
                        submit();
                    }
                    break;
            }
        }
    }

    private void submit(){
        String code = mEditPassword.getText().toString();
        String sn = DeviceUtils.getDeviceId(context);

        String url = Constants.HOST_URL + "?m=" + MessageApi.auth + "&code=" + code + "&sn=" + sn;
        HttpService.get(url, new HttpCallback<ResEntity>(MessageApi.auth) {

            @Override
            public void onError(int errorId, String errorMsg) {
                if(!TextUtils.isEmpty(errorMsg)) {
                    SingleToast.show(context, errorMsg);
                }else {
                    SingleToast.show(context, "授权码不正确");
                }
            }

            @Override
            public void onMessage(ResEntity body) {
                if(body == null)return;
                int error = body.getError();
                String msg = body.getMsg();

                if(!TextUtils.isEmpty(msg)) {
                    SingleToast.show(context, msg);
                }else {

                    if(error == 0) {
                        SingleToast.show(context, "启动成功");
                    }else {
                        SingleToast.show(context,"授权码不正确");
                    }
                }
            }
        });
    }

}
