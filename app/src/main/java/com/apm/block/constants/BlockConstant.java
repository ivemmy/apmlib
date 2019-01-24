package com.apm.block.constants;

import android.os.Environment;

/**
 * Created by Hyman on 2018/5/5.
 */
public class BlockConstant {
    public static final String BASE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String BLOCK_DIR = BASE_DIR + "/APMBlock";
    public static final String LEAK_DIR = BASE_DIR + "/LEAK";
}
