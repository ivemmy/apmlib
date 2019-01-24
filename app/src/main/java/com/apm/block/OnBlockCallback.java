package com.apm.block;

/**
 * Created by Hyman on 2018/5/5.
 */
public interface OnBlockCallback {

    void onBlockHappen(String info, float duration, long startTime, long endTime);

}
