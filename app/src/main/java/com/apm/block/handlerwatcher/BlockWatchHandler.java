package com.apm.block.handlerwatcher;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Hyman on 2018/5/5.
 */
public class BlockWatchHandler extends Handler {

    private BlockPool blockPool;

    public BlockWatchHandler(BlockPool blockPool) {
        this.blockPool = blockPool;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        blockPool.minusCount();
    }
}
