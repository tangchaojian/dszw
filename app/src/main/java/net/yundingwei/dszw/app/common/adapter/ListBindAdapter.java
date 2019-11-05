package net.yundingwei.dszw.app.common.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.yundingwei.dszw.BR;

import java.util.List;

/**
 * Created by Administrator on 2016/9/6.
 * 用来实现万能数据绑定适配器
 */
public class ListBindAdapter<T> extends BaseAdapter {
    protected Context context;
    protected Fragment fragment;
    protected List<T> list;
    private int layoutId;

    private int mSelectedIndex;


    public ListBindAdapter(Context context, Fragment fragment, List<T> list, int layoutId) {
        this.context = context;
        this.fragment = fragment;
        this.list = list;
        this.layoutId = layoutId;
    }


    public ListBindAdapter(Context context, List<T> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    public void replaceData(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setSelectedIndex(int position) {
        this.mSelectedIndex = position;
    }

    public int getSelectedIndex() {
        return this.mSelectedIndex;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, layoutId, null);
        }
        ViewDataBinding binding = DataBindingUtil.bind(convertView);
        bind(binding, getItem(position));
        convert(binding, getItem(position), position);
        return convertView;
    }

    public void bind(ViewDataBinding binding, @NonNull T item) {
        binding.setVariable(BR.item, item);

        if(fragment != null){
            binding.setVariable(BR.fragment, fragment);
        }
    }

    public void convert(ViewDataBinding binding, T item, int position) {

    }

    //用于支持自定义Layout的数据绑定
    public void notifyDataBind(ViewGroup parent) {
        for (int i = 0; i < getCount(); ++i) {
            View view = getView(i, null, parent);
            parent.addView(view, i);
        }
    }
}
