package net.yundingwei.dszw.app.common.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.yundingwei.dszw.BR;

import java.util.List;

/**
 * Created by Administrator on 2016/9/6.
 * 用来实现万能数据绑定适配器
 */
public class ListTypeBindAdapter<T extends AdapterType> extends BaseAdapter {
    protected Context context;
    protected Fragment fragment;
    protected List<T> list;
    private SparseArray<Integer> layoutIds;

    private int mSelectedIndex;


    public ListTypeBindAdapter(Context context, Fragment fragment, List<T> list, SparseArray<Integer> layoutIds) {
        this.context = context;
        this.fragment = fragment;
        this.list = list;
        this.layoutIds = layoutIds;
    }


    public ListTypeBindAdapter(Context context, Fragment fragment, SparseArray<Integer> layoutIds) {
        this.context = context;
        this.fragment = fragment;
        this.layoutIds = layoutIds;
    }

    public ListTypeBindAdapter(Context context, List<T> list, SparseArray<Integer> layoutIds) {
        this.context = context;
        this.list = list;
        this.layoutIds = layoutIds;
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
    public int getViewTypeCount() {
        return this.layoutIds.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, this.layoutIds.get(this.list.get(position).getViewType()), null);
        }
        ViewDataBinding binding = DataBindingUtil.bind(convertView);
        bind(binding, getItem(position));
        convert(binding, getItem(position));
        convert(position, binding, getItem(position));
        return convertView;
    }

    public void bind(ViewDataBinding binding, @NonNull T item) {
        binding.setVariable(BR.item, item);

        if(fragment != null){
            binding.setVariable(BR.fragment, fragment);
        }
    }

    public void convert(ViewDataBinding binding, T item) {

    }

    public void convert(int position, ViewDataBinding binding, T item) {

    }

    public void updateView(int itemIndex){

    }

    //用于支持自定义Layout的数据绑定
    public void notifyDataBind(ViewGroup parent) {
        for (int i = 0; i < getCount(); ++i) {
            View view = getView(i, null, parent);
            parent.addView(view, i);
        }
    }
}
