package com.apm.util;

import android.os.Environment;

/**
 * 常量接口定义常用的常量
 *
 * @author 王俊超
 */
public interface Constant {


    // 定义文件常量部分
    public static interface File {
        static final String MEMORY = "/proc/meminfo"; // 安卓系统内存文件位置
        static final String CPU = "/proc/cpuinfo"; // 安卓系统cpu文件位置
        static final String DATAPATH = Environment.getExternalStorageDirectory().getPath() + "/apmll";
    }

    // 发送请求的地址
    public static interface Uri {
        static final String HOST = "http://i-test.com.cn/PerformanceMonitorCenter";
        static final String ACTIVITY_ADD = HOST + "/activity/add"; // 添加Activity的地址
    }

    public static interface Turl {
        static final String IP = "i-test.com.cn";
        //static final String IP = "125.216.242.151:8080";
        static final String HOST = "http://" + IP + "/PerformanceMonitorCenter";
        //static final String HOST = "http://192.168.1.103:8080/PerformanceMonitorCenter";
        static final String ACTIVITY_ADD = HOST + "/receiveActivityInfo"; // 添加Activity的地址
        static final String ROUTE_ADD = HOST + "/receiveActivityRouteInfo"; // 添加Activity路径的URL
        static final String FRAGMENT_ADD = HOST + "/receiveFragmentInfo"; // 添加Fragment的URL
        static final String MEMORY_USAGE_ADD = HOST + "/receiveMemoryInfo"; // 添加内存使用量的URL
        static final String CPU_USAGE_ADD = HOST + "/receiveCPUInfo"; // 添加CPU使用量的URL
        static final String BOOT_USAGE_ADD = HOST + "/receiveLoginInfo"; // 添加每次使用信息的URL
        static final String REGIST_ADD = HOST + "/receiveRegistInfo"; // 添加每次使用信息的URL
        static final String CRACK_REPORT = HOST + "/receiveCrackReport";
        static final String NETWORK_ADD = HOST + "/receiveHttpInfo"; // 添加网络访问信息
        static final String DATABASE_ADD = HOST + "/receiveDatabaseInfo"; // 添加数据库访问信息
        static final String EVENT_ADD = HOST + "/receiveCustomEventInfo";
        static final String WEBVIEW_ADD = HOST + "/receiveWebViewInfo";  // WebView性能数据
        static final String ALLINFO = HOST + "/receiveAllInfo";
        static final String BLOCK_ADD = HOST + "/receiveBlockInfo";  //卡顿堆栈数据
        static final String LEAK_ADD = HOST + "/receiveLeakInfo";  //内存泄漏数据

        static final String GETCONFIG = HOST + "/loadConfig";

//		static final String NETWORK_ADD = "http://localhost:8080/PerformanceMonitorCenter/receiveHttpInfo"; // 添加网络访问信息
//		static final String DATABASE_ADD = "http://localhost:8080/PerformanceMonitorCenter//receiveDatabaseInfo"; // 添加数据库访问信息

        //static final String NETWORK_ADD = "http://192.168.1.103:8080/PerformanceMonitorCenter/receiveHttpInfo"; // 添加网络访问信息
        //static final String DATABASE_ADD = "http://192.168.1.103:8080/PerformanceMonitorCenter/receiveDatabaseInfo"; // 添加数据库访问信息

    }
}
