package com.apm.service;

import com.apm.log.ApmLogger;
import com.apm.util.Converter;
import com.apm.util.SendDataUtil;

import org.json.JSONObject;

/**
 * Created by Hyman on 2018/5/5.
 */

public class BlockTask {

    public static void send(JSONObject jsonObject) {

        JSONObject jo = Converter.getBlockInfo(jsonObject);

        String jstr = jo.toString();
        SendDataUtil.SendData(jo, 12);

        ApmLogger.info("BlockTask - send data to upload queue success");
    }

}
