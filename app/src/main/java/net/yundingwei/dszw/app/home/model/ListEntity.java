package net.yundingwei.dszw.app.home.model;

import java.util.List;

/**
 * Created by Administrator on 2018/3/10 0010.
 */

public class ListEntity {

    private List<String> option;

    private int number;//type等于6

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<String> getOption() {
        return option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }
}
