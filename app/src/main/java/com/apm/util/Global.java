package com.apm.util;


public class Global {

    /**
     * 是否调用了Activity的OnPaused方法
     */
    private static volatile boolean isOnPaused = false;
    private static volatile long lastPausedTime;

    /**
     * <pre>
     * 是否是一次新的启动，在应用启动的时候会检查这个，如果是true就会设置为false，
     * 在应用关闭闭或者进入后台的时候，这个会再次被设置这true
     * </pre>
     */
    private static volatile boolean newStart = true;

    /**
     * 判断应用是否在运行
     *
     * @return
     */
    public static boolean isRunning() {
        return !(isOnPaused && System.currentTimeMillis() - lastPausedTime > Config.maxNoOpTime);
        //	return !isOnPaused();
    }

    public static boolean noOptimeout() {
        return System.currentTimeMillis() - lastPausedTime > Config.maxNoOpTime;
    }

    /**
     * 应用是否退出运行
     *
     * @return
     */
    public static boolean isExist() {
        return !isRunning();
    }

    public static boolean isOnPaused() {
        return isOnPaused;
    }

    public static void setOnPaused(boolean isOnPaused) {
        Global.isOnPaused = isOnPaused;
        // 如果调用了Activity的方法就记录onPaused的时间
        if (isOnPaused) {
            lastPausedTime = System.currentTimeMillis();
        }

    }

    public static boolean isNewStart() {
        return newStart;
    }

    public static void setNewStart(boolean newStart) {
        Global.newStart = newStart;
    }

    public static long getLastPausedTime() {
        return lastPausedTime;
    }


}
