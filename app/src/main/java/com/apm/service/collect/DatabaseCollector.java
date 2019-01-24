package com.apm.service.collect;

import com.apm.model.DatabaseInfo;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class DatabaseCollector {
    // DatabaseInfo队列
    private final static BlockingDeque<DatabaseInfo> //
            REQUEST = new LinkedBlockingDeque<DatabaseInfo>(100);

    private DatabaseCollector() {
    }

    /**
     * 添加一条数据
     * <p>
     * 数据
     */
    public static void add(DatabaseInfo hi) {
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
    public static DatabaseInfo get() {
        return REQUEST.poll();
    }

    public static BlockingDeque<DatabaseInfo> getData() {
        return REQUEST;
    }

}
