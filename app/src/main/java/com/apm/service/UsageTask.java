package com.apm.service;

import com.apm.model.Usage;
import com.apm.util.Converter;
import com.apm.util.Global;
import com.apm.util.SendDataUtil;

import org.json.JSONObject;

import java.util.Date;

/**
 * 应用使用收集器
 *
 * @author 王俊超
 */
public class UsageTask implements Runnable {

    private static Usage usage = new Usage();

    private UsageTask() {
        // 不要进行初始化
    }

    private static class Holder {
        private final static UsageTask TASK = new UsageTask();
    }

    public static UsageTask getIstance() {
        // 每次调用时就更新时间
        usage.setDate(new Date());
        // 设置启动时间
        usage.setStart(System.currentTimeMillis());
        return Holder.TASK;
    }

    public Usage getUsage() {
        return usage;
    }

    @Override
    public void run() {
/*		while (Global.isRunning()) {
			try {
				Thread.sleep(Config.SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
        // 设置运行结束的时间
        usage.setEnd(Global.getLastPausedTime());
        JSONObject object = Converter.getUsage(usage);
//		ApmLogger.info(object);
        // 添加数据
        //HttpUrlConnectionUtil.sendPostJsonObjectData(object, Constant.Turl.BOOT_USAGE_ADD);
        SendDataUtil.SendData(object, 7);
    }
}
