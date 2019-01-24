package com.apm.model;

/**
 * Fragment创建的信息
 *
 * @author 王俊超
 */
public class FragmentInfo {
    // Fragment名称
    private String name;
    // Fragment所在的Activity名称
    private String wraper;
    // Fragment开始创建的时间
    private long beforeCreate;
    // Fragment完成创建的时间
    private long afterCreate;

    // // 应用的名称
    private String appName;
    // // 应用的包名
    private String appPackage;
    // // 应用的版本号
    private String appVersion;
    // // 手出的品牌型号
    private String manufacturer;
    // // 手机操作系统的版本号
    private String osVersion;
    // // 网络访问类型
    private String accessType;
    // // 当前应用的标识Id
    private String appId;
    // // 当前的设备Id
    private String deviceId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWraper() {
        return wraper;
    }

    public void setWraper(String wraper) {
        this.wraper = wraper;
    }

    public long getBeforeCreate() {
        return beforeCreate;
    }

    public void setBeforeCreate(long beforeCreate) {
        this.beforeCreate = beforeCreate;
    }

    public long getAfterCreate() {
        return afterCreate;
    }

    public void setAfterCreate(long afterCreate) {
        this.afterCreate = afterCreate;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
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

    @Override
    public String toString() {
        return "FragmentInfo{" +
                "name='" + name + '\'' +
                ", wraper='" + wraper + '\'' +
                ", beforeCreate=" + beforeCreate +
                ", afterCreate=" + afterCreate +
                ", appName='" + appName + '\'' +
                ", appPackage='" + appPackage + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", accessType='" + accessType + '\'' +
                ", appId='" + appId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
