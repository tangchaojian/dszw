package net.yundingwei.dszw.app.home.model;

/**
 * Created by Administrator on 2018/3/10 0010.
 */

public class PaiEntity {

    private int resId;

    private int defaultResId;

    private int max;//最大选牌数

    private boolean selected;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getDefaultResId() {
        return defaultResId;
    }

    public void setDefaultResId(int defaultResId) {
        this.defaultResId = defaultResId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
