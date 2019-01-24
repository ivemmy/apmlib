package com.apm.test.tool;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.apm.log.ApmLogger;
import com.apm.model.ActivityInfo;
import com.apm.service.collect.ActivityCollector;
import com.apm.util.Config;
import com.apm.util.ContextHolder;
import com.apm.util.Converter;
import com.apm.util.Global;
import com.apm.util.SendDataUtil;
import com.apm.util.SystemUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
public class ActivityMonitor implements Application.ActivityLifecycleCallbacks {
    private static SystemUtils systemUtils;
    private static ActivityMonitor monitor = null;
    private static boolean flag = false;

    protected ActivityMonitor() {
    }

    public static ActivityMonitor getInstance(Context context) {
        if (monitor == null) {
            try {
                systemUtils = SystemUtils.getInstance(context);
                monitor = new ActivityMonitor();
                //context.registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return monitor;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ActivityInfo singleActivityInfo = null;
        flag = true;
        singleActivityInfo = new ActivityInfo();
        singleActivityInfo.setBeforeCreate(System.currentTimeMillis());
        singleActivityInfo.setName(activity.getLocalClassName());
        singleActivityInfo.setAppName(systemUtils.getAppName());
        singleActivityInfo.setAppVersion(systemUtils.getAppVersion());
        singleActivityInfo.setOsVersion(systemUtils.getOsVersion());
        singleActivityInfo.setManufacturer(systemUtils.getManufacturer());
        singleActivityInfo.setAppPackage(systemUtils.getAppPackage());
        singleActivityInfo.setDeviceId(systemUtils.getDeviceId());
        singleActivityInfo.setAppId(Config.appId);
/*        if (ActivityCollector.getCurrentActivityInfo() == null) {
            singleActivityInfo.setFrom(null);
        } else {
            singleActivityInfo.setFrom(new String(ActivityCollector.getCurrentActivityInfo().getName()));
        }*/

        singleActivityInfo.setFrom(ActivityCollector.getNowActivityName());
        ActivityCollector.setCurrent(singleActivityInfo);

    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Global.setOnPaused(false);
        if (flag == true) {
            ActivityInfo singleActivityInfo = ActivityCollector.getCurrentActivityInfo();
            singleActivityInfo.setAfterCreate(System.currentTimeMillis());
            ActivityCollector.setNowActivityName(activity.getLocalClassName());
        }
        //ActivityCollector.setCurrent(singleActivityInfo);
        //Activity路径

        ActivityInfo activityInfo = new ActivityInfo();
        activityInfo.setName(activity.getLocalClassName());
        activityInfo.setBeforeCreate(System.currentTimeMillis());
        ActivityCollector.addtoRoute(activityInfo);
        //SendDataUtil.SendData(Converter.getJson(activityInfo),0);


    }

    @Override
    public void onActivityPaused(Activity activity) {
        Global.setOnPaused(true);
        ActivityInfo singleActivityInfo = null;
        singleActivityInfo = ActivityCollector.getCurrentActivityInfo();
        if (singleActivityInfo != null && flag) {
            singleActivityInfo.setAfterPause(System.currentTimeMillis());
            //ActivityCollector.put(singleActivityInfo);
            SendDataUtil.SendData(Converter.getJson(singleActivityInfo), 0);
            flag = false;
            //ActivityCollector.setCurrent(null);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (!isAppOnForeground()) {
            sendActivityRoute();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

/*
    private static BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("onReceive......");
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                System.out.println("onReceive_reson=="+reason);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    //表示按了home键,程序到了后台
                    Toast.makeText(ContextHolder.context, "home", Toast.LENGTH_SHORT).show();
                    sendActivityRoute();
                }else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){
                    //表示长按home键,显示最近使用的程序列表
                }
            }
        }
    };
*/

    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) ContextHolder.context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = ContextHolder.context.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    private static void sendActivityRoute() {
        // 如果有数据就发送数据
        List<JSONObject> saiList = ActivityCollector.getRoute();
        if (saiList.size() > 0) {
            try {
                JSONArray array = new JSONArray();
                for (JSONObject jsonObject : saiList) {
                    array.put(jsonObject);
                }

                // 创建一个尾结点表示结束
                JSONObject sai = new JSONObject();
                sai.put("name", "null");
                sai.put("createTime", System.currentTimeMillis());
                array.put(sai);

                // 清空原有的数据
                saiList.clear();
                JSONObject route = new JSONObject();
                route.put("appId", Config.appId);
                route.put("deviceId", SystemUtils.getInstance(null).getDeviceId());
                route.put("route", array);
                ApmLogger.info(route); // 记录日志
                //HttpUrlConnectionUtil.sendPostJsonObjectData(route, Constant.Turl.ROUTE_ADD);
                SendDataUtil.SendData(route, 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
