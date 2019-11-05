package net.yundingwei.dszw.app.home.view;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import net.yundingwei.dszw.R;
import net.yundingwei.dszw.app.common.adapter.ListBindAdapter;
import net.yundingwei.dszw.app.common.constants.Constants;
import net.yundingwei.dszw.app.common.utils.DisplayUtils;
import net.yundingwei.dszw.app.common.view.BaseFragment;
import net.yundingwei.dszw.app.home.activity.AuthorizeActivity;
import net.yundingwei.dszw.app.home.activity.MenuActivity;
import net.yundingwei.dszw.app.home.model.GameEntity;
import net.yundingwei.dszw.app.net.HttpCallback;
import net.yundingwei.dszw.app.net.HttpService;
import net.yundingwei.dszw.app.net.MessageApi;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends BaseFragment {

    private TextView mTvAuthorize;
    private GridView mGridView;
    private ArrayList<GameEntity> list = new ArrayList();
    private ListBindAdapter<GameEntity> adapter;

    private int itemWidth;
    private int itemHeight;

    private int iconWidth;
    private int iconHeight;


    @Override
    protected int getContentViewId() {
        return R.layout.home_main;
    }

    @Override
    protected void initUI() {

        this.mTvAuthorize = (TextView) this.view.findViewById(R.id.mTvAuthorize);
        this.mGridView = (GridView) this.view.findViewById(R.id.mGridView);

        this.itemWidth = this.itemHeight = DisplayUtils.getScrrent(context).widthPixels / 4;
        this.iconWidth = this.iconHeight = (int)((this.itemWidth * 1.0f) / 2);

        this.adapter = new ListBindAdapter(context, this, list, R.layout.adapter_game_bind) {
            @Override
            public void convert(ViewDataBinding binding, Object item, int position) {
                super.convert(binding, item, position);
                View mItemView = binding.getRoot();
                LinearLayout mContentView = (LinearLayout) mItemView.findViewById(R.id.mContentView);
                SimpleDraweeView mIvIcon = (SimpleDraweeView)mItemView.findViewById(R.id.mIvIcon);

                FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams)mContentView.getLayoutParams();
                params1.width = itemWidth;
                params1.height = itemHeight;

                LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)mIvIcon.getLayoutParams();
                params2.width = iconWidth;
                params2.height = iconHeight;


                mContentView.requestLayout();
                mIvIcon.requestLayout();
            }
        };
        this.mGridView.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();


        this.mTvAuthorize.setOnClickListener(new OnViewClickListener());

        this.query();

    }

    private void query(){

        HttpService.get(Constants.HOST_URL + "?m=" + MessageApi.game, new HttpCallback<List<GameEntity>>(MessageApi.game) {
//
            @Override
            public void onError(int errorId, String errorMsg) {

            }

            @Override
            public void onMessage(List<GameEntity> body) {
                if(body == null)return;
                list.clear();

                list.addAll(body);

                adapter.notifyDataSetChanged();
            }
        });
    }

    public void authorize(GameEntity item) {
        Intent intent = new Intent(context, MenuActivity.class);
        intent.putExtra("mGameEntity", item);
        context.startActivity(intent);
    }

    private class OnViewClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mTvAuthorize:
                    Intent intent = new Intent(context, AuthorizeActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
