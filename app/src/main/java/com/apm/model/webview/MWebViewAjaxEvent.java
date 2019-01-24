package com.apm.model.webview;

/**
 * Created by Hyman on 2016/7/9.
 */
public class MWebViewAjaxEvent extends EventBase {

    public static final String jsonPropName = "nest_ajax_tracking";
    public String request_id = "";
    public String sender_name = "WebView";
    public String link_url = "";
    public String target_name = "";
    public long timestamp = 0L;
    public String domain = "";
    public String uri = "";
    public long cb_start_time = 0L;
    public long cb_end_time = 0L;
    public String eve_type = "ajax";
    public long firstbyte_time = 0L;
    public long lastbyte_time = 0L;
    public long timeout = 0L;
    public int is_err = 0;
    public boolean is_asyn = true;
    public String code_text = "";
    public int rep_code = 200;
    public long rep_size = 0L;
    public String req_method = "";
    public long req_size = 0L;
    public long req_time = 0L;
    public String req_url = "";
    public long res_time = 0L;

    public String toString() {
        return "{" + this.q + "request_id" + this.q + ":" + this.q + this.request_id + this.q + "," + this.q + "timestamp" + this.q + ":" + this.timestamp + "," + this.q + "sender_name" + this.q + ":" + this.q + this.sender_name + this.q + "," + this.q + "target_name" + this.q + ":" + this.q + this.target_name + this.q + "," + this.q + "link_url" + this.q + ":" + this.q + this.link_url + this.q + "," + this.q + "domain" + this.q + ":" + this.q + this.domain + this.q + "," + this.q + "uri" + this.q + ":" + this.q + this.uri + this.q + "," + this.q + "code_text" + this.q + ":" + this.q + this.code_text + this.q + "," + this.q + "rep_size" + this.q + ":" + this.rep_size + "," + this.q + "cb_start_time" + this.q + ":" + this.cb_start_time + "," + this.q + "cb_end_time" + this.q + ":" + this.cb_end_time + "," + this.q + "firstbyte_time" + this.q + ":" + this.firstbyte_time + "," + this.q + "lastbyte_time" + this.q + ":" + this.lastbyte_time + "," + this.q + "req_method" + this.q + ":" + this.q + this.req_method + this.q + "," + this.q + "eve_type" + this.q + ":" + this.q + this.eve_type + this.q + "," + this.q + "timeout" + this.q + ":" + this.timeout + "," + this.q + "rep_code" + this.q + ":" + this.rep_code + "," + this.q + "req_time" + this.q + ":" + this.req_time + "," + this.q + "is_err" + this.q + ":" + this.is_err + "," + this.q + "res_time" + this.q + ":" + this.res_time + "," + this.q + "is_asyn" + this.q + ":" + (this.is_asyn ? "true" : "false") + "," + this.q + "req_size" + this.q + ":" + this.req_size + "," + this.q + "req_url" + this.q + ":" + this.q + this.req_url + this.q + "}";
    }
}