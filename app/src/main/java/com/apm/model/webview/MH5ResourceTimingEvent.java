package com.apm.model.webview;

/**
 * Created by Hyman on 2016/7/9.
 */
public class MH5ResourceTimingEvent extends EventBase {

    public long connectStart = 0L;
    public long connectEnd = 0L;
    public long domainLookupStart = 0L;
    public long domainLookupEnd = 0L;
    public long duration = 0L;
    public String entryType = "";
    public long fetchStart = 0L;
    public String initiatorType = "";
    public String name = "";
    public long redirectStart = 0L;
    public long redirectEnd = 0L;
    public long requestStart = 0L;
    public long responseStart = 0L;
    public long responseEnd = 0L;
    public long secureConnectionStart = 0L;
    public long startTime = 0L;

    public String toString() {
        return "{" + this.q + "cs" + this.q + ":" + this.connectStart + "," + this.q + "ce" + this.q + ":" + this.connectEnd + "," + this.q + "dls" + this.q + ":" + this.domainLookupStart + "," + this.q + "dle" + this.q + ":" + this.domainLookupEnd + "," + this.q + "dur" + this.q + ":" + this.duration + "," + this.q + "et" + this.q + ":" + this.q + this.entryType + this.q + "," + this.q + "fs" + this.q + ":" + this.fetchStart + "," + this.q + "it" + this.q + ":" + this.q + this.initiatorType + this.q + "," + this.q + "name" + this.q + ":" + this.q + this.name + this.q + "," + this.q + "rs" + this.q + ":" + this.redirectStart + "," + this.q + "re" + this.q + ":" + this.redirectEnd + "," + this.q + "reqs" + this.q + ":" + this.requestStart + "," + this.q + "ress" + this.q + ":" + this.responseStart + "," + this.q + "rese" + this.q + ":" + this.responseEnd + "," + this.q + "scs" + this.q + ":" + this.secureConnectionStart + "," + this.q + "st" + this.q + ":" + this.startTime + "}";
    }
}