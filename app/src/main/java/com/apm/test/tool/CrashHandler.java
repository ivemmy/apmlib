package com.apm.test.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ParseException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.apm.util.Config;
import com.apm.util.SendDataUtil;
import com.apm.util.SystemUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author user
 */
@Deprecated
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    // CrashHandler 实例
    private static CrashHandler INSTANCE = new CrashHandler();

    // 程序的 Context 对象
    private Context mContext;
    // 系统默认的 UncaughtException 处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();
    private Map<String, String> deviceInfo = new HashMap<String, String>();// 锟斤拷锟斤拷锟芥储锟借备锟斤拷息锟斤拷锟届常锟斤拷息
    private Map<String, String> performanceInfo = new HashMap<String, String>();// 锟斤拷锟斤拷锟芥储锟借备锟斤拷锟斤拷锟斤拷息
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");// 锟斤拷锟节革拷式锟斤拷锟斤拷锟斤拷,锟斤拷为锟斤拷志锟侥硷拷锟斤拷锟斤拷一锟斤拷锟斤拷
    private String crashReport;
    StringBuffer sb = new StringBuffer();

    JSONObject jsonResult = new JSONObject(); // 锟斤拷锟节憋拷锟斤拷锟届常锟斤拷息锟斤拷锟斤拷锟酵碉拷锟斤拷锟斤拷锟斤拷

    /**
     * 保证只有一个 CrashHandler 实例
     */
    private CrashHandler() {
    }

    /**
     * 获取 CrashHandler 实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;

        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            if (!handleException(ex) && mDefaultHandler != null) {
                // 如果用户没有处理则让系统默认的异常处理器来处理
                mDefaultHandler.uncaughtException(thread, ex);
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "error : ", e);
                }
                // mDefaultHandler.uncaughtException(thread, ex);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param ex
     * @return true：如果处理了该异常信息；否则返回 false
     */
    /**
     * 锟皆讹拷锟斤拷锟届常锟斤拷锟斤拷锟斤拷,锟秸硷拷锟斤拷锟斤拷锟斤拷息锟斤拷锟斤拷锟酵达拷锟襟报革拷炔锟斤拷锟斤拷锟斤拷诖锟斤拷锟斤拷.
     *
     * @param ex 锟届常锟斤拷锟斤拷
     * @return true:锟斤拷锟斤拷锟斤拷锟斤拷烁锟斤拷斐ｏ拷锟较�;锟斤拷锟津返伙拷false.
     */
    public boolean handleException(Throwable ex) throws IOException {
        if (ex == null) // 锟斤拷锟矫伙拷锟斤拷斐ｏ拷锟斤拷锟斤拷锟斤拷斐ｏ拷丫锟斤拷锟斤拷锟斤拷锟斤拷耍锟斤拷蚍祷锟絝alse
            return false;
        // crashReport = getCrashReport(mContext, ex);
        new Thread() { // 锟斤拷锟津开憋拷锟斤拷锟竭程达拷锟斤拷锟斤拷示
            public void run() {
                Looper.prepare();
                // showTipDialog(mContext);
                Toast.makeText(mContext, "Program occurs exception, it wil exit soon!", Toast.LENGTH_LONG).show();
                // showTipDialog(mContext);
                Looper.loop();

            }
        }.start();
        // 锟秸硷拷锟借备锟斤拷锟斤拷锟斤拷息
        collectDeviceInfo(mContext);
        getPhoneParams(ex, sb);
        getPerformanceInfo(ex, sb);

        try {
            getExceptionInfo(ex, sb);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        getDetailExceptionInfo(ex, sb);
        // 锟斤拷锟斤拷锟斤拷志锟侥硷拷
        saveCrashInfo2File(ex);

        return true;
    }

    /**
     * 锟斤拷取锟斤拷细锟斤拷锟届常锟斤拷息
     * <p>
     * 锟斤拷锟届常栈锟斤拷锟斤拷锟斤拷锟届常锟斤拷锟轿硷拷录
     *
     * @param ex 锟斤拷锟届常锟斤拷锟斤拷
     * @param sb 锟斤拷锟斤拷锟节斤拷锟届常锟斤拷息锟斤拷录锟斤拷锟斤拷志锟侥硷拷
     */
    private void getDetailExceptionInfo(Throwable ex, StringBuffer sb) {

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
        sb.append("" + "\r\n");
        sb.append("" + "\r\n");
        sb.append("" + "\r\n");
        sb.append("=================================锟斤拷锟斤拷锟斤拷息追锟斤拷=================================" + "\r\n" + "\r\n" + "\r\n");
        String[] results = result.split("\n");
        for (String eachresult : results) {
            sb.append(eachresult + "\r\n");
        }

    }

    /**
     * 通锟斤拷锟斤拷锟斤拷锟斤拷苹锟饺★拷璞革拷锟接诧拷锟斤拷锟较拷锟斤拷锟斤拷娴絛eviceInfo锟斤拷
     */
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

    /**
     * 锟秸硷拷锟借备锟斤拷锟斤拷锟斤拷息
     *
     * @param context
     */
    public void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();// 锟斤拷冒锟斤拷锟斤拷锟斤拷锟�
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);// 锟矫碉拷锟斤拷应锟矫碉拷锟斤拷息锟斤拷锟斤拷锟斤拷Activity
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                deviceInfo.put("VersionMain", versionName);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        // 锟斤拷取锟借备锟斤拷硬锟斤拷锟斤拷息
        setPhoneParams();
        // 锟斤拷取锟借备锟斤拷锟斤拷锟斤拷锟斤拷息
        setPerformanceInfo();
    }

    /**
     * 锟斤拷取锟届常锟斤拷息
     *
     * @param ex 锟斤拷锟届常锟斤拷锟斤拷
     * @param sb 锟斤拷锟斤拷锟节存储锟届常锟斤拷息锟斤拷锟斤拷录锟斤拷锟斤拷志锟侥硷拷锟斤拷锟斤拷锟斤拷
     * @throws JSONException
     */
    private void getExceptionInfo(Throwable ex, StringBuffer sb) throws JSONException {
        sb.append("" + "\r\n");
        sb.append("" + "\r\n");
        sb.append("" + "\r\n");
        /*
         * Writer writer = new StringWriter(); //锟斤拷锟节硷拷录锟届常锟斤拷息 PrintWriter pw = new PrintWriter(writer);
         * ex.printStackTrace(pw); Throwable cause = ex.getCause(); // 循锟斤拷锟脚帮拷锟斤拷锟叫碉拷锟届常锟斤拷息写锟斤拷writer锟斤拷 //锟斤拷锟斤拷锟届常栈锟斤拷每锟斤拷锟届常锟斤拷直锟斤拷原锟斤拷锟铰硷拷锟叫达拷锟絧w锟斤拷
         * while (cause != null) { cause.printStackTrace(pw); cause = cause.getCause(); } pw.close();// 锟角得关憋拷
         */
        // String result = writer.toString();
        sb.append("=================================锟斤拷锟斤拷锟斤拷息锟斤拷锟斤拷=================================" + "\r\n" + "\r\n" + "\r\n");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 锟斤拷锟斤拷锟斤拷锟节革拷式
        sb.append("锟届常锟斤拷锟斤拷锟斤拷时锟戒：" + df.format(new Date()) + "\r\n"); // new
        // Date()为锟斤拷取锟斤拷前系统时锟斤拷
        jsonResult.put("CrackTime", df.format(new Date()).toString());
        sb.append("锟届常锟斤拷锟酵ｏ拷" + ex.getClass().getName() + "\r\n"); // 锟斤拷录锟届常锟斤拷锟斤拷
        jsonResult.put("CrackType", ex.getClass().getName().toString());
        if (ex.getMessage() != null) {
            sb.append("锟届常锟斤拷息锟斤拷" + ex.getMessage() + "\r\n"); // 锟斤拷录锟届常锟斤拷息
            jsonResult.put("CrackInfo", ex.getMessage().toString());
        } else {
            sb.append("锟届常锟斤拷息锟斤拷" + ex.getMessage() + "\r\n"); // 锟斤拷录锟届常锟斤拷息
            jsonResult.put("CrackInfo", "java.lang.NullPointerException");
        }
        String appId = Config.appId;
        String deviceId = SystemUtils.getInstance(null).getDeviceId();
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

        if (ex.getCause() != null) { // 锟斤拷锟斤拷锟斤拷锟斤拷斐ｏ拷锟皆拷锟轿拷眨锟斤拷锟铰硷拷斐Ｔ拷锟�
            sb.append("锟斤拷锟铰憋拷锟斤拷锟斤拷锟斤拷锟斤拷原锟斤拷" + ex.getCause().getClass() + "\r\n");
            jsonResult.put("CrackStackInfo", ex.getCause().getClass().toString());
        }
    }


    /**
     * 锟斤拷锟届常锟斤拷息锟斤拷锟芥到锟斤拷志锟侥硷拷锟叫ｏ拷锟剿达拷锟斤拷时锟芥到锟街伙拷锟芥储锟斤拷锟斤拷
     *
     * @param ex 锟斤拷 锟届常锟斤拷锟斤拷
     * @return
     * @throws IOException
     */
    private File saveCrashInfo2File(Throwable ex) throws IOException {

        // 锟斤拷取锟街伙拷硬锟斤拷锟斤拷息

        // 锟斤拷锟解开锟斤拷一锟斤拷锟竭程斤拷锟届常锟斤拷息锟斤拷锟酵碉拷锟斤拷锟斤拷锟斤拷

//		 Runnable runnable = new Runnable() { public void run() { try { try { saveToMongoDB(jsonResult); } catch
//		 (JSONException e1) { // TODO Auto-generated catch block e1.printStackTrace(); } catch (ParseException e1) {
//		 // TODO Auto-generated catch block e1.printStackTrace(); } } catch (Exception e) { // TODO Auto-generated
//		 catch block e.printStackTrace(); } } }; try { // 锟斤拷锟斤拷锟竭筹拷 new Thread(runnable).start(); // handler锟斤拷锟竭筹拷之锟斤拷锟酵拷偶锟斤拷锟斤拷荽锟斤拷锟� }
//		 catch (Exception e) { e.printStackTrace(); }

/*		Runnable runnable = new Runnable(){
			public void run(){
				try {
					saveToMongoDB(jsonResult);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};*/


        System.out.println("=====json======" + jsonResult.toString());

        String allInfo = sb.toString();
        // allInfo = en2ch(allInfo);
        // 锟斤拷取锟斤拷前时锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟轿ㄒ伙拷锟斤拷募锟斤拷锟�
        long timetamp = System.currentTimeMillis();
        // 锟斤拷取锟斤拷前时锟斤拷
        String time = format.format(new Date());
        // 锟斤拷锟斤拷唯一锟斤拷锟侥硷拷锟斤拷
        String fileName = "crash-" + time + "-" + timetamp + ".txt";
        // 锟斤拷锟斤拷锟斤拷息锟斤拷锟街伙拷锟芥储锟斤拷锟斤拷
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                        + "unsend_crash");
                if (!dir.exists())
                    dir.mkdir();
                File file = new File(dir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(allInfo.getBytes());
                fos.close();
                return file;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("=====json======" + jsonResult.toString());
        return null;

        // return null;
    }

    /**
     * 转锟斤拷锟斤拷锟斤拷锟斤拷
     *
     * @throws JSONException
     * @throws ParseException
     * @throws IOException
     */

    private void saveToMongoDB(final JSONObject jsonObj) {

        //HttpUrlConnectionUtil.sendPostJsonObjectData(jsonObj,Constant.Turl.CRACK_REPORT);
        SendDataUtil.SendData(jsonObj, 9);
    }

    /**
     * 锟斤拷取锟街伙拷硬锟斤拷锟斤拷息
     *
     * @param ex 锟斤拷锟届常锟斤拷锟斤拷
     * @param sb 锟斤拷锟斤拷锟节存储锟街伙拷硬锟斤拷锟斤拷息
     */
    private void getPhoneParams(Throwable ex, StringBuffer sb) {
        sb.append("" + "\r\n");
        sb.append("" + "\r\n");
        sb.append("" + "\r\n");
        sb.append("=================================锟街伙拷锟斤拷锟斤拷锟斤拷锟斤拷=================================" + "\r\n" + "\r\n" + "\r\n");

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
            // 锟斤拷硬锟斤拷锟斤拷息锟芥储锟斤拷StringBuffer锟叫ｏ拷锟斤拷锟节存储锟斤拷锟斤拷志锟侥硷拷
            sb.append(key + ": " + value + "\r\n");
        }

        try {
            jsonResult.put("Crash_Device_Info", Crash_Device_Info);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void getPerformanceInfo(Throwable ex, StringBuffer sb) {
        sb.append("" + "\r\n");
        sb.append("" + "\r\n");
        sb.append("" + "\r\n");
        sb.append("=================================锟斤拷锟杰诧拷锟斤拷锟斤拷锟斤拷=================================" + "\r\n" + "\r\n" + "\r\n");

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
            sb.append(key + ": " + value + "\r\n");
        }

        try {
            jsonResult.put("Crash_Performance_Info", Crash_Performance_Info);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private String getCrashReport(Context context, Throwable ex) {
        PackageInfo pinfo = getPackageInfo(context);
        StringBuffer exceptionStr = new StringBuffer();
        exceptionStr.append("Version: " + pinfo.versionName + "(" + pinfo.versionCode + ")\n");
        exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE + "(" + android.os.Build.MODEL + ")\n");
        exceptionStr.append("Exception: " + ex.getMessage() + "\n");
        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString() + "\n");
        }
        return exceptionStr.toString();
    }

    /**
     * 锟斤拷取App锟斤拷装锟斤拷锟斤拷息
     *
     * @return
     */
    private PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // e.printStackTrace(System.err);
            // L.i("getPackageInfo err = " + e.getMessage());
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

}