package com.apm.service;

import com.apm.log.ApmLogger;
import com.apm.util.Converter;
import com.apm.util.SendDataUtil;

import org.json.JSONObject;

/**
 * Created by Hyman on 2018/5/5.
 */

public class MemoryLeakTask {

    public static void send(JSONObject jsonObject) {

        JSONObject jo = Converter.getBlockInfo(jsonObject);

        SendDataUtil.SendData(jo, 13);

        ApmLogger.info("MemoryLeakTask - send data to upload queue success");
    }

}
