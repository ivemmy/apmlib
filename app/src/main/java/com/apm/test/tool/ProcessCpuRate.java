package com.apm.test.tool;

import android.os.Build;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class ProcessCpuRate {
    private int pid;

    public ProcessCpuRate(int pid) {
        this.pid = pid;
    }

    public float getProcessCpuRate() {

        float totalCpuTime1 = getTotalCpuTime();
        float processCpuTime1 = getAppCpuTime();
        try {
            Thread.sleep(360);

        } catch (Exception e) {
        }

        float totalCpuTime2 = getTotalCpuTime();
        float processCpuTime2 = getAppCpuTime();

        float cpuRate = 100 * (processCpuTime2 - processCpuTime1)
                / (totalCpuTime2 - totalCpuTime1);

        return cpuRate;
    }

    public long getTotalCpuTime() { // 获取系统总CPU使用时间
        String[] cpuInfos = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
            long totalCpu = Long.parseLong(cpuInfos[2])
                    + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
                    + Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
                    + Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
            return totalCpu;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        }

    }

    public long getAppCpuTime() { // 获取应用占用的CPU时间

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
                        return new BigDecimal(info[9]).setScale(3, BigDecimal.ROUND_HALF_UP).longValue();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String[] cpuInfos = null;
        try {
            int pid = android.os.Process.myPid();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + pid + "/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
            long appCpuTime = Long.parseLong(cpuInfos[13])
                    + Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15])
                    + Long.parseLong(cpuInfos[16]);
            return appCpuTime;
        } catch (IOException ex) {
            System.out.println("发生崩溃时记录CPU");
            ex.printStackTrace();
            return 1;
        }

    }
}

