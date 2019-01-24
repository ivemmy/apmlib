package com.apm;

import android.app.Application;
import android.content.Context;

import com.apm.log.ApmLogger;
import com.apm.model.Regist;
import com.apm.model.Usage;
import com.apm.service.CuCollectorTask;
import com.apm.service.MuCollectorTask;
import com.apm.test.tool.ActivityMonitor;
import com.apm.util.Config;
import com.apm.util.Converter;
import com.apm.util.Global;
import com.apm.util.SendDataUtil;
import com.apm.util.SystemUtils;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by JJY on 2016/3/21.
 */
public class APM_Native {
    private static APM_Native instance = null;
    private SystemUtils systemUtils;
    private volatile boolean hasRunOnce = false;

    private APM_Native() {
    }

    ;

    public static APM_Native APM_NativeInstance() {
        if (instance == null) {
            synchronized (APM_Native.class) {
                if (instance == null)
                    instance = new APM_Native();
            }
        }
        return instance;
    }

    public void start(Application context) {
        systemUtils = SystemUtils.getInstance(context);
        startMonitor(context);
        setContext(context);

    }


    private Application setContext(Application application) {
        // 如果没有注册生命周其监听器，注册一个
        checkAndRunOnce(application);
        return application;
    }

    /**
     * 检查并且运行一次方法
     *
     * @param ctx 安卓应用上下文
     */
    private void checkAndRunOnce(Context ctx) {
        if (!hasRunOnce) {
            hasRunOnce = true;
            systemUtils = SystemUtils.getInstance(ctx);
            // 如果开启了登录记录或者使用记录就进行注册记录
            if (Config.appRegistCollection || Config.appBootCollection) {
                //new Thread(RegistTask.getIstance()).start();
                Regist regist = new Regist();
                regist.setDate(new Date());
                JSONObject object = Converter.getRegist(regist);
                ApmLogger.info(object);
                SendDataUtil.SendData(object, 8);
            }
        }
    }

    /**
     * 检查是否是新的一次开始
     */
    private void startMonitor(Application context) {
        // 是一次新的启动或者是上一次退出后启动的，
        System.out.println("Config.activityColection==" + Config.activityColection);
        System.out.println("Config.appBootCollection==" + Config.appBootCollection);
        System.out.println("Config.appRegistCollection==" + Config.appRegistCollection);
        System.out.println("Config.cpuUsageCollection==" + Config.cpuUsageCollection);
        System.out.println("Config.memoryUsageCollection==" + Config.memoryUsageCollection);
        System.out.println("Config.customEventCollection==" + Config.customEventCollection);
        System.out.println("Config.databaseCollection==" + Config.databaseCollection);
        System.out.println("Config.networkAccess==" + Config.networkAccess);

        if (Global.isNewStart()) {
            Global.setNewStart(false);
            // 如果开启了activity
            if (Config.activityColection) {
                context.registerActivityLifecycleCallbacks(ActivityMonitor.getInstance(context));
                //new Thread(ActivityCollectTask.getInstance()).start();
            }
            //如果开起来崩溃收集
/*            if(Config.crashCollection){
                CrashHandler crashHandler=CrashHandler.getInstance();
                crashHandler.init(context);

            }*/
            // 如果启内存收集就开始内存收集，OK
            if (Config.memoryUsageCollection) {
                new Thread(MuCollectorTask.getInstance()).start();
            }
            // 开始CPU使用率监控，OK
            if (Config.cpuUsageCollection) {
                new Thread(CuCollectorTask.getInstance()).start();
            }
            // 用户启动收集
            if (Config.appBootCollection) {
                //new Thread(UsageTask.getIstance()).start();
                Usage usage = new Usage();
                usage.setDate(new Date());
                // 设置启动时间
                usage.setStart(System.currentTimeMillis());
                usage.setEnd(Global.getLastPausedTime());
                JSONObject object = Converter.getUsage(usage);
                SendDataUtil.SendData(object, 7);
            }
/*            // 应用性能API监控
            // 网络访问性能监控
            if (Config.networkAccess) {
                //new Thread(HttpRequestTask.getInstance()).start();
            }
            if (Config.databaseCollection) {
                //new Thread(DatabaseTask.getInstance()).start();
            }
            if (Config.customEventCollection) {
                //System.out.println("customevnet="+ Config.customEventCollection);
                //new Thread(EventTask.getInstance()).start();
            }*/
        }
    }


}
