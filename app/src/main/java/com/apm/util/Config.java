package com.apm.util;

/**
 * 配置类
 *
 * @author 王俊超
 */
public class Config {

    /**
     * 应用标识ID
     */
    public volatile static String appId = "85d4a553-ee8d-4136-80ab-2469adcae44d";

    /**
     * 是否进行Activity数据收集
     */
    public volatile static boolean activityColection = true;


    /**
     * Activity的最大收集深度是20
     */
    public volatile static int maxActivityDepth = 30;

    /**
     * Activity的最大收集时间，默认30分钟
     */
    public volatile static long maxCollectTime = 30 * 60 * 1000;

    /**
     * 最大的不活动时间，如果一个Activity已经退出，并且20秒后没有新的Activity生产，说明程序已经退出
     */
    public volatile static int maxNoOpTime = 16 * 1000;

    /**
     * 是否收集CPU使用信息
     */
    public volatile static boolean cpuUsageCollection = true;

    /**
     * CPU收集的时间间隔
     */
    public volatile static int cpuDuration = 5;

    /**
     * 收集的次数
     */
    public volatile static int cpuTimes = 20;

    /**
     * 是否收集内存使用信息
     */
    public volatile static boolean memoryUsageCollection = true;

    /**
     * 内存收集的时间间隔
     */
    public volatile static int memoryDuration = 5;

    /**
     * 内存收集的次数
     */
    public volatile static int memoryTimes = 20;

    /**
     * 是否对应用启动进行收集
     */
    public volatile static boolean appBootCollection = true;

    /**
     * 是否对应用注册进行收集
     */
    public volatile static boolean appRegistCollection = true;

    /**
     * 是否进行网络访问数据收集监测
     */
    public volatile static boolean networkAccess = true;

    /**
     * 数据库操作性能收集
     */
    public volatile static boolean databaseCollection = true;

    /**
     * 应用下载的渠道
     */
    public volatile static String way = "未知";


    /**
     * 线程休眠时间
     */
    public volatile static int SLEEP_TIME = 3000;

    /**
     * 崩溃收集
     */
    public volatile static boolean crashCollection = false;

    /**
     * 自定义事件收集
     */
    public volatile static boolean customEventCollection = true;


}
