package com.apm.service;

import com.apm.log.ApmLogger;
import com.apm.model.EventInfo;
import com.apm.service.collect.EventCollector;
import com.apm.util.Converter;
import com.apm.util.SendDataUtil;

import org.json.JSONObject;

/**
 * Created by JJY on 2016/4/17.
 */
public class EventTask implements Runnable {

    private static class Holder {
        private final static EventTask TASK = new EventTask();
    }

    private EventTask() {
        // 不能被初始化
    }

    public static EventTask getInstance() {
        return Holder.TASK;
    }

    @Override
    public void run() {
        while (true) {
            //System.out.println("eventTask=="+Global.isRunning());
            if (!EventCollector.isEmpty()) {
                EventInfo ei = EventCollector.get();
                if (ei != null) {
                    JSONObject object = Converter.getEventInfo(ei);
                    ApmLogger.info(object);
                    System.out.println("apmevnet==" + object);
                    //HttpUrlConnectionUtil.sendPostJsonObjectData(object, Constant.Turl.EVENT_ADD);
                    SendDataUtil.SendData(Converter.getEventInfo(ei), 10);
                }
            }
        }

    }
}
