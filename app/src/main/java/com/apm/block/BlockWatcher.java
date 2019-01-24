package com.apm.block;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.view.Choreographer;

import com.apm.block.framewatcher.MyFrameMonitor;
import com.apm.block.handlerwatcher.AddPoolRunnable;
import com.apm.block.handlerwatcher.BlockPool;
import com.apm.block.handlerwatcher.BlockWatchHandler;
import com.apm.block.looperwatcher.BlockPrinter;
import com.apm.block.model.BlockInfo;
import com.apm.block.utils.StackTraceRecorder;
import com.apm.log.ApmLogger;
import com.apm.service.BlockTask;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hyman on 2018/5/5.
 */
public class BlockWatcher {

    private static volatile BlockWatcher instance = null;
    private static BlockWatchHandler blockWatchHandler;
    private static BlockPool blockPool;
    private int DEFAULT_BLOCK_MSEC = 3000;
    private StackTraceRecorder stackTraceRecorder;
    private Context context;

    private BlockWatcher(Context context) {
        this.context = context;

        stackTraceRecorder = new StackTraceRecorder(Looper.getMainLooper().getThread());
        stackTraceRecorder.start();
    }

    public static BlockWatcher getInstance(Context context) {
        if (instance == null) {
            synchronized (BlockWatcher.class) {
                if (instance == null) {
                    blockPool = new BlockPool();
                    blockWatchHandler = new BlockWatchHandler(blockPool);
                    instance = new BlockWatcher(context);
                }
            }
        }
        return instance;
    }

    /**
     * 默认用Loop方案
     */
    public void startWatching() {
        startWatching_Looper(DEFAULT_BLOCK_MSEC);
    }

    public void startWatching_Looper() {
        startWatching_Looper(DEFAULT_BLOCK_MSEC);
    }

    public void startWatching_Looper(int blockmsec) {
        BlockPrinter printer = BlockPrinter.getInstance(blockmsec);
        printer.setOnBlockCallback(new OnBlockCallback() {
            @Override
            public void onBlockHappen(String info, float duration, long startTime, long endTime) {
                handleBlockInfo(info, duration, stackTraceRecorder.getThreadStackEntries(startTime, endTime));
                //NotificationUtil.sendNotification(context, "发生了卡顿", "卡顿时间为：" + duration + "ms");
                //System.out.println("卡顿回调......" + duration);
            }
        });
        Looper.getMainLooper().setMessageLogging(printer);
    }

    public void startWatching_Handler() {
        startWatching_Handler(DEFAULT_BLOCK_MSEC);
    }

    public void startWatching_Handler(int blockmsec) {
        AddPoolRunnable addPoolRunnable = new AddPoolRunnable(blockPool, blockWatchHandler, blockmsec / 1000);
        addPoolRunnable.setOnBlockCallback(new OnBlockCallback() {
            @Override
            public void onBlockHappen(String info, float duration, long startTime, long endTime) {
                handleBlockInfo(info, duration, stackTraceRecorder.getThreadStackEntries(startTime, endTime));
                //NotificationUtil.sendNotification(context, "发生了卡顿", "卡顿时间为：" + duration + "ms");
                //System.out.println("卡顿回调......" + duration);
            }
        });
        new Thread(addPoolRunnable).start();
    }

    public void startWatching_Frame() {
        startWatching_Frame(DEFAULT_BLOCK_MSEC);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startWatching_Frame(int blockmsec) {

        MyFrameMonitor.getInstance().setOnBlockCallback(new OnBlockCallback() {
            @Override
            public void onBlockHappen(String info, float duration, long startTime, long endTime) {
                handleBlockInfo(info, duration, stackTraceRecorder.getThreadStackEntries(startTime, endTime));
                //NotificationUtil.sendNotification(context, "发生了卡顿", "卡顿时间为：" + duration + "ms");
                //System.out.println("卡顿回调......" + duration);
            }
        });
        Choreographer.getInstance().postFrameCallback(MyFrameMonitor.getInstance());
    }

    private void handleBlockInfo(String info, float duration, ArrayList<String> stacktrace) {
        BlockInfo blockInfo = new BlockInfo();
        blockInfo.setDuration(duration);
        blockInfo.setInfo(info);
        blockInfo.setStacktrace(stacktrace);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        blockInfo.setDatetime(dateNowStr);

        //保存到文件
        //FileUtil.writeToFile(new Gson().toJson(blockInfo));

        ApmLogger.info("BlockWatcher - 保存卡顿：" + blockInfo.toString());
        try {
            JSONObject jObj = new JSONObject(new Gson().toJson(blockInfo));
            BlockTask.send(jObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
