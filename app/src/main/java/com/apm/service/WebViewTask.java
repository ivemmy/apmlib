package com.apm.service;

import android.os.Environment;

import com.apm.log.ApmLogger;
import com.apm.util.Converter;
import com.apm.util.SendDataUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Hyman on 2016/7/14.
 */
public class WebViewTask {

    public static void send(JSONObject jsonObject) {

        JSONObject jo = Converter.getWebViewInfo(jsonObject);

        //writeToSD("jsonObject.txt", jo.toString());

        SendDataUtil.SendData(Converter.getWebViewInfo(jo), 11);

        //ApmLogger.info("WebViewTask-send: " + jo);
    }


    private static void writeToSD(String fileName, String s) {
        try {
            String pathName = Environment.getExternalStorageDirectory().getPath() + "/BetterUse/webview";
            File path = new File(pathName);
            File file = new File(pathName + "/" + fileName);
            if (!path.exists()) {
                path.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file, true);
            //Writer out = new OutputStreamWriter(stream, "UTF-8");
            byte[] buf = s.getBytes();
            stream.write(buf);
            //out.write(s);
            //out.close();
            stream.close();

            ApmLogger.info("write to sd card success, fileName: " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
