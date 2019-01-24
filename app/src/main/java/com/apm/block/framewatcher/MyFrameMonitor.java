package com.apm.block.framewatcher;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.Choreographer;

import com.apm.block.OnBlockCallback;
import com.apm.log.ApmLogger;


/**
 * Created by Hyman on 2018/5/5.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MyFrameMonitor implements Choreographer.FrameCallback {
    public static MyFrameMonitor sInstance;

    private String TAG = "MyFrameMonitor";

    public static final float deviceRefreshRateMs = 16.6f;

    public static long lastFrameTimeNanos = 0;//纳秒为单位

    public static long currentFrameTimeNanos = 0;

    private static final int MSEC = 3000;

    private OnBlockCallback onBlockCallback;

    private MyFrameMonitor() {
    }

    public static MyFrameMonitor getInstance() {
        if (sInstance == null) {
            sInstance = new MyFrameMonitor();
        }
        return sInstance;
    }


    public void setOnBlockCallback(OnBlockCallback onBlockCallback) {
        this.onBlockCallback = onBlockCallback;
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        currentFrameTimeNanos = frameTimeNanos;
        float value = (currentFrameTimeNanos - lastFrameTimeNanos) / 1000000.0f;
        if (value > MSEC) {

            ApmLogger.error("MyFrameMonitor - 检测到卡顿，两次绘制时间间隔：" + value);

            long startTime = System.currentTimeMillis() - (long) value;
            onBlockCallback.onBlockHappen("", value, startTime, System.currentTimeMillis());
            lastFrameTimeNanos = currentFrameTimeNanos;
            Choreographer.getInstance().postFrameCallback(this);
            return;
        }
        lastFrameTimeNanos = currentFrameTimeNanos;

        //注册下一帧的回调
        Choreographer.getInstance().postFrameCallback(this);


        /** 或者：
         * 如果时间间隔大于最小时间间隔，3*16ms，小于最大的时间间隔，60*16ms，就认为是掉帧，累加统计该时间
         * 因为正常情况下，两帧的间隔都是在16ms以内，
         * 如果我们统计到的两帧间隔时间大于三倍的普通绘制时间，我们就认为是出现了卡顿，
         * 之所以设置最大时间间隔，是为了有时候页面不刷新绘制的时候，不做统计处理
         * if (mLastFrameNanoTime != 0) {//mLastFrameNanoTime 上一次绘制的时间
         *     long frameInterval = frameTimeNanos - mLastFrameNanoTime;//计算两帧的时间间隔
         * if (frameInterval > MIN_FRAME_TIME && frameInterval < MAX_FRAME_TIME) { }
         **/
    }

}
