package com.apm.model.webview;

import android.os.Build;

/**
 * Created by Hyman on 2016/7/9.
 */
public class EventsHolder {

    /*public static String deviceId2() {
        try {
            TelephonyManager tm = (TelephonyManager) MyApp.getInstance().getSystemService("phone");
            return deviceId(tm);
        } catch (Exception e) {
        }
        return null;
    }*/

    /*private static String deviceId(TelephonyManager tm) {
        String tmDevice = "" + tm.getDeviceId();
        String tmSerial = "" + tm.getSimSerialNumber();
        String androidId = "" + Settings.Secure.getString(MyApp.getInstance().getContentResolver(), "android_id");

        UUID deviceUuid = new UUID(androidId.hashCode(), tmDevice.hashCode() << 32 | tmSerial.hashCode());

        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }*/

    private static String deviceId() {
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10;
        return m_szDevIDShort;
    }
}
