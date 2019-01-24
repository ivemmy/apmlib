package com.apm.log;

import android.util.Log;

/**
 * 日志记录类
 *
 * @author 王俊超
 */
public class ApmLogger {
    private final static String TAG = "apm-tracing";

    private ApmLogger() {
    }

    public static void verbose(String str) {
        Log.v(TAG, str);
    }

    public static void verbose(Object o) {
        if (o == null) {
            verbose("null");
        } else {
            verbose(o.toString());
        }
    }

    public static void debug(String str) {
        Log.d(TAG, str);
    }

    public static void debug(Object o) {
        if (o == null) {
            debug("null");
        } else {
            debug(o.toString());
        }
    }

    public static void info(String str) {
        Log.i(TAG, str);
    }

    public static void info(Object o) {
        if (o == null) {
            info("null");
        } else {
            info(o.toString());
        }
    }

    public static void warn(String str) {
        Log.w(TAG, str);
    }

    public static void warn(Object o) {
        if (o == null) {
            warn("null");
        } else {
            warn(o.toString());
        }
    }

    public static void error(String str) {
        Log.e(TAG, str);
    }

    public static void error(Object o) {
        if (o == null) {
            error("null");
        } else {
            error(o.toString());
        }
    }

    public static void info(String name, String msg) {
        Log.i(name, msg);
    }

    public static void info(String name, Object msg) {
        String s = msg == null ? "null" : msg.toString();
        info(name, s);
    }

}
