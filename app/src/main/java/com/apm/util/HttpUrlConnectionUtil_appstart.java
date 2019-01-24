package com.apm.util;

import android.database.sqlite.SQLiteDatabase;

import com.apm.model.HttpRequest;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by JJY on 2016/4/26.
 */
public class HttpUrlConnectionUtil_appstart {
    private static HttpURLConnection uRLConnection;
    private static BlockingDeque<HttpRequest> httpRequests = new LinkedBlockingDeque<HttpRequest>(250);

    static {
        new PostThread().start();
    }

    public static void sendPostFileData(int id, String jsoncontent, int type) {
        String response = "";
        System.out.println(id + "//sendPostFileData===" + jsoncontent);
        httpRequests.add(new HttpRequest(id, jsoncontent, type));
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
                    String urlAddress = getURL(httpRequest.getType());
                    System.out.println("aappmm===url=start=" + urlAddress);
                    String jsonString = httpRequest.getJsonObject();
                    System.out.println("aappmm===json=start=" + jsonString);
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
                        System.out.println("upload on app_start code==" + code);
                        if (code == 200) {
                            SQLiteDatabase db = SQLLiteInstance.getInstance();
                            String whereClause = "_id=?";//删除的条件
                            String[] whereArgs = {String.valueOf(httpRequest.getId())};//删除的条件参数
                            db.delete("apm", whereClause, whereArgs);//执行删除
                        }
                    } catch (Exception e) {
                        System.out.println("catch...not uploaded");
                    }
                }
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
                default:
                    return "";
            }
        }
    }
}
