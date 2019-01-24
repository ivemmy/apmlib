package com.apm.model;

import java.util.HashMap;

/**
 * Created by JJY on 2016/4/17.
 */
public class EventInfo {
    private String eventId;
    private String eventNmae;
    private String componetId;
    private HashMap<String, String> params;
    private long duration;
    private long startTime;
    private String date;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventNmae() {
        return eventNmae;
    }

    public void setEventNmae(String eventNmae) {
        this.eventNmae = eventNmae;
    }

    public String getComponetId() {
        return componetId;
    }

    public void setComponetId(String componetId) {
        this.componetId = componetId;
    }


    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "EventInfo{" +
                "eventId='" + eventId + '\'' +
                ", eventNmae='" + eventNmae + '\'' +
                ", componetId='" + componetId + '\'' +
                ", params=" + params +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }
}
