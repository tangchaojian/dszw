package net.yundingwei.dszw.app.home.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.yundingwei.dszw.BR;
import net.yundingwei.dszw.R;
import net.yundingwei.dszw.app.common.adapter.ListBindAdapter;
import net.yundingwei.dszw.app.common.constants.Constants;
import net.yundingwei.dszw.app.common.model.KeyValueEntity;
import net.yundingwei.dszw.app.common.utils.DeviceUtils;
import net.yundingwei.dszw.app.common.utils.SingleToast;
import net.yundingwei.dszw.app.common.view.BaseFragment;
import net.yundingwei.dszw.app.common.widgets.dialog.UIDialog;
import net.yundingwei.dszw.app.common.widgets.gridview.FullGridView;
import net.yundingwei.dszw.app.common.widgets.viewgroup.WrapwordLayout;
import net.yundingwei.dszw.app.home.activity.AuthorizeActivity;
import net.yundingwei.dszw.app.home.model.GameEntity;
import net.yundingwei.dszw.app.home.model.ListEntity;
import net.yundingwei.dszw.app.home.model.MajiangEntity;
import net.yundingwei.dszw.app.home.model.MenuEntity;
import net.yundingwei.dszw.app.home.model.PaiEntity;
import net.yundingwei.dszw.app.home.model.PaohuziEntity;
import net.yundingwei.dszw.app.home.model.PukeEntity;
import net.yundingwei.dszw.app.home.model.ResEntity;
import net.yundingwei.dszw.app.net.HttpCallback;
import net.yundingwei.dszw.app.net.HttpService;
import net.yundingwei.dszw.app.net.MessageApi;
import net.yundingwei.dszw.databinding.AdapterListHeaderBindBinding;
import net.yundingwei.dszw.databinding.DialogSelectMajiangBinding;
import net.yundingwei.dszw.databinding.DialogSelectPaohuziBinding;
import net.yundingwei.dszw.databinding.DialogSelectPukeBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends BaseFragment {

    private MenuFragment fragment = this;
    private FrameLayout mBackView;
    private TextView mTvAuthorize;
    private LinearLayout mContentView;
    private View mHeaderView;
    private View mBottomView;

    private List<MenuEntity> list = new ArrayList();

    private MajiangEntity model1;
    private PukeEntity model2;
    private PaohuziEntity model3;

    private AdapterListHeaderBindBinding mHeaderBinding;

    private ListBindAdapter<PaiEntity> adapter;
    private ListBindAdapter<PaiEntity> selectedAdapter;

    private GameEntity mGameEntity;

    private UIDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle args = this.getArguments();
        if(args != null){
            this.mGameEntity = (GameEntity) args.getSerializable("mGameEntity");
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_menu;
    }

    @Override
    protected void initUI() {

        this.mBackView = (FrameLayout) this.view.findViewById(R.id.mBackView);
        this.mTvAuthorize = (TextView) this.view.findViewById(R.id.mTvAuthorize);

        this.mContentView = (LinearLayout) this.view.findViewById(R.id.mContentView);
        this.mHeaderView = View.inflate(context, R.layout.adapter_list_header_bind, null);
        this.mBottomView = View.inflate(context, R.layout.adapter_list_bottom_bind, null);

        this.mBackView.setOnClickListener(new OnViewClickListener());
        this.mTvAuthorize.setOnClickListener(new OnViewClickListener());

        this.mHeaderBinding = DataBindingUtil.bind(mHeaderView);
        this.mHeaderBinding.setModel(mGameEntity);
        this.mHeaderBinding.setFragment(fragment);

        SharedPreferences preferences = context.getSharedPreferences("dszw", Context.MODE_PRIVATE);
        String authCode = preferences.getString("authCode", null);
        String gameID = preferences.getString("gameID", null);

        if(!TextUtils.isEmpty(gameID)) {
            this.mHeaderBinding.mEditGameID.setText(gameID);
        }

        if(!TextUtils.isEmpty(authCode)) {
            this.mHeaderBinding.mEditAuthCode.setText(authCode);
        }

        Button mBtnStart = (Button) this.mBottomView.findViewById(R.id.mBtnStart);
        mBtnStart.setOnClickListener(new OnViewClickListener());


        this.query();

    }

    private void query(){

        String id = mGameEntity.getId();

        HttpService.get(Constants.HOST_URL + "?m=" + MessageApi.menu + "&id=" + id, new HttpCallback<List<MenuEntity>>(MessageApi.menu) {
            //
            @Override
            public void onError(int errorId, String errorMsg) {

            }

            @Override
            public void onMessage(List<MenuEntity> body) {
                if(body == null)return;
                list.clear();
                mContentView.addView(mHeaderView);
                for (MenuEntity item : body) {

                    //1、文本；2、开关；3、列表；4、开关+列表；5、麻将选牌；6、扑克选牌, 7、跑胡子选牌
                    View view = null;
                    ViewDataBinding binding = null;
                    if(item.getType() == 1) {
                        view = View.inflate(context, R.layout.adapter_list_bind1, null);
                    }else if(item.getType() == 2) {
                        view = View.inflate(context, R.layout.adapter_list_bind2, null);
                    }else if(item.getType() == 3) {
                        Gson gson = new Gson();
                        String json = gson.toJson(item.getList());
                        Log.i("DSZW", "json->" + json);
                        Type type = new TypeToken<ListEntity>(){}.getType();
                        ListEntity entity = gson.fromJson(json, type);
                        List<String> options = entity.getOption();
                        List<KeyValueEntity> datas = new ArrayList<KeyValueEntity>();
                        if(options != null && !options.isEmpty()) {
                            for(String option : options){
                                KeyValueEntity keyValueEntity = new KeyValueEntity();
                                keyValueEntity.setValue(option);
                                keyValueEntity.setExt(item);
                                datas.add(keyValueEntity);
                            }
                        }

                        item.setData(datas);
                        item.setValue(datas.get(0).getValue());
                        view = View.inflate(context, R.layout.adapter_list_bind3, null);

                    }else if(item.getType() == 4) {
                        Gson gson = new Gson();
                        String json = gson.toJson(item.getList());
                        Log.i("DSZW", "json->" + json);
                        Type type = new TypeToken<ListEntity>(){}.getType();
                        ListEntity entity = gson.fromJson(json, type);
                        List<String> options = entity.getOption();
                        List<KeyValueEntity> datas = new ArrayList<KeyValueEntity>();
                        if(options != null && !options.isEmpty()) {
                            for(String option : options){
                                KeyValueEntity keyValueEntity = new KeyValueEntity();
                                keyValueEntity.setValue(option);
                                keyValueEntity.setExt(item);
                                datas.add(keyValueEntity);
                            }
                        }

                        item.setData(datas);
                        item.setValue(datas.get(0).getValue());
                        view = View.inflate(context, R.layout.adapter_list_bind4, null);

                    }else if(item.getType() == 5) {

                        Gson gson = new Gson();
                        String json = gson.toJson(item.getList());
                        Log.i("DSZW", "json->" + json);
                        Type type = new TypeToken<ListEntity>(){}.getType();
                        ListEntity entity = gson.fromJson(json, type);
                        item.setMax(entity.getNumber());
                        view = View.inflate(context, R.layout.adapter_list_bind5, null);
                    }else if(item.getType() == 6) {
                        Gson gson = new Gson();
                        String json = gson.toJson(item.getList());
                        Log.i("DSZW", "json->" + json);
                        Type type = new TypeToken<ListEntity>(){}.getType();
                        ListEntity entity = gson.fromJson(json, type);
                        item.setMax(entity.getNumber());


                        view = View.inflate(context, R.layout.adapter_list_bind6, null);
                    }else if(item.getType() == 7) {

                        Gson gson = new Gson();
                        String json = gson.toJson(item.getList());
                        Log.i("DSZW", "json->" + json);
                        Type type = new TypeToken<ListEntity>(){}.getType();
                        ListEntity entity = gson.fromJson(json, type);
                        item.setMax(entity.getNumber());

                        view = View.inflate(context, R.layout.adapter_list_bind7, null);
                    }

                    if(view != null) {
                        binding = DataBindingUtil.bind(view);
                        binding.setVariable(BR.item, item);
                        binding.setVariable(BR.fragment, fragment);
                        mContentView.addView(view);
                    }

                }
                mContentView.addView(mBottomView);
            }
        });
    }

    private void authorize(){

        //http://www.dingweiyun.net/dszw/index.php?m=auth&id=游戏ID&code=授权码&sn=软件机器码&game_id=用户填写的ID

        final String gameID = mHeaderBinding.mEditGameID.getText().toString();
        final String code = mHeaderBinding.mEditAuthCode.getText().toString();
        String sn = DeviceUtils.getDeviceId(context);

        String url = Constants.HOST_URL + "?m=" + MessageApi.auth + "&id=" + gameID + "&code=" + code + "&sn=" +sn;
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
                if(error == 0) {

                    SharedPreferences preferences = context.getSharedPreferences("dszw", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("gameID", gameID);
                    editor.putString("authCode", code);
                    editor.commit();

                    SingleToast.show(context, "启动成功");

                    if(mGameEntity != null) {
                        String path_android = mGameEntity.getPath_android();
                        if(!TextUtils.isEmpty(path_android)) {
                            String str[] = path_android.split("\\|");

                            if(str != null && str.length == 2) {
                                String packageName = str[0];
                                String className = str[1];

                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                ComponentName cn = new ComponentName(packageName, className);
                                intent.setComponent(cn);
                                startActivity(intent);
                            }
                        }
                    }
                }else if(error == 404){
                    SingleToast.show(context, body.getMsg());
                }
            }
        });
    }

    /**
     * 选中
     * @param item
     */
    public void selected(MenuEntity item) {
        if (item.getType() == 3) {
            //列表
            View dialogView = View.inflate(context, R.layout.dialog_select_list, null);
            ListView mListView = (ListView) dialogView.findViewById(R.id.mListView);
            ListBindAdapter<KeyValueEntity> adapter = new ListBindAdapter(context, fragment, item.getData(), R.layout.adapter_dialog_list_bind);
            mListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            dialog = new UIDialog(context, dialogView);
            dialog.show();

        }else if(item.getType() == 4) {
            //开关 + 列表

            View dialogView = View.inflate(context, R.layout.dialog_select_list, null);
            ListView mListView = (ListView) dialogView.findViewById(R.id.mListView);
            ListBindAdapter<KeyValueEntity> adapter = new ListBindAdapter(context, fragment, item.getData(), R.layout.adapter_dialog_list_bind);
            mListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            dialog = new UIDialog(context, dialogView);
            dialog.show();

        }else if(item.getType() == 5) {
            //麻将选牌
            if(!item.isChecked()) {
                item.setChecked(true);
                int[] majiangs = Constants.majiang;
                List<PaiEntity> majiangList = new ArrayList<>();
                for (int i = 0; i < majiangs.length; i++) {
                    int resId = majiangs[i];
                    PaiEntity entity = new PaiEntity();
                    entity.setResId(resId);
                    entity.setDefaultResId(R.drawable.bg);
                    entity.setMax(item.getMax());
                    majiangList.add(entity);
                }

                View dialogView = View.inflate(context, R.layout.dialog_select_majiang, null);

                this.model1 = new MajiangEntity();
                this.model1.setPaiList(majiangList);
                this.model1.setCheckedList(new ArrayList<PaiEntity>());

                DialogSelectMajiangBinding binding = DataBindingUtil.bind(dialogView);
                binding.setModel(model1);
                binding.setFragment(fragment);

                FullGridView mGridView = (FullGridView) dialogView.findViewById(R.id.mGridView);
                WrapwordLayout mWrapwordLayout = (WrapwordLayout) dialogView.findViewById(R.id.mWLayout);


                this.adapter = new ListBindAdapter(context, fragment, majiangList, R.layout.adapter_gridview_majiang_bind);
                mGridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                this.selectedAdapter = new ListBindAdapter(context, fragment, model1.getCheckedList(), R.layout.adapter_gridview_majiang_checked_bind);
                mWrapwordLayout.setAdapter(selectedAdapter);
                selectedAdapter.notifyDataSetChanged();

                dialog = new UIDialog(context, dialogView);
                dialog.show();
            }else {
                item.setChecked(false);
            }

        }else if(item.getType() == 6) {
            //扑克牌选牌
            if(!item.isChecked()) {
                item.setChecked(true);
                int[] pukes = Constants.puke;
                List<PaiEntity> pukeList = new ArrayList<>();
                for (int i = 0; i < pukes.length; i++) {
                    int resId = pukes[i];
                    PaiEntity entity = new PaiEntity();
                    entity.setResId(resId);
                    entity.setDefaultResId(R.drawable.pk_bg);
                    entity.setMax(item.getMax());
                    pukeList.add(entity);
                }

                View dialogView = View.inflate(context, R.layout.dialog_select_puke, null);
                this.model2 = new PukeEntity();
                this.model2.setPaiList(pukeList);
                this.model2.setCheckedList(new ArrayList<PaiEntity>());

                DialogSelectPukeBinding binding = DataBindingUtil.bind(dialogView);
                binding.setModel(model2);
                binding.setFragment(fragment);

                FullGridView mGridView = (FullGridView) dialogView.findViewById(R.id.mGridView);
                WrapwordLayout mWrapwordLayout = (WrapwordLayout) dialogView.findViewById(R.id.mWLayout);


                this.adapter = new ListBindAdapter(context, fragment, pukeList, R.layout.adapter_gridview_puke_bind);
                mGridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                this.selectedAdapter = new ListBindAdapter(context, fragment, model2.getCheckedList(), R.layout.adapter_gridview_puke_checked_bind);
                mWrapwordLayout.setAdapter(selectedAdapter);
                selectedAdapter.notifyDataSetChanged();

                dialog = new UIDialog(context, dialogView);
                dialog.show();
            }else {
                item.setChecked(false);
            }
        }else if(item.getType() == 7) {
            //跑胡子选牌
            if(!item.isChecked()) {
                item.setChecked(true);
                int[] paohuzis = Constants.paohuzi;
                List<PaiEntity> paohuziList = new ArrayList<>();
                for (int i = 0; i < paohuzis.length; i++) {
                    int resId = paohuzis[i];
                    PaiEntity entity = new PaiEntity();
                    entity.setResId(resId);
                    entity.setDefaultResId(R.drawable.zpbg);
                    entity.setMax(item.getMax());
                    paohuziList.add(entity);
                }

                View dialogView = View.inflate(context, R.layout.dialog_select_paohuzi, null);

                this.model3 = new PaohuziEntity();
                this.model3.setPaiList(paohuziList);
                this.model3.setCheckedList(new ArrayList<PaiEntity>());

                DialogSelectPaohuziBinding binding = DataBindingUtil.bind(dialogView);
                binding.setModel(model3);
                binding.setFragment(fragment);

                FullGridView mGridView = (FullGridView) dialogView.findViewById(R.id.mGridView);
                WrapwordLayout mWrapwordLayout = (WrapwordLayout) dialogView.findViewById(R.id.mWLayout);


                this.adapter = new ListBindAdapter(context, fragment, paohuziList, R.layout.adapter_gridview_paohuzi_bind);
                mGridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                this.selectedAdapter = new ListBindAdapter(context, fragment, model3.getCheckedList(), R.layout.adapter_gridview_paohuzi_checked_bind);
                mWrapwordLayout.setAdapter(selectedAdapter);
                selectedAdapter.notifyDataSetChanged();

                dialog = new UIDialog(context, dialogView);
                dialog.show();
            }else {
                item.setChecked(false);
            }
        }
    }

    private class OnViewClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.mBackView:
                    getActivity().finish();
                    break;
                case R.id.mTvAuthorize:
                    intent = new Intent(context, AuthorizeActivity.class);
                    context.startActivity(intent);
                    break;
                case R.id.mBtnStart:

                    String gameID = mHeaderBinding.mEditGameID.getText().toString();
                    String authCode = mHeaderBinding.mEditAuthCode.getText().toString();

                    if(TextUtils.isEmpty(gameID)) {
                        SingleToast.show(context, "请输入游戏ID");
                        return;
                    }

                    if(TextUtils.isEmpty(authCode)) {
                        SingleToast.show(context, "请授权或跳到 授权页面。");
                        return;
                    }

                    authorize();

                    break;
            }
        }
    }


    /**
     * 选牌
     * @param type 0 麻将 1 扑克 2跑胡子
     * @param item
     */
    public void selectPai(int type, PaiEntity item){
        if(type == 0) {

            int max = item.getMax();
            boolean selected = item.isSelected();

            if(!selected) {
                if(max > 0) {
                    List<PaiEntity> list = model1.getPaiList();
                    int sum = 0;
                    for (PaiEntity paiEntity : list) {
                        if(paiEntity.isSelected()) {
                            sum ++;
                        }
                    }

                    if(sum >= max) {
                        SingleToast.show(context, "选牌不能超过" + max + "张");
                        return;
                    }
                }

                item.setSelected(!selected);
                model1.getCheckedList().add(item);
            }else {
                item.setSelected(!selected);
                model1.getCheckedList().remove(item);
            }

            if(this.adapter != null) {
                this.adapter.notifyDataSetChanged();
            }

            if(this.selectedAdapter != null) {
                this.selectedAdapter.notifyDataSetChanged();
            }
        }else if(type == 1) {

            int max = item.getMax();
            boolean selected = item.isSelected();

            if(!selected) {
                if(max > 0) {
                    List<PaiEntity> list = model2.getPaiList();
                    int sum = 0;
                    for (PaiEntity paiEntity : list) {
                        if(paiEntity.isSelected()) {
                            sum ++;
                        }
                    }

                    if(sum >= max) {
                        SingleToast.show(context, "选牌不能超过" + max + "张");
                        return;
                    }
                }

                item.setSelected(!selected);
                model2.getCheckedList().add(item);
            }else {
                item.setSelected(!selected);
                model2.getCheckedList().remove(item);
            }

            if(this.adapter != null) {
                this.adapter.notifyDataSetChanged();
            }

            if(this.selectedAdapter != null) {
                this.selectedAdapter.notifyDataSetChanged();
            }

        }else if(type == 2) {

            boolean selected = item.isSelected();

            if(!selected) {
                int max = item.getMax();
                if(max > 0) {
                    List<PaiEntity> list = model3.getPaiList();
                    int sum = 0;
                    for (PaiEntity paiEntity : list) {
                        if(paiEntity.isSelected()) {
                            sum ++;
                        }
                    }

                    if(sum >= max) {
                        SingleToast.show(context,"选牌不能超过" + max + "张");
                        return;
                    }
                }

                item.setSelected(!selected);
                model3.getCheckedList().add(item);
            }else {
                item.setSelected(!selected);
                model3.getCheckedList().remove(item);
            }

            if(this.adapter != null) {
                this.adapter.notifyDataSetChanged();
            }

            if(this.selectedAdapter != null) {
                this.selectedAdapter.notifyDataSetChanged();
            }
        }
    }


    public void onListSelected(KeyValueEntity item){
        if(dialog != null)dialog.cancel();

        MenuEntity menu = (MenuEntity) item.getExt();
        menu.setValue(item.getValue());
    }

    /**
     *
     * @param type 0 麻将，1扑克 2 跑胡子
     * @param position 第几个按钮
     */
    public void onButtonClick(int type, int position){
        if(type == 0) {
            if(position == 0) {
                if(model1 != null) {
                    boolean checked = model1.isChecked1();
                    model1.setChecked1(!checked);
                }
            }else if(position == 1){
                if(model1 != null) {
                    boolean checked = model1.isChecked2();
                    model1.setChecked2(!checked);
                }
            }else if(position == 2){
                if(model1 != null) {
                    boolean checked = model1.isChecked3();
                    model1.setChecked3(!checked);
                }
            }else if(position == 3){
                if(model1 != null) {
                    boolean checked = model1.isChecked4();
                    model1.setChecked4(!checked);
                }
            }else if(position == 4){
                if(model1 != null) {
                    boolean checked = model1.isChecked5();
                    model1.setChecked5(!checked);
                }
            }else if(position == 5){
                if(dialog != null) {
                    dialog.cancel();
                }
            }
        }else if(type == 1) {
            //扑克
            if(position == 0) {
                if(model2 != null) {
                    boolean checked = model2.isChecked1();
                    model2.setChecked1(!checked);
                }
            }else {
                if(dialog != null) {
                    dialog.cancel();
                }
            }
        }else if(type == 2) {
            //扑克
            if(position == 0) {
                if(model3 != null) {
                    boolean checked = model3.isChecked1();
                    model3.setChecked1(!checked);
                }
            }else {
                if(dialog != null) {
                    dialog.cancel();
                }
            }
        }
    }
}