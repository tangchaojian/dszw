package net.yundingwei.dszw.app.home.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.List;
import net.yundingwei.dszw.BR;

/**
 * Created by Administrator on 2018/3/11 0011.
 */

public class PukeEntity extends BaseObservable{

    private List<PaiEntity> paiList;

    private List<PaiEntity> checkedList;//选中了的牌

    private boolean checked1;//智能选牌

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
}
