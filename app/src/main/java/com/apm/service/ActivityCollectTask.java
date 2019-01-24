package com.apm.service;

import com.apm.log.ApmLogger;
import com.apm.model.ActivityInfo;
import com.apm.model.FragmentInfo;
import com.apm.service.collect.ActivityCollector;
import com.apm.service.collect.FragmentCollector;
import com.apm.util.Config;
import com.apm.util.Converter;
import com.apm.util.Global;
import com.apm.util.SendDataUtil;
import com.apm.util.SystemUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Deque;
import java.util.List;

/**
 * 数据发送类，收集数据，进行发送
 *
 * @author 王俊超
 */
public class ActivityCollectTask implements Runnable {

    private ActivityCollectTask() {
        // 不对直接创建对象
    }

    /**
     * 静态内部内，辅助创建单例模式，同进保证线程安全
     *
     * @author 王俊超
     */
    private static class Holder {
        private final static ActivityCollectTask TASK = new ActivityCollectTask();
    }

    /**
     * 获取任务对象
     *
     * @return
     */
    public static ActivityCollectTask getInstance() {
        return Holder.TASK;
    }

    @Override
    public void run() {
        // ApmLogger.info("public void run() ");

        while (true) {
            // 如果应用正在运行就发送运行数据

            while (Global.isRunning() && !ActivityCollector.isFinished()) {
                // running();
                try {
                    Thread.sleep(Config.SLEEP_TIME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //}
                sendRemainedActivityInfo();
                sendRemainedFragmentInfo();
/*			if(ActivityCollector.isRoutetimeout()) {
				ActivityCollector.setLastResumeTime();
				sendActivityRoute();
			}*/
                if (Global.isOnPaused()) {
                    sendActivityRoute();
                }
                // 如果应用已经退出或者已经过了监控完成时间就执行结束方法
                //if (Global.isExist() || ActivityCollector.isFinished()) {
                //if(!Global.isExist()){
                //System.out.println("Global.isExist()=="+Global.isExist());
                // 设置新的启动标记为true，以待下次启动使用
/*				if (Global.isExist()) {
					Global.setNewStart(true);
				}*/
                //	exist();
                //}
            }
        }
    }

    // /**
    // * APP运行时调用的方法
    // */
    // private void running() {
    // sendActivityData();
    // sendFragmentData();
    // }

    /**
     * APP进入到后台时运行的方法
     */
    private void exist() {        // 发送执行流数据
        sendActivityRoute();
        // 发送剩余的ActivityInfo数据
        sendRemainedActivityInfo();
        // 发送剩余的Fragment数据
        sendRemainedFragmentInfo();
        // 重置收集器
        ActivityCollector.reset();
    }

    /**
     * 向服务器端发送收集好的Activity数据
     */
    private void sendActivityData() {
        ActivityInfo ai = ActivityCollector.takeNonLast();
        if (ai != null) {
//			ApmLogger.info(ai);
            //HttpUrlConnectionUtil.sendPostJsonObjectData(Converter.getJson(ai), Constant.Turl.ACTIVITY_ADD);
            SendDataUtil.SendData(Converter.getJson(ai), 0);
        }
    }

    /**
     * 向服务器发送收集好的Fragment数据
     */
    private void sendFragmentData() {
        FragmentInfo fi = FragmentCollector.get();
        if (fi != null) {
            // ApmLogger.info(fi);
            //HttpUrlConnectionUtil.sendPostJsonObjectData(Converter.getJson(fi), Constant.Turl.FRAGMENT_ADD);
            SendDataUtil.SendData(Converter.getJson(fi), 2);
        }
    }

    /**
     * 发送ActivityInfo信息
     */
    private void sendActivityRoute() {
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

    /**
     * 发送剩余的ActivityInfo数据
     */
    private void sendRemainedActivityInfo() {
        Deque<ActivityInfo> aiList = ActivityCollector.getAll();
        ActivityInfo ai;
        while (!aiList.isEmpty()) {
            ai = aiList.remove();
            //HttpUrlConnectionUtil.sendPostJsonObjectData(Converter.getJson(ai), Constant.Turl.ACTIVITY_ADD);
            SendDataUtil.SendData(Converter.getJson(ai), 0);
            ApmLogger.info(ai);
        }

    }

    /**
     * 发送剩余的FragmentInfo对象
     */
    private void sendRemainedFragmentInfo() {
        Deque<FragmentInfo> fiList = FragmentCollector.getAll();
        FragmentInfo fi;
        while (!fiList.isEmpty()) {
            fi = fiList.remove();
            //HttpUrlConnectionUtil.sendPostJsonObjectData(Converter.getJson(fi), Constant.Turl.FRAGMENT_ADD);
            SendDataUtil.SendData(Converter.getJson(fi), 2);
            ApmLogger.info(fi);
        }
        // 清空数据
        FragmentCollector.clear();
    }
}
