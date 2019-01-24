package com.apm.model;

import java.util.Date;

/**
 * 应用一次使用的信息
 *
 * @author 王俊超
 */
public class Usage extends Base {

    /**
     * 使用的日期
     */
    private Date date;
    /**
     * 使用的位置
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

    /**
     * 应用启动的时间
     */
    private long start;
    /**
     * 应用关闭的时间
     */
    private long end;

    public Usage() {

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

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    /**
     * 应用一次的使用时间
     *
     * @return 使用时间（单位：分钟）
     */
    public double getUseTime() {
        return 1.0 * (this.end - this.start) / (1000 * 60);
    }

    @Override
    public String toString() {
        return "Usage [date=" + date + ", loc=" + loc + ", way=" + way + ", operator=" + operator + ", screen="
                + screen + "]";
    }

}
