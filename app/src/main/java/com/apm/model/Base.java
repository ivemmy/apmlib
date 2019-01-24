package com.apm.model;

/**
 * 应用信息收集的基类
 *
 * @author 王俊超
 */
public class Base {
    // 应用的名称
    private String appName;

    // 应用的包名
    private String appPackage;

    // 应用的版本号
    private String appVersion;

    // 手出的品牌型号
    private String manufacturer;

    // 手机操作系统的版本号
    private String osVersion;

    // 网络访问类型，4G，3G，2G，WIFI等
    private String accessType;

    // 当前应用的标识Id
    private String appId;

    // 当前的设备Id
    private String deviceId;

    // 网络运营商
    private String simProvider;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSimProvider() {
        return simProvider;
    }

    public void setSimProvider(String simProvider) {
        this.simProvider = simProvider;
    }

    @Override
    public String toString() {
        return "Base [appName=" + appName + ", appPackage=" + appPackage + ", appVersion=" + appVersion
                + ", manufacturer=" + manufacturer + ", osVersion=" + osVersion + ", accessType=" + accessType
                + ", appId=" + appId + ", deviceId=" + deviceId + ", simProvider=" + simProvider + "]";
    }
}
