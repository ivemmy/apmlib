package com.apm.block.model;

import java.util.ArrayList;

/**
 * Created by Hyman on 2018/5/5.
 */
public class BlockInfo {
    private String info;
    private float duration;
    private String datetime;
    private ArrayList<String> stacktrace;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public ArrayList<String> getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(ArrayList<String> stacktrace) {
        this.stacktrace = stacktrace;
    }

    @Override
    public String toString() {
        return "BlockInfo{" +
                "info='" + info + '\'' +
                ", duration=" + duration +
                ", datetime='" + datetime + '\'' +
                ", stacktrace=" + stacktrace +
                '}';
    }
}
