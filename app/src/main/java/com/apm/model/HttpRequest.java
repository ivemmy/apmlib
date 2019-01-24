package com.apm.model;

/**
 * Created by JJY on 2016/3/31.
 */
public class HttpRequest {
    private int id;
    private String jsonObject;
    private int type;

    public HttpRequest(String jsonObject, int type) {
        this.jsonObject = jsonObject;
        this.type = type;
    }

    public HttpRequest(int id, String jsonObject, int type) {
        this.id = id;
        this.jsonObject = jsonObject;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
