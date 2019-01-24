package com.apm.model;

import java.util.ArrayList;
import java.util.List;

public class ActivityRoute {
    /**
     * 设备手机设备Id
     */
    private String deviceId;
    /**
     * 当前应用的标识Id
     */
    private String appId;

    private List<Page> route = new ArrayList<Page>();

    public ActivityRoute() {
        super();
    }

    public ActivityRoute(String deviceId, String appId) {
        super();
        this.deviceId = deviceId;
        this.appId = appId;
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


    @Override
    public String toString() {
        return "{\"deviceId\":\"" + deviceId + "\", \"appId\":\"" + appId + "\", \"route\":" + route + "}";
    }


    public static class Page {
        /**
         * Activity的名称
         */
        private String name;
        /**
         * Activity的创建时间
         */
        private Long createTime;

        public Page() {
            super();
        }

        public Page(String name, Long createTime) {
            super();
            this.name = name;
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        @Override
        public String toString() {
            return "{\"name: \"" + name + "\", \"createTime\":\"" + createTime + "\"}";
        }
    }
}
