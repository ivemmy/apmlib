package com.apm.block.utils;

import com.apm.log.ApmLogger;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Hyman on 2018/5/5.
 */
public class StackTraceRecorder {

    private static final int DEFAULT_MAX_ENTRY_COUNT = 100;
    private Thread mCurrentThread;
    private static final LinkedHashMap<Long, String> sStackMap = new LinkedHashMap<>();
    private int mMaxEntryCount = DEFAULT_MAX_ENTRY_COUNT;

    public StackTraceRecorder(Thread thread) {
        mCurrentThread = thread;
    }

    public ArrayList<String> getThreadStackEntries(long startTime, long endTime) {

        //System.out.println("getThreadStackEntries=="+startTime);

        //startTime -= 1000;
        //endTime += 1000;

        ArrayList<String> result = new ArrayList<>();
        synchronized (sStackMap) {
            for (Long entryTime : sStackMap.keySet()) {
                if (startTime < entryTime && entryTime < endTime) {
                    result.add(sStackMap.get(entryTime));
                    ApmLogger.debug("gotcha: " + sStackMap.get(entryTime));
                }
            }
        }
        return result;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    doSample();
                }
            }
        }).start();
    }

    public void doSample() {
        StringBuilder stringBuilder = new StringBuilder();

        for (StackTraceElement stackTraceElement : mCurrentThread.getStackTrace()) {
            stringBuilder.append(stackTraceElement.toString());
        }
        //System.out.println("doSample......stringBuilder=="+stringBuilder);

        synchronized (sStackMap) {
            if (sStackMap.size() == mMaxEntryCount && mMaxEntryCount > 0) {
                sStackMap.remove(sStackMap.keySet().iterator().next());
            }
            sStackMap.put(System.currentTimeMillis(), stringBuilder.toString());
        }
    }
}
