package com.apm.model.webview;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hyman on 2016/7/9.
 */
public class EventBase implements Delayed {
    public String q = "\"";
    public boolean done = false;

    public int compareTo(Delayed o) {
        return 0;
    }

    public long getDelay(TimeUnit unit) {
        return 0L;
    }
}
