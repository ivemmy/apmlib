package com.apm.service;

import com.apm.log.ApmLogger;
import com.apm.model.Regist;
import com.apm.util.Converter;
import com.apm.util.SendDataUtil;

import org.json.JSONObject;

import java.util.Date;

/**
 * 应用注册收集器
 *
 * @author 王俊超
 */
public class RegistTask implements Runnable {

    private static Regist regist = new Regist();

    private RegistTask() {
        // 不要进行初始化
    }

    private static class Holder {
        private final static RegistTask TASK = new RegistTask();
    }

    public static RegistTask getIstance() {
        // 每次调用时就更新时间
        regist.setDate(new Date());
        return Holder.TASK;
    }

    public Regist getRegist() {
        return regist;
    }


    @Override
    public void run() {
        JSONObject object = Converter.getRegist(regist);
        ApmLogger.info(object);
        // 添加数据
        //HttpUrlConnectionUtil.sendPostJsonObjectData(object, Constant.Turl.REGIST_ADD);
        SendDataUtil.SendData(object, 8);
    }
}
