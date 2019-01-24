package com.apm.service;

import com.apm.log.ApmLogger;
import com.apm.service.collect.CuCollector;
import com.apm.util.Config;
import com.apm.util.Converter;
import com.apm.util.Global;
import com.apm.util.SendDataUtil;
import com.apm.util.SystemUtils;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

/**
 * CPU使用率工作线程
 *
 * @author 王俊超
 */
public class CuCollectorTask implements Runnable {
    // 收集次数计数器
    private int count = 0;

    private static class Holder {
        private final static CuCollectorTask TASK = new CuCollectorTask();
    }

    private CuCollectorTask() {
        // 不能被初始化
    }

    public static CuCollectorTask getInstance() {
        return Holder.TASK;
    }

    @Override
    public synchronized void run() {
        // 如果开启了进行内存收集，就进行操作
        if (Config.cpuUsageCollection) {
            // 如果应用还在运行，并且还未达到指定收集的最大次数
            while (Global.isRunning() && count <= Config.cpuTimes) {
                // 进行一次内存收集
                CuCollector.add(SystemUtils.getInstance(null).getCpuUsage());
                count++;
                try {
                    // 休眠
                    TimeUnit.SECONDS.sleep(Config.cpuDuration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 数据已经收集完，需要发送数据 了
            JSONObject object = Converter.getCpuUsage(CuCollector.getData());
            ApmLogger.info(object);
            // 清空原有数据
            CuCollector.clear();
            // 记数器归0
            count = 0;
            //HttpUrlConnectionUtil.sendPostJsonObjectData(object, Constant.Turl.CPU_USAGE_ADD);
            SendDataUtil.SendData(object, 3);
//			ApmLogger.info(object);
            // 如果已经退出
            if (Global.isExist()) {
                // 设置新的启动标记为true，以待下次启动使用
                Global.setNewStart(true);
            }

        }
    }

}
