package com.apm.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jjy on 16-5-7.
 */
public class SQLLiteInstance {
    private static SQLiteDatabase instance = null;

    private SQLLiteInstance() {
    }

    ;

    public static SQLiteDatabase getInstance() {
        if (instance == null) {
            synchronized (SQLLiteInstance.class) {
                if (instance == null)
                    instance = ContextHolder.context.openOrCreateDatabase("lab_ll_apm.db", Context.MODE_PRIVATE, null);
            }
        }
        return instance;
    }
}
