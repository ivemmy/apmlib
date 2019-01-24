package com.apm.service.collect;

import com.apm.model.EventInfo;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by JJY on 2016/4/17.
 */
public class EventCollector {
    // DatabaseInfo队列
    private final static BlockingDeque<EventInfo> //
            REQUEST = new LinkedBlockingDeque<EventInfo>(100);

    private EventCollector() {
    }

    /**
     * 添加一条数据
     * <p>
     * 数据
     */
    public static void add(EventInfo ei) {
        REQUEST.add(ei);
    }

    /**
     * 清空数据
     */
    public static void clear() {
        REQUEST.clear();
    }

    /**
     * 获取队首元素
     *
     * @return
     */
    public static EventInfo get() {
        return REQUEST.poll();
    }

    public static BlockingDeque<EventInfo> getData() {
        return REQUEST;
    }

    public static boolean isEmpty() {
        return REQUEST.isEmpty();
    }

}
