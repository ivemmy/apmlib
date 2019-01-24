package com.apm.test.tool;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.apm.log.ApmLogger;
import com.apm.model.DatabaseInfo;
import com.apm.util.BaseInfoUtil;
import com.apm.util.Config;
import com.apm.util.ContextHolder;
import com.apm.util.Converter;
import com.apm.util.SendDataUtil;
import com.apm.util.SystemUtils;

/**
 * Created by JJY on 2016/3/23.
 */
public class APMContentResolver {
    private SystemUtils systemUtils;
    private ContentResolver contentResolver;

    public APMContentResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public final @Nullable
    Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                 @Nullable String selection, @Nullable String[] selectionArgs,
                 @Nullable String sortOrder) {

        Cursor c = null;
        if (!Config.databaseCollection)
            c = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.PROVIDER);
            dbInfo.setType(DatabaseInfo.Type.SELECT);
            dbInfo.setSql("select from " + uri.toString());
            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                c = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
                dbInfo.setStatus(200);
            } catch (Exception e) {
                e.printStackTrace();
                dbInfo.setStatus(400);
            }
            dbInfo.setEndTime(System.currentTimeMillis());
            ApmLogger.info(dbInfo);
            // 添加到收集队队中
            //DatabaseCollector.add(dbInfo);
            SendDataUtil.SendData(Converter.getDatabaseInfo(dbInfo), 6);
        }
        return c;
    }

    public final @Nullable
    Cursor query(final @NonNull Uri uri, @Nullable String[] projection,
                 @Nullable String selection, @Nullable String[] selectionArgs,
                 @Nullable String sortOrder, @Nullable CancellationSignal cancellationSignal) {
        Cursor c = null;
        if (!Config.databaseCollection)
            c = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.PROVIDER);
            dbInfo.setType(DatabaseInfo.Type.SELECT);
            dbInfo.setSql("select from " + uri.toString());

            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                c = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
                dbInfo.setStatus(200);
            } catch (Exception e) {
                e.printStackTrace();
                dbInfo.setStatus(400);
            }
            dbInfo.setEndTime(System.currentTimeMillis());
            ApmLogger.info(dbInfo);
            // 添加到收集队队中
            //DatabaseCollector.add(dbInfo);
            SendDataUtil.SendData(Converter.getDatabaseInfo(dbInfo), 6);
        }
        return c;
    }

    public final @Nullable
    Uri insert(@NonNull Uri url, @Nullable ContentValues values) {
        Uri resulturi = null;
        if (!Config.databaseCollection)
            resulturi = contentResolver.insert(url, values);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.PROVIDER);
            dbInfo.setType(DatabaseInfo.Type.INSERT);
            dbInfo.setSql("insert into " + url.toString());

            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                resulturi = contentResolver.insert(url, values);
                dbInfo.setStatus(200);
            } catch (Exception e) {
                e.printStackTrace();
                dbInfo.setStatus(400);
            }
            dbInfo.setEndTime(System.currentTimeMillis());
            ApmLogger.info(dbInfo);
            // 添加到收集队队中
            //DatabaseCollector.add(dbInfo);
            SendDataUtil.SendData(Converter.getDatabaseInfo(dbInfo), 6);
        }
        return resulturi;
    }

    public final int update(@NonNull Uri uri, @Nullable ContentValues values,
                            @Nullable String where, @Nullable String[] selectionArgs) {

        int result = 0;
        if (!Config.databaseCollection)
            result = contentResolver.update(uri, values, where, selectionArgs);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.PROVIDER);
            dbInfo.setType(DatabaseInfo.Type.UPDATE);
            dbInfo.setSql("upate " + uri.toString());
            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                result = contentResolver.update(uri, values, where, selectionArgs);
                dbInfo.setStatus(200);
            } catch (Exception e) {
                e.printStackTrace();
                dbInfo.setStatus(400);

            }
            dbInfo.setEndTime(System.currentTimeMillis());
            ApmLogger.info(dbInfo);
            // 添加到收集队队中
            //DatabaseCollector.add(dbInfo);
            SendDataUtil.SendData(Converter.getDatabaseInfo(dbInfo), 6);
        }
        return result;
    }

    public final int delete(@NonNull Uri url, @Nullable String where,
                            @Nullable String[] selectionArgs) {

        int result = 0;
        if (!Config.databaseCollection)
            result = contentResolver.delete(url, where, selectionArgs);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.PROVIDER);
            dbInfo.setType(DatabaseInfo.Type.DELETE);
            dbInfo.setSql("delete from " + url.toString());
            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                result = contentResolver.delete(url, where, selectionArgs);
                dbInfo.setStatus(200);
            } catch (Exception e) {
                e.printStackTrace();
                dbInfo.setStatus(400);

            }
            dbInfo.setEndTime(System.currentTimeMillis());
            ApmLogger.info(dbInfo);
            // 添加到收集队队中
            //DatabaseCollector.add(dbInfo);
            SendDataUtil.SendData(Converter.getDatabaseInfo(dbInfo), 6);
        }
        return result;
    }

    public final @Nullable
    Bundle call(@NonNull Uri uri, @NonNull String method,
                @Nullable String arg, @Nullable Bundle extras) {

        Bundle bundle = null;
        if (!Config.databaseCollection)
            bundle = contentResolver.call(uri, method, arg, extras);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.PROVIDER);
            dbInfo.setType(DatabaseInfo.Type.OTHER);
            dbInfo.setSql("call " + uri.toString());

            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                bundle = contentResolver.call(uri, method, arg, extras);
                dbInfo.setStatus(200);
            } catch (Exception e) {
                e.printStackTrace();
                dbInfo.setStatus(400);
            }
            dbInfo.setEndTime(System.currentTimeMillis());
            ApmLogger.info(dbInfo);
            // 添加到收集队队中
            //DatabaseCollector.add(dbInfo);
            SendDataUtil.SendData(Converter.getDatabaseInfo(dbInfo), 6);
        }
        return bundle;
    }


}
