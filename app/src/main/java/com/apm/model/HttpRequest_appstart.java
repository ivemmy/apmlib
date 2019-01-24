package com.apm.model;

import java.io.File;

/**
 * Created by JJY on 2016/4/26.
 */
public class HttpRequest_appstart {
    private File file;
    private String urlAddress;

    public HttpRequest_appstart(File file, String urlAddress) {
        this.file = file;
        this.urlAddress = urlAddress;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }
}
