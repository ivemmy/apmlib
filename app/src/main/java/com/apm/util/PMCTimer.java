package com.apm.util;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Hyman on 2016/7/9.
 */
public class PMCTimer {
    private static AtomicLong diff = new AtomicLong(0L);
    private static boolean synced = false;

    private static void getPMCServerTime() {
        try {
            long s = System.currentTimeMillis();
            URL url = new URL("");              // TODO 写个接口去取得服务器端的时间(long)
            HttpURLConnection hconn = (HttpURLConnection) url.openConnection();
            hconn.setDoInput(true);
            hconn.setDoOutput(false);
            hconn.setConnectTimeout(8000);
            hconn.setReadTimeout(8000);
            InputStream is = hconn.getInputStream();
            byte[] b = new byte[''];
            int c = 0;
            StringBuilder sb = new StringBuilder();
            while ((c = is.read(b)) != -1) {
                sb.append(new String(b, 0, c));
            }
            is.close();
            hconn.disconnect();
            long e = System.currentTimeMillis();

            String tmp = sb.toString().replaceAll("\\s+", "");

            long pmcTime = Long.parseLong(tmp);
            long roundTripTime = e - s;

            diff.set(System.currentTimeMillis() - pmcTime - roundTripTime / 2L);
            synced = true;

        } catch (ConnectTimeoutException ex) {
            //Logger.e("时间校验连接超时！");
        } catch (SocketTimeoutException ex) {
            //Logger.e("时间校验读取超时！");
        } catch (Exception e) {
            //Logger.e("时间校验异常！");
        }
    }

    public static boolean syncPMCServerTime() {
        if (!synced) {
            //getPMCServerTime();
        }
        return synced;
    }

    public static boolean isSynecd() {
        return synced;
    }

    public static void setSyncedFlag(boolean flag) {
        synced = flag;
    }

    public static long getPMCTimeMilli() {
        long rtn = 0L;
        rtn = System.currentTimeMillis() - diff.get();
        return rtn;
    }

    public static long getPMCTimeMilli(long ctime) {
        long rtn = ctime;
        if (rtn > 0L) {
            rtn -= diff.get();
        }
        return rtn;
    }

    public static long getPMCTimeMicro() {
        return getPMCTimeMilli();
    }

    public static long getDiff() {
        return diff.get();
    }

}

