package com.apm.util;

import android.content.Context;

import com.apm.model.DatabaseInfo;
import com.apm.model.FragmentInfo;
import com.apm.model.HttpInfo;

/**
 * Created by JJY on 2016/3/23.
 */
public class BaseInfoUtil {

    private static SystemUtils systemUtils;
    private static BaseInfoUtil instance;

    private BaseInfoUtil() {
    }

    ;

    public static BaseInfoUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (BaseInfoUtil.class) {
                if (instance == null)
                    instance = new BaseInfoUtil();
            }
        }
        systemUtils = SystemUtils.getInstance(context);
        return instance;
    }

    /**
     * 获取HttpInfo对象
     *
     * @return
     */
    public HttpInfo getHttpInfo() {
        HttpInfo httpInfo = new HttpInfo();
        // 设置httpInfo相关信息
        httpInfo.setAccessType(systemUtils.getAccessType());
        httpInfo.setAppName(systemUtils.getAppName());
        httpInfo.setAppVersion(systemUtils.getAppVersion());
        httpInfo.setOsVersion(systemUtils.getOsVersion());
        httpInfo.setManufacturer(systemUtils.getManufacturer());
        httpInfo.setAppPackage(systemUtils.getAppPackage());
        httpInfo.setDeviceId(systemUtils.getDeviceId());
        httpInfo.setAppId(Config.appId);
        httpInfo.setLoc(systemUtils.getProvince());
        httpInfo.setSimProvider(systemUtils.getSimProvider());
        return httpInfo;
    }

    public DatabaseInfo getDatabaseInfo() {
        DatabaseInfo dbInfo = new DatabaseInfo();
        // 设置DatabaseInfo相关信息
        dbInfo.setAccessType(systemUtils.getAccessType());
        dbInfo.setAppName(systemUtils.getAppName());
        dbInfo.setAppVersion(systemUtils.getAppVersion());
        dbInfo.setOsVersion(systemUtils.getOsVersion());
        dbInfo.setManufacturer(systemUtils.getManufacturer());
        dbInfo.setAppPackage(systemUtils.getAppPackage());
        dbInfo.setDeviceId(systemUtils.getDeviceId());
        dbInfo.setAppId(Config.appId);
        dbInfo.setStatus(500);
        return dbInfo;
    }

    public FragmentInfo getFragmentInfo() {
        FragmentInfo fragmentInfo = new FragmentInfo();
        fragmentInfo.setAppName(systemUtils.getAppName());
        fragmentInfo.setAppVersion(systemUtils.getAppVersion());
        fragmentInfo.setOsVersion(systemUtils.getOsVersion());
        fragmentInfo.setManufacturer(systemUtils.getManufacturer());
        fragmentInfo.setAppPackage(systemUtils.getAppPackage());
        fragmentInfo.setDeviceId(systemUtils.getDeviceId());
        fragmentInfo.setAppId(Config.appId);
        return fragmentInfo;
    }
}
