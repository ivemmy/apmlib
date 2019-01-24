package com.apm.block.handlerwatcher;

import com.apm.block.OnBlockCallback;
import com.apm.log.ApmLogger;

/**
 * Created by Hyman on 2018/5/5.
 */
public class AddPoolRunnable implements Runnable {

    private BlockPool blockPool;
    private BlockWatchHandler blockWatchHandler;
    private int blockmsec;
    private OnBlockCallback onBlockCallback;
    private boolean isBlock = false;
    private int lagTime = 0;

    public void setOnBlockCallback(OnBlockCallback onBlockCallback) {
        this.onBlockCallback = onBlockCallback;
    }

    public AddPoolRunnable(BlockPool blockPool, BlockWatchHandler blockWatchHandler, int blockmsec) {
        this.blockPool = blockPool;
        this.blockWatchHandler = blockWatchHandler;
        this.blockmsec = blockmsec;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            blockPool.addCount();
            blockWatchHandler.sendEmptyMessage(0);

            ApmLogger.error("AddPoolRunnable - 检测到卡顿，时间：" + blockPool.getCount());

            if (blockPool.getCount() > blockmsec) {
                if (!isBlock) {
                    isBlock = true;
                }
                lagTime = blockPool.getCount();
            } else {
                if (isBlock) {
                    onBlockCallback.onBlockHappen("", lagTime * 1000,
                            System.currentTimeMillis() - lagTime * 1000, System.currentTimeMillis());
                    isBlock = false;
                }
            }
        }
    }
}