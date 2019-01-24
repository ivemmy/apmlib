package com.apm.model;

public class HttpInfo extends Base {
    /**
     * 访问的接口URL
     */
    private String uri;
    /**
     * HTTP响应的状态
     */
    private int status;
    /**
     * HTTP响应包的大小
     */
    private int size;
    /**
     * 请求的时间，从1970.1.1号开的毫秒数
     */
    private long requestStart;
    /**
     * 响应的时间，从1970.1.1号开的毫秒数
     */
    private long requestEnd;
    /**
     * 请求类型 GET、POST等等
     */
    private String type;
    /**
     * 请求发起的位置
     */
    private String loc;
    /**
     * 请求错误发生时的信息
     */
    private String error;

    public HttpInfo() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getRequestStart() {
        return requestStart;
    }

    public void setRequestStart(long requestStart) {
        this.requestStart = requestStart;
    }

    public long getRequestEnd() {
        return requestEnd;
    }

    public void setRequestEnd(long requestEnd) {
        this.requestEnd = requestEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "HttpInfo{" +
                "uri='" + uri + '\'' +
                ", status=" + status +
                ", size=" + size +
                ", requestStart=" + requestStart +
                ", requestEnd=" + requestEnd +
                ", type='" + type + '\'' +
                ", loc='" + loc + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
