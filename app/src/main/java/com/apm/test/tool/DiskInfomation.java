package com.apm.test.tool;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class DiskInfomation {
    private long availDisk;
    private long totalDisk;

    public long getTotalMemory() {
        long blockSize = sf.getBlockSize();
        long totalCount = sf.getBlockCount();
        totalDisk = (totalCount * blockSize) / 1024 / 1024 / 1024;
        return totalDisk;
    }

    public long getAvailMemory() {
        long blockSize = sf.getBlockSize();
        long availCount = sf.getAvailableBlocks();
        availDisk = (availCount * blockSize) / 1024 / 1024 / 1024;
        return availDisk;
    }


    File root = Environment.getDataDirectory();
    StatFs sf = new StatFs(root.getPath());
    long blockSize = sf.getBlockSize();

    public long[] getRomMemroy() {
        long[] romInfo = new long[2];
        //Total rom memory  
        romInfo[0] = getTotalInternalMemorySize();

        //Available rom memory  
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        romInfo[1] = blockSize * availableBlocks;
        return romInfo;
    }

    public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }


}
