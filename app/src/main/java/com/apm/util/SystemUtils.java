package com.apm.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 系统工具获取系统相关的参数指标
 *
 * @author 王俊超
 */
public class SystemUtils {
    private final String TAG = "com.apm.util.SystemUtils";
    /**
     * 安卓应用上下文对象
     */
    private Context ctx;

    /**
     * 手机设备ID号
     */
    private volatile String deviceId;

    /**
     * 应用的名称
     */
    private String appName;

    /**
     * 应用的包名
     */
    private String appPackage;

    /**
     * 应用的版本号
     */
    private String appVersion;

    /**
     * 手出的品牌型号
     */
    private String manufacturer;

    /**
     * 操作系统版本
     */
    private String osVersion;

    private final static SystemUtils SU = new SystemUtils();

    private final Map<String, String> PROVINCES = new HashMap<String, String>();

    {
        String[][] map = { //
                {"Anhui", "Macao", "Beijing", "Chongqing", "Fujian",//
                        "Gansu", "Guangdong", "Guangxi Zhuangzu Zizhiqu", "Guizhou", "Hainan",//
                        "Hebei", "Heilongjiang", " Henan", "Hubei", "Hunan", //
                        "Jiangsu", "Jiangxi", "Jilin", "Liaoning", "Inner Mongolia",//
                        "Ningxia Huizu Zizhiqu", "Qinghai", "Shaanxi", "Shandong", "Shanghai", //
                        "Shanxi", "Sichuan", "Taiwan", "Tianjin", "Xianggang Tebie Xingzhengqu", //
                        "Xinjiang Uygur Zizhiqu", "Tibet Autonomous Region", "Yunnan", "Zhejiang",}, //
                {"安徽", "澳门", "北京", "重庆", "福建", //
                        "甘肃", "广东", "广西", "贵州", "海南", //
                        "河北", "黑龙江", "河南", "湖北", "湖南", //
                        "江苏", "江西", "吉林", "辽宁", "内蒙古", //
                        "宁夏", "青海", "陕西", "山东", "上海", //
                        "山西", "四川", "台湾", "天津", "香港", //
                        "新疆", "西藏", "云南", "浙江"} //
        };

        for (int i = 0; i < map[0].length; i++) {
            PROVINCES.put(map[0][i], map[1][i]);
        }
    }

    private SystemUtils() {
        // 不要进行初始化
    }

    /**
     * 设置安卓应用上下文
     *
     * @param ctx 安卓应用上下文
     */
    public static SystemUtils getInstance(Context ctx) {

        if (SystemUtils.SU.ctx == null && ctx == null) {
            throw new IllegalArgumentException("第一次调用参数不能为空");
        } else if (SystemUtils.SU.ctx == null) {
            SystemUtils.SU.ctx = ctx;
        }

        return SystemUtils.SU;
    }

    // /**
    // * 获取设备信息对象
    // *
    // * @param context
    // * @return
    // */
    // public DeviceInfo getDeviceInfo() {
    // if (!permessionCheck(ctx)) {
    // Log.e(TAG, "The permession READ_PHONE_STATE and ACCESS_NETWORK_STATE are needed");
    // return null;
    // }
    //
    // DeviceInfo deviceInfo = new DeviceInfo();
    // // 设置手机信息属性
    // deviceInfo.setBoard(getBoard());
    // deviceInfo.setBrand(getBrand());
    // deviceInfo.setCis(getCis());
    // String[] cupInof = getCpuInfo();
    // deviceInfo.setCpuType(cupInof[0]);
    // deviceInfo.setCpuFrequecy(cupInof[1]);
    // deviceInfo.setDevice(getDevice());
    // deviceInfo.setDisplay(getDisplay());
    // deviceInfo.setFingerPrint(getFingerPrint());
    // deviceInfo.setId(getDeviceId());
    // deviceInfo.setManufacturer(getManufacturer());
    // deviceInfo.setTotalMemory(getTotalMemory(ctx));
    // deviceInfo.setVersion(getOsVersion());
    //
    // deviceInfo.setCpuFrequecy(getCupType());
    // return deviceInfo;
    // }

    /**
     * 检查是否有权限
     *
     * @param context 安卓运行上下文
     * @return true有权限，false无权限
     */
    private boolean permessionCheck(Context context) {
        return PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(
                android.Manifest.permission.READ_PHONE_STATE, context.getPackageName());
    }

    /**
     * 获取手机管理器对象
     * <p>
     * 应用上下文对象
     *
     * @return 手机管理器对象
     */
    public TelephonyManager getTm() {
        return (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 获取设备唯一ID号，直接使用设备号会暴露用户个人信息，使用设备号加上SIM序列号和安卓ID号同样生成唯一的设备IDk号，并且不会暴露个人信息
     * <p>
     * 应用上下文对象
     *
     * @return 设备唯一ID号
     */
    public String getDeviceId() {

        if (deviceId == null) {

            try {
                TelephonyManager tm = getTm();
                String tmDevice, tmSerial, androidId;
                tmDevice = FileUtils.getString(tm.getDeviceId()); // 获取设备ID号
                tmSerial = FileUtils.getString(tm.getSimSerialNumber()); // 获取SIM卡的序列号
                androidId = FileUtils.getString( //
                        android.provider.Settings.Secure.getString( //
                                ctx.getContentResolver(), //
                                android.provider.Settings.Secure.ANDROID_ID)); // 获取安卓的ID号

                UUID deviceUuid = new UUID(//
                        androidId.hashCode(), //
                        ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode()); // 创建一个UUID对象
                deviceId = deviceUuid.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return deviceId;
    }

    /**
     * 获取手机主板信息
     *
     * @return 手机主板信息
     */
    public String getBoard() {
        Log.i(TAG, Build.BOARD + ", " + Build.BOOTLOADER);

        return Build.BOARD + ", " + Build.BOOTLOADER;
    }

    /**
     * 获取操作系统定制商
     *
     * @return 操作系统定制商
     */
    public String getBrand() {
        Log.i(TAG, Build.BRAND);

        return Build.BRAND;
    }

    /**
     * 获取手机芯片指令集信息
     *
     * @return 手机芯片指令集信息
     */
    public String getCis() {
        Log.i(TAG, Build.CPU_ABI + ", " + Build.CPU_ABI2);

        return Build.CPU_ABI + ", " + Build.CPU_ABI2;
    }

    // 手机设备参数
    public String getDevice() {
        Log.i(TAG, Build.DEVICE);
        return Build.DEVICE;
    }

    /**
     * 获取手机显示屏参数
     *
     * @return 手机显示屏参数
     */
    public String getDisplay() {
        Log.i(TAG, Build.DISPLAY);

        return Build.DISPLAY;
    }

    /**
     * 获取手机硬件名称
     *
     * @return 手机硬件名称
     */
    public String getFingerPrint() {
        Log.i(TAG, Build.FINGERPRINT);

        return Build.FINGERPRINT;
    }

    /**
     * 获取手机版本号
     *
     * @return 手机版本号
     */
    public String getOsVersion() {

        if (osVersion == null) {
            osVersion = Build.VERSION.RELEASE;
        }

        return osVersion;
    }

    /**
     * 获取手机制造商
     *
     * @return 手机制造商
     */
    public String getManufacturer() {
        if (manufacturer == null) {
            manufacturer = Build.MANUFACTURER;
        }
        return manufacturer;
    }

    /**
     * 获取应用的名称
     *
     * @return 应用的名称
     */
    public String getAppName() {
        if (appName == null) {
            PackageManager pm = ctx.getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), 0);
                appName = pi.applicationInfo.loadLabel(pm).toString();
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return appName;
    }

    /**
     * 获取应用的包名
     *
     * @return 应用的包名
     */
    public String getAppPackage() {
        if (appPackage == null) {
            PackageManager pm = ctx.getPackageManager();
            try {
                appPackage = pm.getPackageInfo(ctx.getPackageName(), 0).packageName;
            } catch (NameNotFoundException e) {
                appPackage = "未知";
                e.printStackTrace();
            }
        }

        return appPackage;
    }

    public String getAppVersion() {
        if (appVersion == null) {
            PackageManager pm = ctx.getPackageManager();
            try {
                appVersion = pm.getPackageInfo(ctx.getPackageName(), 0).versionName;
            } catch (NameNotFoundException e) {
                appVersion = "未知";
                e.printStackTrace();
            }
        }

        return appVersion;
    }

    /**
     * 获取手机总的内存
     *
     * @return
     */
    public String getTotalMemory(Context context) {
        String fileName = Constant.File.MEMORY;// 系统内存信息文件的位置
        String firstLine; // 用于保存内存文件读取到的第一行
        String[] arrayOfString;
        long totalMemory = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName), 8192);
            firstLine = reader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = firstLine.split("\\s+");

            totalMemory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Formatter.formatFileSize(context, totalMemory);// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 获取手机CPU信息
     *
     * @return CPU信息， result[0]为cpu型号，result[1]为cpu频率
     */
    public String[] getCpuInfo() {
        String cpuFile = Constant.File.CPU;
        String line = ""; // 保存每次读到的一行信息
        String[] cpuInfo = {"", ""};  // 1-cpu型号 //2-cpu频率
        String[] items; // 保存每行分离后的项
        try {
            BufferedReader reader = new BufferedReader(new FileReader(cpuFile), 8192);
            line = reader.readLine(); // 读第一行

            items = line.split("\\s+"); // 将第一行读到的信息以空白符分隔开

            for (int i = 2; i < items.length; i++) {
                cpuInfo[0] += items[i] + " ";
            }

            line = reader.readLine(); // 读第二行
            items = line.split("\\s+");

            cpuInfo[1] = items[2];

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, cpuInfo[0] + ", " + cpuInfo[1]);
        return cpuInfo;
    }

    /**
     * 获取CPU型号
     *
     * @return CPU型号
     */
    public String getCupType() {
        Log.i(TAG, Build.HARDWARE);
        return Build.HARDWARE;
    }

    /**
     * 获取用户所在的省份
     *
     * @return 用户所在的省份
     */
    public String getProvince() {
        return province;
    }

    private String province = "未知地区";

    {
        new Thread() {
            public void run() {
                // 通过IP获取省份和国家
                // 对于香港和澳门返回的只有国家（对应的名称即为它们的名称）没有省份
                String urlStr = "http://ip-api.com/line/?fields=regionName,country";
				/*HttpResponse response = null;
				String result = "未知地区";
				try {
					//response = new DefaultHttpClient().execute(new HttpGet(url));

					HttpClient httpclient = new DefaultHttpClient();
					// 创建Get方法实例
					HttpGet httpgets = new HttpGet(url);
					try {
						response = httpclient.execute(httpgets);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(response!=null) {
						String name = EntityUtils.toString(response.getEntity());
						String[] tmp = name.split("\\n");
						if (tmp.length == 2) {
							if (!tmp[1].trim().equals("")) {
								name = tmp[1];
							} else {
								name = tmp[0];
							}

							// 根据返回的拼音或者英文名找到对应的汉字名称
							// 必须有这个Key才获取数据，否则就认为是未地区
							if (PROVINCES.containsKey(name)) {
								result = PROVINCES.get(name);
							}
						}
					}
					else
						result="";
				} catch (Exception e) {
					e.printStackTrace();
				}*/

                String result = "未知地区";
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(urlStr);
                    connection = (HttpURLConnection) url.openConnection();

                    //设置GET请求
                    connection.setRequestMethod("GET");
                    //设置超时时间
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(1000);

                    //获取服务器响应码
                    int ncode = connection.getResponseCode();
                    if (ncode == 200) {
                        InputStream inputStream = connection.getInputStream();

                        //将流转换成String
                        String content = ReadStream(inputStream);

                        String name;
                        String[] tmp = content.split("\\n");
                        if (tmp.length == 2) {
                            if (!tmp[1].trim().equals("")) {
                                name = tmp[1];
                            } else {
                                name = tmp[0];
                            }

                            // 根据返回的拼音或者英文名找到对应的汉字名称
                            // 必须有这个Key才获取数据，否则就认为是未地区
                            if (PROVINCES.containsKey(name)) {
                                result = PROVINCES.get(name);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

                province = result;
            }
        }.start();
    }

    private String ReadStream(InputStream in) throws Exception {
        int len = -1;
        byte buffer[] = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        while ((len = in.read(buffer)) > 0) {
            baos.write(buffer, 0, len);
        }

        //关闭流
        in.close();

        String content = new String(baos.toByteArray());
        return content;
    }

    /**
     * Activities栈顶的Activity名称
     *
     * @return 栈顶Activity的名称
     */
    public String getTopActivity() {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            return (runningTaskInfos.get(0).topActivity).flattenToString();
        } else {
            return null;
        }
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////
    // 获取网络类型
    // ///////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 网络类型，指不同的制式
     */
    private static final int NT_UNAVAILABLE = -1;
    private static final int NT_WIFI = -101;

    /**
     * 网络类别，指2G、3G、4G、Wi-Fi
     */
    private static final int NC_WIFI = -101;
    private static final int NC_UNAVAILABLE = -1;
    private static final int NC_UNKNOWN = 0;
    private static final int NC_2_G = 1;
    private static final int NC_3_G = 2;
    private static final int NC_4_G = 3;

    /**
     * 网络类型，指不同的制式
     */
    public static final int NT_UNKNOWN = 0;
    public static final int NT_GPRS = 1;
    public static final int NT_EDGE = 2;
    public static final int NT_UMTS = 3;
    public static final int NT_CDMA = 4;
    public static final int NT_EVDO_0 = 5;
    public static final int NT_EVDO_A = 6;
    public static final int NT_1xRTT = 7;
    public static final int NT_HSDPA = 8;
    public static final int NT_HSUPA = 9;
    public static final int NT_HSPA = 10;
    public static final int NT_IDEN = 11;
    public static final int NT_EVDO_B = 12;
    public static final int NT_LTE = 13;
    public static final int NT_EHRPD = 14;
    public static final int NT_HSPAP = 15;

    /**
     * 判断网络是否可用
     *
     * @return
     */
    public boolean IsNetWorkEnable() {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                return false;
            }

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 判断当前网络是否已经连接
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获取网络访问类型
     *
     * @return 2G、3G、4G、Wi-Fi、无
     */
    public String getAccessType() {
        int networkClass = getNetworkClass();
        String type = "未知";
        switch (networkClass) {
            case NC_UNAVAILABLE:
                type = "无";
                break;
            case NC_WIFI:
                type = "Wi-Fi";
                break;
            case NC_2_G:
                type = "2G";
                break;
            case NC_3_G:
                type = "3G";
                break;
            case NC_4_G:
                type = "4G";
                break;
            case NC_UNKNOWN:
                type = "未知";
                break;
        }
        return type;
    }

    /**
     * 获取网络类别
     *
     * @return 网络类别编号
     */
    private int getNetworkClass() {
        // 默认未知
        int networkType = NT_UNKNOWN;
        try {
            // 获取网络信息
            NetworkInfo network = ((ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();
            // 判断是使用Wi-Fi访问网络还是手机网络
            if (network != null && network.isAvailable() && network.isConnected()) {
                int type = network.getType();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    networkType = NT_WIFI;
                }
                // 如果使用手机访问网络，需要判断手机网络类型
                else if (type == ConnectivityManager.TYPE_MOBILE) {
                    TelephonyManager telephonyManager = (TelephonyManager) ctx
                            .getSystemService(Context.TELEPHONY_SERVICE);
                    // 获取手机网络类别编号
                    networkType = telephonyManager.getNetworkType();
                }
            } else {
                networkType = NT_UNAVAILABLE;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getNetworkClassByType(networkType);

    }

    /**
     * 获取类别编号
     *
     * @param networkType 网络类型，指不同的运营商不同的制式
     * @return 类别编号，指2G、3G、4G、Wi-Fi等编号
     */
    private int getNetworkClassByType(int networkType) {
        switch (networkType) {
            case NT_UNAVAILABLE:
                return NC_UNAVAILABLE;
            case NT_WIFI:
                return NC_WIFI;
            case NT_GPRS:
            case NT_EDGE:
            case NT_CDMA:
            case NT_1xRTT:
            case NT_IDEN:
                return NC_2_G;
            case NT_UMTS:
            case NT_EVDO_0:
            case NT_EVDO_A:
            case NT_HSDPA:
            case NT_HSUPA:
            case NT_HSPA:
            case NT_EVDO_B:
            case NT_EHRPD:
            case NT_HSPAP:
                return NC_3_G;
            case NT_LTE:
                return NC_4_G;
            default:
                return NC_UNKNOWN;
        }
    }

    /**
     * 获取应用的内存使用量
     *
     * @return 内存使用量，单位kB
     */
    public int getMenoryUsage() {
        int pid = android.os.Process.myPid();
        int size = 0;
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo rap : am.getRunningAppProcesses()) {
            if (rap.pid == pid) {
                // 占用的内存
                int[] pids = new int[]{pid};
                Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(pids);
                size = memoryInfo[0].getTotalPss();
                break;
            }
        }

        return size;
    }

    /**
     * 获取SIM卡网络运营商类型
     *
     * @return
     */
    public String getSimProvider() {

        TelephonyManager tm = getTm();
        String provider = tm.getSimOperator();

        if ("46000".equals(provider) || "46002".equals(provider)) {
            return "中国移动";
        } else if ("46001".equals(provider)) {
            return "中国联通";
        } else if ("46003".equals(provider)) {
            return "中国电信";
        }

        return "unknown";
    }

    /**
     * 获取CPU的使用率
     *
     * @return CPU使用率
     */
    public synchronized double getCpuUsage() {

        if(Build.VERSION.SDK_INT>=25) {

            try {
                String pid = "" + android.os.Process.myPid();
                java.lang.Process process = Runtime.getRuntime().exec("top");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                String[] info;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(pid)) {
                        info = line.replaceAll(" +", " ").split(" ");
                        return new BigDecimal(info[9]).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        float total1 = getTotalUsage();
        float app1 = getAppUsage();
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }

        double total2 = getTotalUsage();
        double app2 = getAppUsage();

        double rate = 100 * (app2 - app1) / (total2 - total1);

        // 留两位小数
        return BigDecimal.valueOf(rate).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取系统总CPU使用时间
     *
     * @return 系统总的CPU使用时间
     */
    private long getTotalUsage() {
        String[] cis = null;
        long total = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/proc/stat"), 1000);
            String line = reader.readLine();
            reader.close();
            cis = line.split(" ");
            total = Long.parseLong(cis[2]) + Long.parseLong(cis[3]) + Long.parseLong(cis[4]) + Long.parseLong(cis[6])
                    + Long.parseLong(cis[5]) + Long.parseLong(cis[7]) + Long.parseLong(cis[8]);
            return total;
        } catch (IOException ex) {
            ex.printStackTrace();
            return total;
        }
    }

    /**
     * 获取用应占用的CPU时间
     *
     * @return 应用占用的CPU时间
     */
    private long getAppUsage() {
        String[] cis = null;
        try {
            int pid = android.os.Process.myPid();
            BufferedReader reader = new BufferedReader(new FileReader("/proc/" + pid + "/stat"), 1000);
            String line = reader.readLine();
            reader.close();
            cis = line.split(" ");
            long time = Long.parseLong(cis[13]) + Long.parseLong(cis[14]) + Long.parseLong(cis[15])
                    + Long.parseLong(cis[16]);
            return time;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 1;
        }

    }

    public String getScreenType() {
        String result = "NaN";
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);

        if (screenInches >= 9) {
            result = ">=9";
        } else if (screenInches >= 7) {
            result = "[7, 9)";
        } else if (screenInches >= 5) {
            result = "[5, 7)";
        } else {
            result = "<5";
        }
        return result;
    }

    /**
     * 获取手机的运营商
     *
     * @return 手机运营商
     */
    public String getOperator() {
        TelephonyManager tm = getTm();
        String operator = "无";
        String simi = tm.getSubscriberId();
        if (simi == null || simi.trim().equals("")) {
            return operator;
        }
        if (simi.startsWith("46000") || simi.startsWith("46002") || simi.startsWith("46007")) {
            operator = "中国移动";
        } else if (simi.startsWith("46001")) {
            operator = "中国联通";
        } else if (simi.startsWith("46003")) {
            operator = "中国电信";
        } else {
            operator = "其它";
        }
        return operator;
    }

}
