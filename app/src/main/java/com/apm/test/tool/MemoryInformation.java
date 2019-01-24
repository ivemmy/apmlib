package com.apm.test.tool;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MemoryInformation {

    private long totalMemory;
    private long avaliableMemory;
    private long processUsedMemory;
    private Context context;
    private int processId;


    public long getAvaliableMemory() {
        return avaliableMemory;
    }

    public void setAvaliableMemory(long avaliableMemory) {
        this.avaliableMemory = avaliableMemory;
    }

    public long getProcessUsedMemory() {
        return processUsedMemory;
    }

    public void setProcessUsedMemory(long processUsedMemory) {
        this.processUsedMemory = processUsedMemory;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public MemoryInformation(Context context, int processId) {
        this.context = context;
        this.processId = processId;
    }

    public MemoryInformation(Context context) {
        this.context = context;
    }

    //获取当前手机的总内存
    public long getTotalMemory() {
        //从系统文件/proc/meminfo文件中获取
        String filePosition = "/proc/meminfo";
        try {
            BufferedReader memInfoFile = new BufferedReader(new InputStreamReader(new FileInputStream(filePosition)), 1000);
            String memInfo = memInfoFile.readLine();
            memInfo = memInfo.split(":")[1].trim();
            memInfo = memInfo.split(" ")[0];
            totalMemory = Long.parseLong(memInfo);
            Log.v("memeory", memInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalMemory / 1024;
    }

    //获取当前可用的内存
    public long getAvaMemory() {
        ActivityManager activityMan = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo memInfo = new MemoryInfo();
        activityMan.getMemoryInfo(memInfo);
        avaliableMemory = memInfo.availMem;
        Log.v("avamemeory", "ava:" + avaliableMemory);
        return (avaliableMemory / 1024) / 1024;
    }

    //返回当前进程使用的内存
    public long getProcessUsedMem() {
        ActivityManager activityMan = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        int[] memeForProcessId = new int[]{processId};
        Debug.MemoryInfo[] memoryInfo = activityMan.getProcessMemoryInfo(memeForProcessId);
        memoryInfo[0].getTotalSharedDirty();
        processUsedMemory = memoryInfo[0].getTotalPss();
        return processUsedMemory / 1024;
    }

}
