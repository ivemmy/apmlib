package com.apm.model.webview;

/**
 * Created by Hyman on 2016/7/9.
 */
public class MUserEvent extends MBasic {

    public static final String jsonPropName = "nest_event_tracking";
    public long timestamp;
    public String event_type;
    public String event_type_raw;
    public String target_name;
    public String target_name_raw;
    public String sender_name;
    public String sender_name_raw;
    public String action_name;
    public String action_name_raw;
    public String action_tile;
    public String action_tile_raw;
    public String superview_name;
    public String superview_name_raw;
    public String extra_info = "N";
    public String extra_info_raw = "N";
    public String event_tag;
    public String event_tag_raw;
    public String link_url = "N";
    public String link_url_raw = "N";
    public static String globalEventTag;
    public static MUserEvent globalUserEvent;

    public String toString() {
        return "{" + this.q + "timestamp" + this.q + ":" + this.timestamp + "," + this.q + "event_type" + this.q + ":" + this.q + this.event_type + this.q + "," + this.q + "event_type_raw" + this.q + ":" + this.q + this.event_type_raw + this.q + "," + this.q + "target_name" + this.q + ":" + this.q + this.target_name + this.q + "," + this.q + "target_name_raw" + this.q + ":" + this.q + this.target_name_raw + this.q + "," + this.q + "sender_name" + this.q + ":" + this.q + this.sender_name + this.q + "," + this.q + "sender_name_raw" + this.q + ":" + this.q + this.sender_name_raw + this.q + "," + this.q + "action_name" + this.q + ":" + this.q + this.action_name + this.q + "," + this.q + "action_name_raw" + this.q + ":" + this.q + this.action_name_raw + this.q + "," + this.q + "action_tile" + this.q + ":" + this.q + this.action_tile + this.q + "," + this.q + "action_tile_raw" + this.q + ":" + this.q + this.action_tile_raw + this.q + "," + this.q + "superview_name" + this.q + ":" + this.q + this.superview_name + this.q + "," + this.q + "superview_name_raw" + this.q + ":" + this.q + this.superview_name_raw + this.q + "," + this.q + "extra_info" + this.q + ":" + this.q + this.extra_info + this.q + "," + this.q + "extra_info_raw" + this.q + ":" + this.q + this.extra_info_raw + this.q + "," + this.q + "link_url" + this.q + ":" + this.q + this.link_url + this.q + "," + this.q + "link_url_raw" + this.q + ":" + this.q + this.link_url_raw + this.q + "," + this.q + "event_tag" + this.q + ":" + this.q + this.event_tag + this.q + "," + this.q + "event_tag_raw" + this.q + ":" + this.q + this.event_tag_raw + this.q + "}";
    }
}