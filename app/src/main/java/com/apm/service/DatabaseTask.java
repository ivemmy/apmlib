package com.apm.service;

import com.apm.log.ApmLogger;
import com.apm.model.DatabaseInfo;
import com.apm.service.collect.DatabaseCollector;
import com.apm.util.Config;
import com.apm.util.Converter;
import com.apm.util.Global;
import com.apm.util.SendDataUtil;

import org.json.JSONObject;

public class DatabaseTask implements Runnable {

    private static class Holder {
        private final static DatabaseTask TASK = new DatabaseTask();
    }

    private DatabaseTask() {
        // 不能被初始化
    }

    public static DatabaseTask getInstance() {
        return Holder.TASK;
    }

    @Override
    public void run() {
        if (Config.databaseCollection) {
            while (Global.isRunning()) {
                DatabaseInfo bi = DatabaseCollector.get();
                if (bi != null) {
                    JSONObject object = Converter.getDatabaseInfo(bi);
                    ApmLogger.info(object);
                    //HttpUrlConnectionUtil.sendPostJsonObjectData(object, Constant.Turl.DATABASE_ADD);
                    SendDataUtil.SendData(Converter.getDatabaseInfo(bi), 6);
                }
            }
        }
    }

}
