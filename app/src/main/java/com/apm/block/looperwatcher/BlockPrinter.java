package com.apm.block.looperwatcher;

import android.os.SystemClock;
import android.util.Printer;

import com.apm.block.BlockWatcher;
import com.apm.block.OnBlockCallback;

/**
 * Created by Hyman on 2018/5/5.
 */
public class BlockPrinter implements Printer {

    private boolean mPrintingStarted = false;
    private long mStartTimestamp = 0;
    private long mStartThreadTimestamp = 0;
    private static final int DEFAULT_BLOCK_THRESHOLD_MILLIS = 3000;
    private static long mBlockThresholdMillis = DEFAULT_BLOCK_THRESHOLD_MILLIS;
    private static BlockPrinter instance = null;
    private OnBlockCallback onBlockCallback;

    public void setOnBlockCallback(OnBlockCallback onBlockCallback) {
        this.onBlockCallback = onBlockCallback;
    }

    private BlockPrinter() {
    }

    public static BlockPrinter getInstance(int blockmsec) {
        if (instance == null) {
            synchronized (BlockWatcher.class) {
                if (instance == null) {
                    mBlockThresholdMillis = blockmsec;
                    instance = new BlockPrinter();
                }
            }
        }
        return instance;
    }

    @Override
    public void println(String s) {
        if (!mPrintingStarted) {
            mStartTimestamp = System.currentTimeMillis();
            mStartThreadTimestamp = SystemClock.currentThreadTimeMillis();
            mPrintingStarted = true;
        } else {
            final long endTime = System.currentTimeMillis();
            mPrintingStarted = false;
            if (isBlocked(endTime)) {
                onBlockCallback.onBlockHappen("",
                        endTime - mStartTimestamp,
                        System.currentTimeMillis() - endTime + mStartTimestamp,
                        System.currentTimeMillis());
            }
        }
    }

    private boolean isBlocked(long endTime) {
        return endTime - mStartTimestamp > mBlockThresholdMillis;
    }

}
