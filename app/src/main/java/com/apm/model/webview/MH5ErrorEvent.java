package com.apm.model.webview;

/**
 * Created by Hyman on 2016/7/9.
 */
public class MH5ErrorEvent extends EventBase {
    public String request_id = "";
    public long time = 0L;
    public String url = "";
    public String msg = "";
    public long line = 0L;
    public long column = 0L;
    public String type = "";
    public String stack = "";

    public String toString() {
        return "{" + this.q + "request_id" + this.q + ":" + this.q + this.request_id + this.q + "," + this.q + "time" + this.q + ":" + this.time + "," + this.q + "url" + this.q + ":" + this.q + this.url + this.q + "," + this.q + "msg" + this.q + ":" + this.q + this.msg + this.q + "," + this.q + "line" + this.q + ":" + this.line + "," + this.q + "column" + this.q + ":" + this.column + "," + this.q + "type" + this.q + ":" + this.q + this.type + this.q + "," + this.q + "stack" + this.q + ":" + this.q + this.stack + this.q + "}";
    }
}
