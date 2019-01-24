package com.apm;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apm.block.BlockWatcher;
import com.apm.util.Constant;
import com.apm.util.ContextHolder;
import com.apm.util.FileUtils;
import com.apm.util.MyCrashHandler2;
import com.apm.util.SQLLiteInstance;
import com.apm.util.SystemUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/3 0003.
 */
public class APMInstance {

    public static final int SEND_INSTANTLY = 0;
    public static final int SEND_APPSTART = 1;
    public static int activitynumber = 0;
    private static APMInstance instance = null;
    private int sendStrategy = 0;
    private SystemUtils systemUtils;
    private static int sdkVersion;
    private static int SDK_VERSION_LION = 19;

    private APMInstance() {
    }

    static {
        FileUtils.config();
/*        new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(BlockConstant.Turl.GETCONFIG+"?appId="+ Config.appId);
                    System.out.println("nonononourl==="+url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    System.out.println("nononono=code="+ conn.getResponseCode());
                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                        InputStreamReader isr = new InputStreamReader( conn.getInputStream() );
                        BufferedReader br = new BufferedReader(isr);
                        String content = br.readLine();
                        System.out.println("nononono result="+ content);
                        conn.disconnect();
                        if(content!=null)
                        {
                            JSONObject json=new JSONObject(content);
                            Config.appBootCollection=json.optBoolean("appBootCollection");
                            Config.crashCollection=json.optBoolean("crashAnalysisCollection");
                            Config.activityColection=json.optBoolean("activityColection");
                            Config.appRegistCollection=json.optBoolean("appRegistCollection");
                            Config.memoryUsageCollection=json.optBoolean("memoryUsageCollection");
                            Config.cpuUsageCollection=json.optBoolean("cpuUsageCollection");
                            Config.customEventCollection=json.optBoolean("customEventCollection");
                            Config.networkAccess=json.optBoolean("networkAccessCollection");
                            Config.databaseCollection=json.optBoolean("dataBaseCollection");
                        }
                    }else {
                        System.out.println("nonononelseelseleo=code="+ conn.getResponseCode());
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.start();*/
    }

    public static APMInstance getInstance() {
        if (instance == null) {
            synchronized (APMInstance.class) {
                if (instance == null)
                    instance = new APMInstance();
            }
        }
        return instance;
    }

    public void start(Application context) {
        ContextHolder.context = context;
        SQLiteDatabase db = SQLLiteInstance.getInstance();
        String sql = "CREATE TABLE IF NOT EXISTS apm (_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , jsoncontent TEXT, type INTEGER)";
        db.execSQL(sql);


        new Thread() {
            @Override
            public void run() {
                sendSQLLiteData();
            }
        }.start();

        Thread.setDefaultUncaughtExceptionHandler(new MyCrashHandler2(context));

        APM_Native apm_native = APM_Native.APM_NativeInstance();
        apm_native.start(context);

        BlockWatcher.getInstance(context).startWatching();
    }

    private void sendSQLLiteData() {
        SQLiteDatabase db = SQLLiteInstance.getInstance();

        /* try {
            String whereClause = "_id=?";//删除的条件
            String[] whereArgs = {String.valueOf(1)};//删除的条件参数
            int i=db.delete("apm", whereClause, whereArgs);//执行
            System.out.println("sendPostFileData===iiii="+i);
        }catch (Exception e) {
            System.out.println("sendPostFileData===error=="+e.getMessage());
        }*/

        Cursor c = db.query("apm", null, null, null, null, null, null);//查询并获得游标
        int len = c.getCount();
        System.out.println("dblen ===" + len);
        JSONArray jsonArray = new JSONArray();
        String deletesql = "";
        if (len != 0) {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex("_id"));
                deletesql += id + ",";
                System.out.println("sendPostFileData==" + id);
                String jsoncontent = c.getString(c.getColumnIndex("jsoncontent"));
                int type = c.getInt(c.getColumnIndex("type"));
                try {
                    JSONObject jsonObject = new JSONObject(jsoncontent);
                    jsonObject.put("apmtype", type);
                    jsonArray.put(jsonObject);
                    System.out.println("allinfo==" + jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            deletesql = deletesql.substring(0, deletesql.length() - 1);
            System.out.println("allinfojsonarr==" + jsonArray);
            final String jsonstr = jsonArray.toString();

            upload(jsonstr, db, deletesql);

        }
    }

    public void upload(String jsonstr, SQLiteDatabase db, String deletesql) {
        HttpURLConnection uRLConnection = null;
        //System.out.println("upload==="+jsonString);
        try {
            URL url = new URL(Constant.Turl.ALLINFO);
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
            outprint.write(jsonstr);
            outprint.flush();
            outprint.close();
            int code = uRLConnection.getResponseCode();
            //System.out.println("upload code=="+code);
            if (code == 200) {
                try {
                    db.execSQL("delete from apm where _id in (" + deletesql + ")");
                    //db.delete("apm", whereClause, idarr);//执行删除*//*

                } catch (Exception e) {
                    System.out.println("allinfo==" + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSendStrategy() {
        return sendStrategy;
    }

    public void setSendStrategy(int sendStrategy) {
        this.sendStrategy = sendStrategy;
    }
}