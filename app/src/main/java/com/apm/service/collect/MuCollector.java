package com.apm.service.collect;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 内存收集器
 *
 * @author 王俊超
 */
public class MuCollector {

    // 内使用队列
    private final static BlockingDeque<Integer> //
            MEMORY_USAGE = new LinkedBlockingDeque<Integer>(100);

    /**
     * 添加一条数据
     *
     * @param usage 数据
     */
    public static void add(int usage) {
        MEMORY_USAGE.add(usage);
    }

    /**
     * 清空数据
     */
    public static void clear() {
        MEMORY_USAGE.clear();
    }

    public static BlockingDeque<Integer> getData() {
        return MEMORY_USAGE;
    }
}
