package com.apm.model.webview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyman on 2016/7/9.
 */
public class MWebViewResourceEvent extends EventBase {

    public static final String jsonPropName = "nest_resource_tracking";
    public String request_id = "";
    public String target_name = "";
    public String link_url = "";
    public String sender_name = "WebView";
    public String domain = "";
    public String uri = "";
    public long timestamp = 0L;
    public long pageTime = 0L;
    public long responseStart = 0L;
    public long responseEnd = 0L;
    public long redirectStart = 0L;
    public long redirectEnd = 0L;
    public long loadEventStart = 0L;
    public long loadEventEnd = 0L;
    public long domainLookupStart = 0L;
    public long domainLookupEnd = 0L;
    public long fetchStart = 0L;
    public long connectStart = 0L;
    public long connectEnd = 0L;
    public long domInteractive = 0L;
    public long requestStart = 0L;
    public long domContentLoadedEventStart = 0L;
    public long domContentLoadedEventEnd = 0L;
    public long domLoading = 0L;
    public long domComplete = 0L;
    public long navigationStart = 0L;
    public long secureConnectionStart = 0L;
    public long unloadEventStart = 0L;
    public long unloadEventEnd = 0L;
    public List<String> error_list = new ArrayList();
    public List<String> resource_time = new ArrayList();

    public String toString() {
        return "{" + this.q + "request_id" + this.q + ":" + this.q + this.request_id + this.q + "," + this.q + "timestamp" + this.q + ":" + this.timestamp + "," + this.q + "sender_name" + this.q + ":" + this.q + this.sender_name + this.q + "," + this.q + "target_name" + this.q + ":" + this.q + this.target_name + this.q + "," + this.q + "link_url" + this.q + ":" + this.q + this.link_url + this.q + "," + this.q + "domain" + this.q + ":" + this.q + this.domain + this.q + "," + this.q + "uri" + this.q + ":" + this.q + this.uri + this.q + "," + this.q + "p" + this.q + ":" + this.pageTime + "," + this.q + "ress" + this.q + ":" + this.responseStart + "," + this.q + "rese" + this.q + ":" + this.responseEnd + "," + this.q + "rs" + this.q + ":" + this.redirectStart + "," + this.q + "re" + this.q + ":" + this.redirectEnd + "," + this.q + "ls" + this.q + ":" + this.loadEventStart + "," + this.q + "le" + this.q + ":" + this.loadEventEnd + "," + this.q + "dls" + this.q + ":" + this.domainLookupStart + "," + this.q + "dle" + this.q + ":" + this.domainLookupEnd + "," + this.q + "fs" + this.q + ":" + this.fetchStart + "," + this.q + "cs" + this.q + ":" + this.connectStart + "," + this.q + "ce" + this.q + ":" + this.connectEnd + "," + this.q + "di" + this.q + ":" + this.domInteractive + "," + this.q + "reqs" + this.q + ":" + this.requestStart + "," + this.q + "dcls" + this.q + ":" + this.domContentLoadedEventStart + "," + this.q + "dcle" + this.q + ":" + this.domContentLoadedEventEnd + "," + this.q + "dl" + this.q + ":" + this.domLoading + "," + this.q + "dc" + this.q + ":" + this.domComplete + "," + this.q + "ns" + this.q + ":" + this.navigationStart + "," + this.q + "scs" + this.q + ":" + this.secureConnectionStart + "," + this.q + "us" + this.q + ":" + this.unloadEventStart + "," + this.q + "ue" + this.q + ":" + this.unloadEventEnd + "," + this.q + "error_list" + this.q + ":" + this.error_list.toString() + "," + this.q + "resource_time" + this.q + ":" + this.resource_time.toString() + "}";
    }

    public String toResourceTimingString() {
        return "{" + this.q + "request_id" + this.q + ":" + this.q + this.request_id + this.q + "," + this.q + "timestamp" + this.q + ":" + this.timestamp + "," + this.q + "sender_name" + this.q + ":" + this.q + this.sender_name + this.q + "," + this.q + "target_name" + this.q + ":" + this.q + this.target_name + this.q + "," + this.q + "link_url" + this.q + ":" + this.q + this.link_url + this.q + "," + this.q + "domain" + this.q + ":" + this.q + this.domain + this.q + "," + this.q + "uri" + this.q + ":" + this.q + this.uri + this.q + "," + this.q + "error_list" + this.q + ":" + this.error_list.toString() + "," + this.q + "resource_time" + this.q + ":" + this.resource_time.toString() + "}";
    }

    public String toErrorString() {
        return "{" + this.q + "request_id" + this.q + ":" + this.q + this.request_id + this.q + "," + this.q + "timestamp" + this.q + ":" + this.timestamp + "," + this.q + "sender_name" + this.q + ":" + this.q + this.sender_name + this.q + "," + this.q + "target_name" + this.q + ":" + this.q + this.target_name + this.q + "," + this.q + "link_url" + this.q + ":" + this.q + this.link_url + this.q + "," + this.q + "domain" + this.q + ":" + this.q + this.domain + this.q + "," + this.q + "uri" + this.q + ":" + this.q + this.uri + this.q + "," + this.q + "error_list" + this.q + ":" + this.error_list.toString() + "," + this.q + "resource_time" + this.q + ":" + this.resource_time.toString() + "}";
    }
}
