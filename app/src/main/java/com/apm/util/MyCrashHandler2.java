package com.apm.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.apm.model.HttpRequest;
import com.apm.test.tool.DiskInfomation;
import com.apm.test.tool.MemoryInformation;
import com.apm.test.tool.ProcessCpuRate;
import com.apm.test.tool.TrafficInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JJY on 2016/9/2.
 */
public class MyCrashHandler2 implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler originalHandler;
    //private StringBuffer sb = new StringBuffer();
    private Map<String, String> deviceInfo = new HashMap<String, String>();
    private Map<String, String> performanceInfo = new HashMap<String, String>();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private Context mContext;
    private JSONObject jsonResult = new JSONObject(); // 锟斤拷锟节憋拷锟斤拷锟届常锟斤拷息锟斤拷锟斤拷锟酵碉拷锟斤拷锟斤拷锟斤拷

    public MyCrashHandler2(Context context) {
        originalHandler = Thread.getDefaultUncaughtExceptionHandler();
        mContext = context;
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable throwable) {
        // Deal this exception
        System.out.println("catch it----------------------------------");
        //saveFile("catch it" + System.currentTimeMillis());
        try {
            handleException(throwable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (originalHandler != null) {
            originalHandler.uncaughtException(thread, throwable);
        }
    }

    ;

    public boolean handleException(Throwable ex) throws IOException {
        if (ex == null)
            return false;
        collectDeviceInfo(mContext);
        getPhoneParams(ex);
        getPerformanceInfo(ex);
        getDetailExceptionInfo(ex);
        try {
            getExceptionInfo(ex);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
/*        getDetailExceptionInfo(ex, sb);
        // 锟斤拷锟斤拷锟斤拷志锟侥硷拷*/
        saveCrashInfo2File(ex);

        return true;
    }

    private File saveCrashInfo2File(Throwable ex) throws IOException {

        System.out.println("=====json======" + jsonResult.toString());
        saveDB(jsonResult.toString());
        return null;
    }

    private void getDetailExceptionInfo(Throwable ex) {

        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        Throwable cause = ex.getCause();
        // 循锟斤拷锟脚帮拷锟斤拷锟叫碉拷锟届常锟斤拷息写锟斤拷writer锟斤拷
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.close();// 锟角得关憋拷
        String result = writer.toString();

        try {
            jsonResult.put("CrackStackInfo", result.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void getPerformanceInfo(Throwable ex) {
        JSONObject Crash_Performance_Info = new JSONObject();
        for (Map.Entry<String, String> entry : performanceInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            try {
                // 锟斤拷硬锟斤拷锟斤拷息锟芥到json锟斤拷锟斤拷锟叫ｏ拷锟斤拷锟节凤拷锟酵碉拷锟斤拷锟斤拷锟斤拷
                Crash_Performance_Info.put(key, value);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 锟斤拷硬锟斤拷锟斤拷息锟芥储锟斤拷StringBuffer锟叫ｏ拷锟斤拷锟节存储锟斤拷锟斤拷志锟侥硷拷
        }

        try {
            jsonResult.put("Crash_Performance_Info", Crash_Performance_Info);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void getPhoneParams(Throwable ex) {
        JSONObject Crash_Device_Info = new JSONObject();
        for (Map.Entry<String, String> entry : deviceInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            try {
                // 锟斤拷硬锟斤拷锟斤拷息锟芥到json锟斤拷锟斤拷锟叫ｏ拷锟斤拷锟节凤拷锟酵碉拷锟斤拷锟斤拷锟斤拷
                Crash_Device_Info.put(key, value);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            jsonResult.put("Crash_Device_Info", Crash_Device_Info);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();// 锟斤拷冒锟斤拷锟斤拷锟斤拷锟�
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);// 锟矫碉拷锟斤拷应锟矫碉拷锟斤拷息锟斤拷锟斤拷锟斤拷Activity
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                deviceInfo.put("VersionMain", versionName);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // 锟斤拷取锟借备锟斤拷硬锟斤拷锟斤拷息
        setPhoneParams();
        // 锟斤拷取锟借备锟斤拷锟斤拷锟斤拷锟斤拷息
        setPerformanceInfo();
    }

    private void setPhoneParams() {
        deviceInfo.put("Board", Build.BOARD);
        deviceInfo.put("BootLoader", Build.BOOTLOADER);
        deviceInfo.put("Brand", Build.BRAND);
        deviceInfo.put("CPU_ABI", Build.CPU_ABI);
        deviceInfo.put("CPU_ABI2", Build.CPU_ABI2);
        deviceInfo.put("Device", Build.DEVICE);
        deviceInfo.put("Display", Build.DISPLAY);
        deviceInfo.put("HardWare", Build.HARDWARE);
        deviceInfo.put("Manufacturer", Build.MANUFACTURER);
        deviceInfo.put("Model", Build.MODEL);
        deviceInfo.put("Product", Build.PRODUCT);
        deviceInfo.put("VersionRelease", Build.VERSION.RELEASE);
        deviceInfo.put("BrandInfo", Build.VERSION.INCREMENTAL);
        deviceInfo.put("SDKVersion", Build.VERSION.SDK);

    }

    private void setPerformanceInfo() {
        MemoryInformation memInfo = new MemoryInformation(mContext, android.os.Process.myPid());
        performanceInfo.put("Total Memory", memInfo.getTotalMemory() + "MB");
        performanceInfo.put("Avaliable Memory", memInfo.getAvaMemory() + "MB");
        performanceInfo.put("Process Used Memory", memInfo.getProcessUsedMem() + "MB");
        ProcessCpuRate cpuInfo = new ProcessCpuRate(android.os.Process.myPid());
        performanceInfo.put("Process Used CPU", cpuInfo.getProcessCpuRate() + "%");
        // deviceInfo.put("Total Used CPU",cpuInfo.readCpu()+"%");
        DiskInfomation diskInfo = new DiskInfomation();
        performanceInfo.put("Avalible ROM", diskInfo.getAvailMemory() + "GB");
        performanceInfo.put("Total ROM", diskInfo.getTotalMemory() + "GB");
        //performanceInfo.put("Dump Energy", Constant.level + "%");
        //performanceInfo.put("Device Temperature", Constant.temperature / 10 + "锟斤拷C");
        TrafficInformation trafficInfo = new TrafficInformation(android.os.Process.myPid());
        // performanceInfo.put("Process Traffic Consumption",trafficInfo.getTotalTrafficInfo()+"");
    }

    private void getExceptionInfo(Throwable ex) throws JSONException {


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 锟斤拷锟斤拷锟斤拷锟节革拷式
        // Date()为锟斤拷取锟斤拷前系统时锟斤拷
        jsonResult.put("CrackTime", df.format(new Date()).toString());
        jsonResult.put("CrackType", ex.getClass().getName().toString());
        if (ex.getMessage() != null) {
            jsonResult.put("CrackInfo", ex.getMessage().toString());
        } else {
            jsonResult.put("CrackInfo", "java.lang.NullPointerException");
        }
        String appId = Config.appId;
        /////////////////diviceId!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        String deviceId = SystemUtils.getInstance(mContext).getDeviceId();
        jsonResult.put("appId", appId);
        jsonResult.put("deviceId", deviceId);
        String exceptionType = ex.getStackTrace()[0].toString().substring(//
                ex.getStackTrace()[0].toString().lastIndexOf('(') + 1,//
                ex.getStackTrace()[0].toString().lastIndexOf(':'));
        String exceptionLine = ex.getStackTrace()[0].toString().substring(//
                ex.getStackTrace()[0].toString().lastIndexOf(':') + 1,//
                ex.getStackTrace()[0].toString().lastIndexOf(')'));
        String uniqueCode = appId + exceptionType + exceptionLine;
        jsonResult.put("exceptionType", exceptionType);
        jsonResult.put("exceptionLine", exceptionLine);
        jsonResult.put("hashCode", uniqueCode.hashCode() + "");
        // End 2015-06-08 19:51


        System.out.println("jsonResult.toString()==" + jsonResult.toString());
    }

    public void saveDB(String str) {
        string2SQLLite(new HttpRequest(str, 9));
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


}