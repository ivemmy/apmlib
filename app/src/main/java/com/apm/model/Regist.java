package com.apm.model;

import java.util.Date;

/**
 * 应用一次使用的信息
 *
 * @author 王俊超
 */
public class Regist extends Base {

    /**
     * 注册的日期
     */
    private Date date;
    /**
     * 注册的位置
     */
    private String loc;
    /**
     * 应用的渠道
     */
    private String way;
    /**
     * 应用的运营商
     */
    private String operator;
    /**
     * 手机屏幕大小
     */
    private String screen;


    public Regist() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    @Override
    public String toString() {
        return "Usage [date=" + date + ", loc=" + loc + ", way=" + way + ", operator=" + operator + ", screen="
                + screen + "]";
    }

}
