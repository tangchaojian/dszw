package net.yundingwei.dszw.app.home.model;


/**
 * 响应实体类
 */
public class ResEntity {

    private int error;//0成功 其它失败
    private String msg;//描述信息

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
