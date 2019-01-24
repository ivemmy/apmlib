package com.apm.service.collect;

import com.apm.model.HttpInfo;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 请求Http请求收集队列
 *
 * @author 王俊超
 */
public class RequestCollector {
    // httpInfo队列
    private final static BlockingDeque<HttpInfo> //
            REQUEST = new LinkedBlockingDeque<HttpInfo>(100);

    /**
     * 添加一条数据
     * <p>
     * 数据
     */
    public static void add(HttpInfo hi) {
        REQUEST.add(hi);
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
    public static HttpInfo get() {
        return REQUEST.poll();
    }

    public static BlockingDeque<HttpInfo> getData() {
        return REQUEST;
    }
}
