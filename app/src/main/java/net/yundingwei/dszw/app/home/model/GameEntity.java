package net.yundingwei.dszw.app.home.model;

import java.io.Serializable;

/**
 * 菜单实体类
 */
public class GameEntity implements Serializable{

    private String id;
    private String name;
    private String img;

    private String path_android;
    private String path_ios;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPath_android() {
        return path_android;
    }

    public void setPath_android(String path_android) {
        this.path_android = path_android;
    }

    public String getPath_ios() {
        return path_ios;
    }

    public void setPath_ios(String path_ios) {
        this.path_ios = path_ios;
    }
}
