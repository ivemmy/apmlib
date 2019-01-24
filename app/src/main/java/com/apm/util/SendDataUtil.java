package com.apm.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.apm.APMInstance;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by JJY on 2016/4/26.
 */
public class SendDataUtil {
    /**
     * @param jsonstr
     * @param type    0-activity
     *                1-activityRoute
     *                2-fragment
     *                3-cpu
     *                4-memory
     *                5-http
     *                6-database
     *                7-usage
     *                8-regist
     *                9-crash
     *                10-event
     */
    public static void SendData(String jsonstr, int type) {
        System.out.println(type + "///senddata==" + jsonstr);
        if (APMInstance.getInstance().getSendStrategy() == 0)
            HttpUrlConnectionUtil.sendPostJsonObjectData(jsonstr, type);
        else if (APMInstance.getInstance().getSendStrategy() == 1)
            string2SQLLite(jsonstr, type);

    }

    public static void SendData(JSONObject jsonObject, int type) {
        SendData(jsonObject.toString(), type);
    }

    public static void SendData(JSONArray jsonArray, int type) {
        SendData(jsonArray.toString(), type);
    }

    private static boolean string2SQLLite(String jsonstr, int type) {
        if (jsonstr == null)
            return false;
        boolean flag = true;
        try {
            SQLiteDatabase db = SQLLiteInstance.getInstance();
            ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据cv.put("username","Jack Johnson");//添加用户名
            cv.put("jsoncontent", jsonstr);
            cv.put("type", type);
            db.insert("apm", null, cv);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     *
     * @param type
     * @return
     *//*
    private static String getSDCardPath(int type){
        String basepath= Environment.getExternalStorageDirectory().getPath()+"/apmll";
        switch (type){
            case 0:
                return basepath+"/activity";
            case 1:
                return basepath+"/activityRoute";
            case 2:
                return basepath+"/fragment";
            case 3:
                return basepath+"/cpu";
            case 4:
                return basepath+"/memory";
            case 5:
                return basepath+"/http";
            case 6:
                return basepath+"/database";
            case 7:
                return basepath+"/usage";
            case 8:
                return basepath+"/regist";
            case 9:
                return basepath+"/crash";
            case 10:
                return basepath+"/event";
            default:
                return "";
        }
    }*/




/*   private static boolean string2File(String res,int type) {

        String filename="";
        String path="";
        String urlAddress=getSDCardPath(type);
        if(urlAddress!=null) {
            filename=urlAddress.substring(urlAddress.lastIndexOf("/"));
            path= Constant.File.DATAPATH+"/"+filename+"/"+System.currentTimeMillis()+".txt";
        }
        System.out.println("string2File=="+path);
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(path);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }*/
}
