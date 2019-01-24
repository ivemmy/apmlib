package com.apm.test.tool;

import com.apm.model.EventInfo;
import com.apm.util.Config;
import com.apm.util.Converter;
import com.apm.util.SendDataUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by JJY on 2016/4/17.
 */
public class APMEventProxy {
    private static Map<String, EventInfo> map = new ConcurrentHashMap<String, EventInfo>();

    public static void onEvent(String eventId, String eventName, HashMap<String, String> params) {
        if (Config.customEventCollection) {
            EventInfo eventInfo = new EventInfo();
            eventInfo.setEventId(eventId);
            eventInfo.setEventNmae(eventName);
            eventInfo.setParams(params);
            SendDataUtil.SendData(Converter.getEventInfo(eventInfo), 10);
        }
        //EventCollector.add(eventInfo);
    }

    public static void onEvent(String eventId, String eventName, String componentId, HashMap<String, String> params) {
        if (Config.customEventCollection) {
            EventInfo eventInfo = new EventInfo();
            eventInfo.setEventId(eventId);
            eventInfo.setEventNmae(eventName);
            eventInfo.setComponetId(componentId);
            eventInfo.setParams(params);
            SendDataUtil.SendData(Converter.getEventInfo(eventInfo), 10);
        }
        //EventCollector.add(eventInfo);
    }

    public static void onEventduration(String eventId, String eventName, HashMap<String, String> params, int duration) {
        if (Config.customEventCollection) {
            EventInfo eventInfo = new EventInfo();
            eventInfo.setEventId(eventId);
            eventInfo.setEventNmae(eventName);
            eventInfo.setParams(params);
            eventInfo.setDuration(duration);
            SendDataUtil.SendData(Converter.getEventInfo(eventInfo), 10);
        }
        //EventCollector.add(eventInfo);
    }

    public static void onEventduration(String eventId, String eventName, String componentId, HashMap<String, String> params, int duration) {
        if (Config.customEventCollection) {
            EventInfo eventInfo = new EventInfo();
            eventInfo.setEventId(eventId);
            eventInfo.setEventNmae(eventName);
            eventInfo.setParams(params);
            eventInfo.setComponetId(componentId);
            eventInfo.setDuration(duration);
            SendDataUtil.SendData(Converter.getEventInfo(eventInfo), 10);
        }
        //EventCollector.add(eventInfo);
    }

    public static void onEventStart(String eventId) {
        if (Config.customEventCollection) {
            EventInfo eventInfo = new EventInfo();
            eventInfo.setEventId(eventId);
            eventInfo.setStartTime(System.currentTimeMillis());
            map.put(eventId, eventInfo);
        }
    }

    public static void onEventFinish(String eventId, String eventName, HashMap<String, String> params) {
        if (Config.customEventCollection) {
            EventInfo eventInfo = map.get(eventId);
            map.remove(eventId);
            eventInfo.setEventNmae(eventName);
            eventInfo.setParams(params);
            eventInfo.setDuration(System.currentTimeMillis() - eventInfo.getStartTime());
            //EventCollector.add(eventInfo);
            SendDataUtil.SendData(Converter.getEventInfo(eventInfo), 10);
        }

    }

    public static void onEventFinish(String eventId, String eventName, String componentId, HashMap<String, String> params) {
        if (Config.customEventCollection) {
            EventInfo eventInfo = map.get(eventId);
            map.remove(eventId);
            eventInfo.setEventNmae(eventName);
            eventInfo.setComponetId(componentId);
            eventInfo.setParams(params);
            eventInfo.setDuration(System.currentTimeMillis() - eventInfo.getStartTime());
            //EventCollector.add(eventInfo);
            SendDataUtil.SendData(Converter.getEventInfo(eventInfo), 10);
        }
    }
}
