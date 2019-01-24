package com.apm.service.collect;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * CPU使用率收集器
 *
 * @author 王俊超
 */
public class CuCollector {

    // CPU用队列
    private final static BlockingDeque<Double> //
            CPU_USAGE = new LinkedBlockingDeque<Double>(100);

    /**
     * 添加一条数据
     *
     * @param usage 数据
     */
    public static void add(double usage) {
        CPU_USAGE.add(usage);
    }

    /**
     * 清空数据
     */
    public static void clear() {
        CPU_USAGE.clear();
    }

    public static BlockingDeque<Double> getData() {
        return CPU_USAGE;
    }
}
