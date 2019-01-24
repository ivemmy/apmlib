package com.apm.test.tool;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.CancellationSignal;

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
public class APMSQLiteDatebase {
    private SystemUtils systemUtils;
    private SQLiteDatabase sqLiteDatabase;

    public APMSQLiteDatebase(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        long result = 0;
        if (!Config.databaseCollection)
            result = sqLiteDatabase.insert(table, nullColumnHack, values);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            dbInfo.setType(DatabaseInfo.Type.INSERT);
            dbInfo.setSql("insert into " + table.toLowerCase());


            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                result = sqLiteDatabase.insert(table, nullColumnHack, values);
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

    public int delete(String table, String whereClause, String[] whereArgs) {
        int result = 0;
        if (!Config.databaseCollection)
            result = sqLiteDatabase.delete(table, whereClause, whereArgs);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            dbInfo.setType(DatabaseInfo.Type.DELETE);
            dbInfo.setSql("delete from " + table.toLowerCase());


            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                result = sqLiteDatabase.delete(table, whereClause, whereArgs);
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

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        int result = 0;
        if (!Config.databaseCollection)
            result = sqLiteDatabase.update(table, values, whereClause, whereArgs);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            dbInfo.setType(DatabaseInfo.Type.UPDATE);
            dbInfo.setSql("upate " + table.toLowerCase());
            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                result = sqLiteDatabase.update(table, values, whereClause, whereArgs);
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

    public Cursor query(boolean distinct, String table, String[] columns,
                        String selection, String[] selectionArgs, String groupBy,
                        String having, String orderBy, String limit) {
        Cursor c = null;
        if (!Config.databaseCollection)
            c = sqLiteDatabase.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            dbInfo.setSql("select from " + table.toLowerCase());


            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                c = sqLiteDatabase.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
                dbInfo.setStatus(200);
            } catch (Exception e) {
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

    public Cursor query(boolean distinct, String table, String[] columns,
                        String selection, String[] selectionArgs, String groupBy,
                        String having, String orderBy, String limit, CancellationSignal cancellationSignal) {

        Cursor c = null;
        if (!Config.databaseCollection)
            c = sqLiteDatabase.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            dbInfo.setSql("select from " + table.toLowerCase());
            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                c = sqLiteDatabase.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
                dbInfo.setStatus(200);
            } catch (Exception e) {
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

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {


        Cursor c = null;
        if (!Config.databaseCollection)
            c = sqLiteDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            dbInfo.setSql("select from " + table.toLowerCase());
            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                c = sqLiteDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
                dbInfo.setStatus(200);
            } catch (Exception e) {
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

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy, String limit) {

        Cursor c = null;
        if (!Config.databaseCollection)
            c = sqLiteDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            dbInfo.setSql("select from " + table.toLowerCase());
            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                c = sqLiteDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
                dbInfo.setStatus(200);
            } catch (Exception e) {
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

    public Cursor queryWithFactory(SQLiteDatabase.CursorFactory cursorFactory,
                                   boolean distinct, String table, String[] columns,
                                   String selection, String[] selectionArgs, String groupBy,
                                   String having, String orderBy, String limit) {
        Cursor c = null;
        if (!Config.databaseCollection)
            c = sqLiteDatabase.queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            dbInfo.setSql("select from " + table.toLowerCase());

            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                c = sqLiteDatabase.queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
                dbInfo.setStatus(200);
            } catch (Exception e) {
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

    public Cursor queryWithFactory(SQLiteDatabase.CursorFactory cursorFactory,
                                   boolean distinct, String table, String[] columns,
                                   String selection, String[] selectionArgs, String groupBy,
                                   String having, String orderBy, String limit, CancellationSignal cancellationSignal) {

        Cursor c = null;
        if (!Config.databaseCollection)
            c = sqLiteDatabase.queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            dbInfo.setSql("select from " + table.toLowerCase());

            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                c = sqLiteDatabase.queryWithFactory(cursorFactory, distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
                dbInfo.setStatus(200);
            } catch (Exception e) {
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

    public void execSQL(String sql) throws SQLException {
        if (!Config.databaseCollection)
            return;
        else {
            System.out.println("else..." + sql);
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            setType_Sql(dbInfo, sql);
            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                sqLiteDatabase.execSQL(sql);
                dbInfo.setStatus(200);
            } catch (Exception e) {
                dbInfo.setStatus(400);
            }

            dbInfo.setEndTime(System.currentTimeMillis());
            ApmLogger.info(dbInfo);
            // 添加到收集队队中
            //DatabaseCollector.add(dbInfo);
            SendDataUtil.SendData(Converter.getDatabaseInfo(dbInfo), 6);
        }
    }

    public void execSQL(String sql, Object[] bindArgs) throws SQLException {
        if (!Config.databaseCollection)
            return;
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            setType_Sql(dbInfo, sql);
            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                sqLiteDatabase.execSQL(sql, bindArgs);
                dbInfo.setStatus(200);
            } catch (Exception e) {
                dbInfo.setStatus(400);
            }
            dbInfo.setEndTime(System.currentTimeMillis());
            ApmLogger.info(dbInfo);
            // 添加到收集队队中
            //DatabaseCollector.add(dbInfo);
            SendDataUtil.SendData(Converter.getDatabaseInfo(dbInfo), 6);
        }
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {

        Cursor c = null;
        if (!Config.databaseCollection)
            c = sqLiteDatabase.rawQuery(sql, selectionArgs);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            setType_Sql(dbInfo, sql);
            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                c = sqLiteDatabase.rawQuery(sql, selectionArgs);
                dbInfo.setStatus(200);
            } catch (Exception e) {
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

    public Cursor rawQuery(String sql, String[] selectionArgs,
                           CancellationSignal cancellationSignal) {
        Cursor c = null;
        if (!Config.databaseCollection)
            c = sqLiteDatabase.rawQuery(sql, selectionArgs, cancellationSignal);
        else {
            DatabaseInfo dbInfo = BaseInfoUtil.getInstance(ContextHolder.context).getDatabaseInfo();
            dbInfo.setDataType(DatabaseInfo.Type.DATABASE);
            // 设置数据库的版本
            dbInfo.setVersion(sqLiteDatabase.getVersion());
            setType_Sql(dbInfo, sql);
            try {
                dbInfo.setStartTime(System.currentTimeMillis());
                c = sqLiteDatabase.rawQuery(sql, selectionArgs, cancellationSignal);
                dbInfo.setStatus(200);
            } catch (Exception e) {
                dbInfo.setStatus(400);
                dbInfo.setError(e.getMessage());
            }
            dbInfo.setEndTime(System.currentTimeMillis());
            ApmLogger.info(dbInfo);
            // 添加到收集队队中
            //DatabaseCollector.add(dbInfo);
            SendDataUtil.SendData(Converter.getDatabaseInfo(dbInfo), 6);
        }
        return c;
    }

    private void setType_Sql(DatabaseInfo dbInfo, String sql) {
        sql = sql.toLowerCase();
        if (sql.startsWith("select")) {
            dbInfo.setType(DatabaseInfo.Type.SELECT);
            String resultsql = "";
            if (sql.contains("where"))
                resultsql = sql.substring(0, sql.indexOf("where") - 1);
            else
                resultsql = sql;
            dbInfo.setSql(resultsql);
        } else if (sql.startsWith("update")) {
            dbInfo.setType(DatabaseInfo.Type.UPDATE);
            String resultsql = "";
            if (sql.contains("set"))
                resultsql = sql.substring(0, sql.indexOf("set") - 1);
            else
                resultsql = sql;
            dbInfo.setSql(resultsql);
        } else if (sql.startsWith("delete")) {
            dbInfo.setType(DatabaseInfo.Type.DELETE);
            String resultsql = "";
            if (sql.contains("where"))
                resultsql = sql.substring(0, sql.indexOf("where") - 1);
            else
                resultsql = sql;
            dbInfo.setSql(resultsql);
        } else if (sql.startsWith("insert")) {
            dbInfo.setType(DatabaseInfo.Type.INSERT);
            String resultsql = "";
            if (sql.contains("values"))
                resultsql = sql.substring(0, sql.indexOf("values") - 1);
            else
                resultsql = sql;
            dbInfo.setSql(resultsql);
        } else if (sql.startsWith("create") || sql.startsWith("CREATE")) {
            dbInfo.setType(DatabaseInfo.Type.CREATE);
            dbInfo.setSql(sql);
        } else {
            dbInfo.setType(DatabaseInfo.Type.OTHER);
            dbInfo.setSql(sql);
        }
    }
}
