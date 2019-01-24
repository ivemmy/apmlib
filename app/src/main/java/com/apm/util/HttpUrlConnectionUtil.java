package com.apm.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.apm.APMInstance;
import com.apm.model.HttpRequest;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by JJY on 2016/3/31.
 */
public class HttpUrlConnectionUtil {
    private static HttpURLConnection uRLConnection;
    private static BlockingDeque<HttpRequest> httpRequests = new LinkedBlockingDeque<HttpRequest>(250);

    static {
        new PostThread().start();
    }

    public static void sendPostJsonObjectData(String jsonString, int type) {
        //System.out.println("sendPostJsonObjectData=="+jsonString);
        String response = "";
        if (APMInstance.getInstance().getSendStrategy() == 0)
            httpRequests.add(new HttpRequest(jsonString, type));
    }

    private static boolean string2SQLLite(HttpRequest httpRequest) {
        if (httpRequest == null)
            return false;
        boolean flag = true;
        SQLiteDatabase db = SQLLiteInstance.getInstance();
        ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据cv.put("username","Jack Johnson");//添加用户名
        cv.put("jsoncontent", httpRequest.getJsonObject());
        cv.put("type", httpRequest.getType());
        db.insert("apm", null, cv);
        return flag;
    }


    static class PostThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    this.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (httpRequests.size() != 0) {
                    HttpRequest httpRequest = httpRequests.remove();
                    upload(httpRequest);
                }
            }
        }

        private void upload(HttpRequest httpRequest) {
            String urlAddress = getURL(httpRequest.getType());
            String jsonString = httpRequest.getJsonObject();
            System.out.println("upload===" + jsonString);
            System.out.println("upload===" + urlAddress);
            try {
                URL url = new URL(urlAddress);
                uRLConnection = (HttpURLConnection) url.openConnection();
                //uRLConnection.setDoInput(true);
                uRLConnection.setDoOutput(true);
                uRLConnection.setRequestMethod("POST");
                uRLConnection.setUseCaches(false);
                uRLConnection.setConnectTimeout(5000);
                uRLConnection.setRequestProperty("Content-Type", "application/json");
                uRLConnection.connect();
                PrintWriter outprint = new PrintWriter(new OutputStreamWriter(
                        uRLConnection.getOutputStream(), "UTF-8"));
                outprint.write(jsonString);
                outprint.flush();
                outprint.close();
                int code = uRLConnection.getResponseCode();
                //System.out.println("upload code=="+code);
                if (code != 200) {
                    string2SQLLite(httpRequest);
                }
            } catch (Exception e) {
                string2SQLLite(httpRequest);
            }
        }

        private static String getURL(int type) {
            switch (type) {
                case 0:
                    return Constant.Turl.ACTIVITY_ADD;
                case 1:
                    return Constant.Turl.ROUTE_ADD;
                case 2:
                    return Constant.Turl.FRAGMENT_ADD;
                case 3:
                    return Constant.Turl.CPU_USAGE_ADD;
                case 4:
                    return Constant.Turl.MEMORY_USAGE_ADD;
                case 5:
                    return Constant.Turl.NETWORK_ADD;
                case 6:
                    return Constant.Turl.DATABASE_ADD;
                case 7:
                    return Constant.Turl.BOOT_USAGE_ADD;
                case 8:
                    return Constant.Turl.REGIST_ADD;
                case 9:
                    return Constant.Turl.CRACK_REPORT;
                case 10:
                    return Constant.Turl.EVENT_ADD;
                case 11:
                    return Constant.Turl.WEBVIEW_ADD;
                case 12:
                    return Constant.Turl.BLOCK_ADD;
                case 13:
                    return Constant.Turl.LEAK_ADD;
                default:
                    return "";
            }
        }
    }


}
