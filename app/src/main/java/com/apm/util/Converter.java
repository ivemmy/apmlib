package com.apm.util;

import com.apm.model.ActivityInfo;
import com.apm.model.DatabaseInfo;
import com.apm.model.EventInfo;
import com.apm.model.FragmentInfo;
import com.apm.model.HttpInfo;
import com.apm.model.Regist;
import com.apm.model.SimpleActivityInfo;
import com.apm.model.Usage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingDeque;

/**
 * 对象转换器
 *
 * @author 王俊超
 */
public class Converter {

    private static SystemUtils systemUtils;
    // 时期格式化对象
    private final static SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat SDF_TIME = new SimpleDateFormat("HH:mm:ss");

    private Converter() {
        // 不能被初始化
    }

    /**
     * 将一个ActivityInfo对象转换成Json对象
     *
     * @param ai 待转换的对象
     * @return Json对象
     */
    public static JSONObject getJson(ActivityInfo ai) {
        JSONObject object = new JSONObject();
        try {
            object.put("name", ai.getName());
            object.put("prior", ai.getFrom());
            object.put("createTime", ai.getBeforeCreate());
            object.put("loadTime", ai.getAfterCreate() - ai.getBeforeCreate());
            object.put("useTime", ai.getAfterPause() - ai.getAfterCreate());
            object.put("appName", ai.getAppName());
            object.put("appPackage", ai.getAppPackage());
            object.put("appVersion", ai.getAppVersion());
            object.put("manufacturer", ai.getManufacturer());
            object.put("osVersion", ai.getOsVersion());
            object.put("accessType", ai.getAccessType());
            object.put("appId", ai.getAppId());
            object.put("deviceId", ai.getDeviceId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     * 将FragmentInfo对象转换成对应的Json对象
     *
     * @param fi 待转换的FragmentInfo对象
     * @return 转换后的Json对象
     */
    public static JSONObject getJson(FragmentInfo fi) {
        JSONObject object = new JSONObject();
        try {
            object.put("name", fi.getName());
            object.put("wrapper", fi.getWraper());
            object.put("loadTime", fi.getAfterCreate() - fi.getBeforeCreate());
            object.put("appName", fi.getAppName());
            object.put("appPackage", fi.getAppPackage());
            object.put("appVersion", fi.getAppVersion());
            object.put("manufacturer", fi.getManufacturer());
            object.put("osVersion", fi.getOsVersion());
            object.put("accessType", fi.getAccessType());
            object.put("appId", fi.getAppId());
            object.put("deviceId", fi.getDeviceId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     * 将一个ActivityInfo对象转换成一个名值对象链表对象
     *
     * @param ai 待转换的对象
     * @return t 名值对象链表对象
     */
    @Deprecated
    /*public List<NameValuePair> getNVParis(ActivityInfo ai) {
        List<NameValuePair> nvPairs = new ArrayList<NameValuePair>();
        nvPairs.add(new BasicNameValuePair("name", ai.getName()));
        nvPairs.add(new BasicNameValuePair("prior", ai.getFrom()));
        nvPairs.add(new BasicNameValuePair("createTime", ai.getBeforeCreate() + ""));
        nvPairs.add(new BasicNameValuePair("loadTime", ai.getAfterCreate() - ai.getBeforeCreate() + ""));
        nvPairs.add(new BasicNameValuePair("useTime", ai.getAfterPause() - ai.getAfterCreate() + ""));
        return nvPairs;
    }*/

    /**
     * 复制ActivityInof对象
     *
     * @param ai 待转换对象
     * @return 复制后的对象
     */
    public static ActivityInfo copy(ActivityInfo ai) {
        ActivityInfo newAi = new ActivityInfo();

        newAi.setBeforeCreate(ai.getBeforeCreate());
        newAi.setAfterCreate(ai.getAfterCreate());
        newAi.setAfterPause(ai.getAfterPause());
        newAi.setName(ai.getName());
        newAi.setAppName(ai.getAppName());
        newAi.setAppPackage(ai.getAppPackage());
        newAi.setAppVersion(ai.getAppVersion());
        newAi.setManufacturer(ai.getManufacturer());
        newAi.setOsVersion(ai.getOsVersion());
        newAi.setAccessType(ai.getAccessType());
        newAi.setAppId(ai.getAppId());
        newAi.setDeviceId(ai.getDeviceId());

        if (ai.getFrom() == null) {
            newAi.setFrom(null);
        } else {
            ai.setFrom(new String(ai.getFrom()));
        }

        return ai;
    }

    public static SimpleActivityInfo getSimple(ActivityInfo ai) {

        if (systemUtils == null) {
            // 可以设置为null因为在这个方法执行之前已经执行过了
            systemUtils = SystemUtils.getInstance(null);
        }

        SimpleActivityInfo sai = new SimpleActivityInfo();
        sai.setName(ai.getName());
        sai.setCreateTime(ai.getBeforeCreate());
        return sai;
    }

    /**
     * 通过ActivityInfo对象获取SimpleActivityInfo对应的JSONObject对象
     *
     * @param ai ActivityInfo对象
     * @return SimpleActivityInfo对应的JSONObject对象
     */
    public static JSONObject getSimpleActivity(ActivityInfo ai) {
        JSONObject object = new JSONObject();
        try {
            object.put("name", ai.getName());
            object.put("createTime", ai.getBeforeCreate());
            // object.put("deviceId", SystemUtils.getInstance(null).getDeviceId());
            // object.put("appId", Config.appId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    public static JSONObject getSimpleActivity(String name, long createTime) {
        JSONObject object = new JSONObject();

        try {
            object.put("name", name);
            object.put("createTime", createTime);
            // object.put("deviceId", SystemUtils.getInstance(null).getDeviceId());
            // object.put("appId", Config.appId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static JSONObject getMemoryUsage(BlockingDeque<Integer> data) {
        JSONObject object = new JSONObject();
        SystemUtils instance = SystemUtils.getInstance(null);

        try {
            object.put("appName", instance.getAppName());
            object.put("appPackage", instance.getAppPackage());
            object.put("appVersion", instance.getAppVersion());
            object.put("manufacturer", instance.getManufacturer());
            object.put("osVersion", instance.getOsVersion());
            object.put("accessType", instance.getAccessType());
            object.put("appId", Config.appId);
            object.put("deviceId", instance.getDeviceId());
            JSONArray array = new JSONArray();
            // 添加内存使用数据
            if (data != null) {
                for (Integer i : data) {
                    array.put(i);
                }
            }

            object.put("data", array);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;

    }

    public static JSONObject getCpuUsage(BlockingDeque<Double> data) {

        JSONObject object = new JSONObject();
        SystemUtils instance = SystemUtils.getInstance(null);

        try {
            object.put("appName", instance.getAppName());
            object.put("appPackage", instance.getAppPackage());
            object.put("appVersion", instance.getAppVersion());
            object.put("manufacturer", instance.getManufacturer());
            object.put("osVersion", instance.getOsVersion());
            object.put("accessType", instance.getAccessType());
            object.put("appId", Config.appId);
            object.put("deviceId", instance.getDeviceId());
            JSONArray array = new JSONArray();
            // 添加内存使用数据
            if (data != null) {
                for (Double i : data) {
                    array.put(i);
                }
            }

            object.put("data", array);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    public static JSONObject getUsage(Usage usage) {
        JSONObject object = new JSONObject();
        SystemUtils instance = SystemUtils.getInstance(null);

        try {
            object.put("appName", instance.getAppName());
            object.put("appPackage", instance.getAppPackage());
            object.put("appVersion", instance.getAppVersion());
            object.put("manufacturer", instance.getManufacturer());
            object.put("osVersion", instance.getOsVersion());
            object.put("accessType", instance.getAccessType());
            object.put("appId", Config.appId);
            object.put("deviceId", instance.getDeviceId());
            object.put("date", SDF_DATE.format(usage.getDate()));
            object.put("time", SDF_TIME.format(usage.getDate()));
            object.put("loc", instance.getProvince());
            object.put("way", Config.way);
            object.put("operator", instance.getOperator());
            object.put("screen", instance.getScreenType());
            object.put("useTime", usage.getUseTime());
            object.put("moment", System.currentTimeMillis());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    // 获取注册对象
    public static JSONObject getRegist(Regist regist) {

        JSONObject object = new JSONObject();
        SystemUtils instance = SystemUtils.getInstance(null);

        try {
            object.put("appName", instance.getAppName());
            object.put("appPackage", instance.getAppPackage());
            object.put("appVersion", instance.getAppVersion());
            object.put("manufacturer", instance.getManufacturer());
            object.put("osVersion", instance.getOsVersion());
            object.put("accessType", instance.getAccessType());
            object.put("appId", Config.appId);
            object.put("deviceId", instance.getDeviceId());
            object.put("date", SDF_DATE.format(regist.getDate()));
            object.put("time", SDF_TIME.format(regist.getDate()));
            object.put("loc", instance.getProvince());
            object.put("way", Config.way);
            object.put("operator", instance.getOperator());
            object.put("screen", instance.getScreenType());
            object.put("moment", System.currentTimeMillis());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    // 获取请求对象
    public static JSONObject getHttpInfo(HttpInfo hi) {

        JSONObject object = new JSONObject();
        SystemUtils instance = SystemUtils.getInstance(null);

        try {
            object.put("appName", instance.getAppName());
            object.put("appPackage", instance.getAppPackage());
            object.put("appVersion", instance.getAppVersion());
            object.put("manufacturer", instance.getManufacturer());
            object.put("osVersion", instance.getOsVersion());
            object.put("accessType", instance.getAccessType());
            object.put("appId", Config.appId);
            object.put("deviceId", instance.getDeviceId());
            object.put("date", SDF_DATE.format(hi.getRequestStart()));
            object.put("time", SDF_TIME.format(hi.getRequestStart()));
            object.put("loc", instance.getProvince());
            object.put("way", Config.way);
            object.put("operator", instance.getOperator());
            object.put("status", hi.getStatus());
            object.put("method", hi.getType());
            String url = hi.getUri();
            if (url.contains("?")) {
                url = url.substring(0, url.indexOf("?"));
            }
            object.put("uri", url);
            object.put("duration", hi.getRequestEnd() - hi.getRequestStart());
            object.put("startTime", hi.getRequestStart());

            object.put("error", hi.getError());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    // 获取数据库访问对象
    public static JSONObject getDatabaseInfo(DatabaseInfo di) {
        JSONObject object = new JSONObject();
        SystemUtils instance = SystemUtils.getInstance(null);

        try {
            object.put("appName", instance.getAppName());
            object.put("appPackage", instance.getAppPackage());
            object.put("appVersion", instance.getAppVersion());
            object.put("manufacturer", instance.getManufacturer());
            object.put("osVersion", instance.getOsVersion());
            object.put("accessType", instance.getAccessType());
            object.put("appId", Config.appId);
            object.put("deviceId", instance.getDeviceId());

            object.put("sql", di.getSql());
            object.put("status", di.getStatus());
            object.put("type", di.getType());
            object.put("dbVersion", di.getVersion());
            object.put("duration", di.getEndTime() - di.getStartTime());
            object.put("startTime", di.getStartTime());

            object.put("error", di.getError());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static JSONObject getEventInfo(EventInfo ei) {
        JSONObject object = new JSONObject();
        SystemUtils instance = SystemUtils.getInstance(null);

        try {
            object.put("appName", instance.getAppName());
            object.put("appPackage", instance.getAppPackage());
            object.put("appVersion", instance.getAppVersion());
            object.put("manufacturer", instance.getManufacturer());
            object.put("osVersion", instance.getOsVersion());
            object.put("accessType", instance.getAccessType());
            object.put("appId", Config.appId);
            object.put("deviceId", instance.getDeviceId());
            object.put("eventId", ei.getEventId());
            object.put("date", SDF_DATE.format(new Date()));
            object.put("time", SDF_TIME.format(new Date()));
            if (ei.getComponetId() != null) {
                object.put("componentId", ei.getComponetId());
            }
            if (ei.getDuration() != 0) {
                object.put("duration", ei.getDuration());
            }
            object.put("eventName", ei.getEventNmae());
            object.put("params", new JSONObject(ei.getParams()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static JSONObject getWebViewInfo(JSONObject object) {
        try {
            SystemUtils instance = SystemUtils.getInstance(null);
            object.put("appName", instance.getAppName());
            object.put("appPackage", instance.getAppPackage());
            object.put("appVersion", instance.getAppVersion());
            object.put("manufacturer", instance.getManufacturer());
            object.put("osVersion", instance.getOsVersion());
            object.put("accessType", instance.getAccessType());
            object.put("appId", Config.appId);
            object.put("deviceId", instance.getDeviceId());
            object.put("date", SDF_DATE.format(new Date()));
            object.put("date_long", PMCTimer.getPMCTimeMilli(System.currentTimeMillis()));
            object.put("time", SDF_TIME.format(new Date()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 封装卡顿数据
     *
     * @param object
     * @return
     */
    public static JSONObject getBlockInfo(JSONObject object) {
        try {
            SystemUtils instance = SystemUtils.getInstance(null);
            object.put("appName", instance.getAppName());
            object.put("appPackage", instance.getAppPackage());
            object.put("appVersion", instance.getAppVersion());
            object.put("manufacturer", instance.getManufacturer());
            object.put("osVersion", instance.getOsVersion());
            object.put("accessType", instance.getAccessType());
            object.put("appId", Config.appId);
            object.put("deviceId", instance.getDeviceId());
            Date now = new Date();
            object.put("date", SDF_DATE.format(now));
            object.put("date_long", PMCTimer.getPMCTimeMilli(System.currentTimeMillis()));
            object.put("time", SDF_TIME.format(now));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
