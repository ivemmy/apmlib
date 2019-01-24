package com.apm.model;

/**
 * 简单的ActivityInfo对象
 *
 * @author 王俊超
 */
public class SimpleActivityInfo {
    /**
     * Activity的名称
     */
    private String name;
    /**
     * Activity的创建时间
     */
    private Long createTime;

    public SimpleActivityInfo() {
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

    public void setCreateTime(Long beforeCteate) {
        this.createTime = beforeCteate;
    }

    @Override
    public String toString() {
        return "SimpleActivityInfo [name=" + name + ", createTime=" + createTime + "]";
    }
}
