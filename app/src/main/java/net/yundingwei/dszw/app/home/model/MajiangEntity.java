package net.yundingwei.dszw.app.home.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import net.yundingwei.dszw.BR;

import java.util.List;

/**
 * Created by Administrator on 2018/3/11 0011.
 */

public class MajiangEntity extends BaseObservable{

    private List<PaiEntity> paiList;

    private List<PaiEntity> checkedList;//选中了的牌

    private boolean checked1;//中鸟1个
    private boolean checked2;//中鸟2个
    private boolean checked3;//中鸟3个
    private boolean checked4;//中鸟4个
    private boolean checked5;//智能选牌

    public List<PaiEntity> getPaiList() {
        return paiList;
    }

    public void setPaiList(List<PaiEntity> paiList) {
        this.paiList = paiList;
    }

    public List<PaiEntity> getCheckedList() {
        return checkedList;
    }

    public void setCheckedList(List<PaiEntity> checkedList) {
        this.checkedList = checkedList;
    }

    @Bindable
    public boolean isChecked1() {
        return checked1;
    }

    public void setChecked1(boolean checked1) {
        this.checked1 = checked1;
        notifyPropertyChanged(BR.checked1);
    }

    @Bindable
    public boolean isChecked2() {
        return checked2;
    }

    public void setChecked2(boolean checked2) {
        this.checked2 = checked2;
        notifyPropertyChanged(BR.checked2);
    }

    @Bindable
    public boolean isChecked3() {
        return checked3;
    }

    public void setChecked3(boolean checked3) {
        this.checked3 = checked3;
        notifyPropertyChanged(BR.checked3);
    }

    @Bindable
    public boolean isChecked4() {
        return checked4;
    }

    public void setChecked4(boolean checked4) {
        this.checked4 = checked4;
        notifyPropertyChanged(BR.checked4);
    }

    @Bindable
    public boolean isChecked5() {
        return checked5;
    }

    public void setChecked5(boolean checked5) {
        this.checked5 = checked5;
        notifyPropertyChanged(BR.checked5);
    }
}
