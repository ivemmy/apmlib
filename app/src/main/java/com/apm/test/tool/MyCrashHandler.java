package com.apm.test.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JJY on 2016/9/2.
 */
@Deprecated
public class MyCrashHandler implements Thread.UncaughtExceptionHandler {
    // 保证MyCrashHandler只有一个实例
    // 2.提供一个静态的程序变量
    private static MyCrashHandler myCrashHandler;
    private Context context;

    // 1.私有化构造方法
    private MyCrashHandler() {

    }

    // 3.暴露出来一个静态的方法 获取myCrashHandler

    public static synchronized MyCrashHandler getInstance() {
        if (myCrashHandler == null) {
            myCrashHandler = new MyCrashHandler();
        }
        return myCrashHandler;
    }

    public void init(Context context) {
        this.context = context;
    }

    // 程序发生异常的时候调用的方法
    // try catch

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        System.out.println("出现错误啦 哈哈");
        System.out.println("出现错误啦 哈哈");
        System.out.println("出现错误啦 哈哈");
        System.out.println("出现错误啦 哈哈");
        System.out.println("出现错误啦 哈哈");
        System.out.println("出现错误啦 哈哈");
        System.out.println("出现错误啦 哈哈");
        StringBuilder sb = new StringBuilder();
        // 1.获取当前应用程序的版本号.
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(context.getPackageName(),
                    0);
            sb.append("程序的版本号为" + packinfo.versionName);
            sb.append("\n");

            // 2.获取手机的硬件信息.
            Field[] fields = Build.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                // 暴力反射,获取私有的字段信息
                fields[i].setAccessible(true);
                String name = fields[i].getName();
                sb.append(name + " = ");
                String value = fields[i].get(null).toString();
                sb.append(value);
                sb.append("\n");
            }
            // 3.获取程序错误的堆栈信息 .
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);

            String result = writer.toString();
            sb.append(result);


            System.out.println(sb.toString());

            // 4.把错误信息 提交到服务器

        } catch (Exception e) {
            e.printStackTrace();
        }
/*        new Thread(new Runnable() {
            @Override
            public void run() {
                upload("123");
            }
        }).start();*/
        // 完成自杀的操作
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void upload(String content) {
        HttpURLConnection uRLConnection;
        String urlAddress = "http://192.168.1.110:8080/info?info=" + content;
        System.out.println("upload===" + urlAddress);
        try {
            URL url = new URL(urlAddress);
            uRLConnection = (HttpURLConnection) url.openConnection();
            //uRLConnection.setDoInput(true);
            uRLConnection.setDoOutput(true);
            uRLConnection.setRequestMethod("POST");
            uRLConnection.setUseCaches(false);
            uRLConnection.setConnectTimeout(5000);
            uRLConnection.setRequestProperty("Content-Type", "application/json");
            uRLConnection.connect();
            int code = uRLConnection.getResponseCode();
            //System.out.println("upload code=="+code);
            if (code != 200) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}