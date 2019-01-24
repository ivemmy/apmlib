package com.apm.model;

/**
 * @author 王俊超
 */
public class DatabaseInfo extends Base {

    public static interface Type {
        String INSERT = "INSERT";
        String UPDATE = "UPDATE";
        String DELETE = "DELETE";
        String SELECT = "SELECT";
        String OTHER = "OTHER";
        String DATABASE = "DATABASE";
        String PROVIDER = "PROVIDER";
        String DROP = "DROP";
        String CREATE = "CREATE";
    }

    /**
     * 数据库的版本号
     */
    private int version;

    /**
     * 数据库操作的类型，增删改查等
     */
    private String type;

    /**
     * 执行的SQL概要信息
     */
    private String sql;

    /**
     * sql执行的时间
     */
    private long startTime;

    /**
     * 结束时间
     */
    private long endTime;

    /**
     * 操作结果
     */
    private long status;

    private String dataType;

    /**
     * 操作错误时的内容
     */
    private String error;

    public DatabaseInfo() {

    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "DatabaseInfo [version=" + version + ", type=" + type + ", sql=" + sql + ", startTime=" + startTime
                + ", endTime=" + endTime + ", status=" + status + ", dataType=" + dataType + "]";
    }


}
