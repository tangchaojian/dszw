package net.yundingwei.dszw.app.home.model;

import android.databinding.Bindable;

import net.yundingwei.dszw.app.common.adapter.AdapterType;
import net.yundingwei.dszw.app.common.model.*;
import net.yundingwei.dszw.BR;

import java.util.List;

/**
 * Created by Administrator on 2018/3/8 0008.
 */

public class MenuEntity extends AdapterType{

    private String name;
    private String value;
    private int type;//1、文本；2、开关；3、列表；4、开关+列表；5、麻将选牌；6、扑克选牌, 7、跑胡子选牌

    private Object list;

    private boolean checked;
    private int max;//最大选牌数量

    private List<KeyValueEntity> data;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }

    public List<KeyValueEntity> getData() {
        return data;
    }

    public void setData(List<KeyValueEntity> data) {
        this.data = data;
    }

    @Bindable
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
    }

    @Bindable
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyPropertyChanged(BR.checked);
    }

    @Bindable
    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        notifyPropertyChanged(BR.max);
    }
}
