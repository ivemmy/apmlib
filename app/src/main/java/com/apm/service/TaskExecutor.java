package com.apm.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 数据发送类，专门处理数据发送
 *
 * @author 王俊超
 */
public class TaskExecutor {
    /**
     * 线程池执行器，默认创建两个线程
     */
    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    /**
     * 静态内部类，帮助创建单例模式
     *
     * @author 王俊超
     */
    private final static class Holder {
        private static final TaskExecutor INSTANCE = new TaskExecutor();
    }

    /**
     * 获取数据发送对象
     *
     * @return 数据发送对象
     */
    public static TaskExecutor getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 任务执行方法
     *
     * @param runnable 执行任任务的对象
     */
    public void exectTask(Runnable runnable) {
        executor.execute(runnable);
    }

    /**
     * 关闭数据发布中心
     */
    public void shutDown() {
        executor.shutdown();
    }


}
