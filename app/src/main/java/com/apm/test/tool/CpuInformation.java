package com.apm.test.tool;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class CpuInformation {
    //当前的运行的应用的进程ID
    private int processId;
    private long processUsedCpu;
    private long freeCpu;
    private long totalCpu;
    private long processUsedCpu1;
    private long freeCpu1;
    private long totalCpu1;
    private double processCpuPercentage = 0;
    private double totalUsed = 0.0;


    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public long getProcessUsedCpu() {
        return processUsedCpu;
    }

    public void setProcessUsedCpu(long processUsedCpu) {
        this.processUsedCpu = processUsedCpu;
    }

    public long getFreeCpu() {
        return freeCpu;
    }

    public void setFreeCpu(long freeCpu) {
        this.freeCpu = freeCpu;
    }

    public long getTotalCpu() {
        return totalCpu;
    }

    public void setTotalCpu(long totalCpu) {
        this.totalCpu = totalCpu;
    }

    public long getProcessUsedCpu1() {
        return processUsedCpu1;
    }

    public void setProcessUsedCpu1(long processUsedCpu1) {
        this.processUsedCpu1 = processUsedCpu1;
    }

    public long getFreeCpu1() {
        return freeCpu1;
    }

    public void setFreeCpu1(long freeCpu1) {
        this.freeCpu1 = freeCpu1;
    }

    public long getTotalCpu1() {
        return totalCpu1;
    }

    public void setTotalCpu1(long totalCpu1) {
        this.totalCpu1 = totalCpu1;
    }

    public double getProcessCpuPercentage() {
        return processCpuPercentage;
    }

    public void setProcessCpuPercentage(double processCpuPercentage) {
        this.processCpuPercentage = processCpuPercentage;
    }

    public CpuInformation(int processId) {
        this.processId = processId;
    }

    //读取每个运行的应用的进程CPU信息
    public double readCpu() {
        String runProcessId = Integer.toString(processId);
        //获得总的CPU实用情况
        try {
            BufferedReader totalCpuInfoFile = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")), 1000);
            String toatlCpuInfo = totalCpuInfoFile.readLine();

            Log.v("totaiCpu", toatlCpuInfo);
            String[] totalInfo = toatlCpuInfo.split(" ");

            //totalInfo的第二个字符串是空的，计算总的CPU时间从第三个值开始

            totalCpu = Long.parseLong(totalInfo[8]) + Long.parseLong(totalInfo[2]) + Long.parseLong(totalInfo[3]) + Long.parseLong(totalInfo[4])
                    + Long.parseLong(totalInfo[6]) + Long.parseLong(totalInfo[5]) + Long.parseLong(totalInfo[7]);
            freeCpu = Long.parseLong(totalInfo[5]);
            totalUsed = ((totalCpu - freeCpu) * 100) / totalCpu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // /proc/pid/stat文件中存了关于当前进程Id相关的信息
        String processStatPosition = "/proc/" + runProcessId + "/stat";
        try {

            BufferedReader cpuInfoFile = new BufferedReader(new InputStreamReader(new FileInputStream(processStatPosition)), 1000);
            String cpuInfo = cpuInfoFile.readLine();
            String[] info = cpuInfo.split(" ");
            processUsedCpu = Long.parseLong(info[13]) + Long.parseLong(info[14]) + Long.parseLong(info[15]) + Long.parseLong(info[16]);
            Log.v("length", Long.toString(processUsedCpu));
            cpuInfoFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalUsed;
    }

    //计算当前运行的应用的CPU使用率
    public double computeCpuPercentage() {
        DecimalFormat df = new DecimalFormat(".00");
        //获取当前时间的CPU实用情况
        readCpu();
        //计算CPU实用率公式
        processCpuPercentage = (100 * ((double) (processUsedCpu - processUsedCpu1))) / (double) (totalCpu - totalCpu1);
        processUsedCpu1 = processUsedCpu;
        totalCpu1 = totalCpu;
        String result = df.format(processCpuPercentage);
        processCpuPercentage = Double.parseDouble(result);
        return processCpuPercentage;
    }

}
