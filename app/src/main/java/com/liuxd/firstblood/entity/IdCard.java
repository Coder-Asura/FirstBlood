package com.liuxd.firstblood.entity;

/**
 * Created by Liuxd on 2016/12/6 16:37.
 * 身份证实体类
 */

public class IdCard {
    private String area;
    private String sex;
    private String birthday;
    private String res;
    private String tips;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
