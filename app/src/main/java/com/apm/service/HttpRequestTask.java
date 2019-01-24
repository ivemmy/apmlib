package com.apm.service;

import com.apm.model.HttpInfo;
import com.apm.service.collect.RequestCollector;
import com.apm.util.Config;
import com.apm.util.Converter;
import com.apm.util.Global;
import com.apm.util.SendDataUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpRequestTask implements Runnable {

    private static class Holder {
        private final static HttpRequestTask TASK = new HttpRequestTask();
    }

    private HttpRequestTask() {
        // 不能被初始化
    }

    public static HttpRequestTask getInstance() {
        return Holder.TASK;
    }

    @Override
    public void run() {

        if (Config.networkAccess) {

            while (Global.isRunning()) {

                HttpInfo hi = RequestCollector.get();
                if (hi != null) {
                    JSONObject object = Converter.getHttpInfo(hi);
                    try {
                        String uriStr = (String) object.get("uri");
                        // 不包含这个ip-api接口
                        if (uriStr == null || !uriStr.contains("ip-api")) {
                            //HttpUrlConnectionUtil.sendPostJsonObjectData(object, Constant.Turl.NETWORK_ADD);
                            SendDataUtil.SendData(Converter.getHttpInfo(hi), 5);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

}
