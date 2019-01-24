package com.apm.block.handlerwatcher;

/**
 * Created by Hyman on 2018/5/5.
 */
public class BlockPool {

    private Integer count;

    public BlockPool() {
        count = 0;
    }

    public Integer getCount() {
        return count;
    }

    public void addCount() {
        this.count++;
    }

    public void minusCount() {
        this.count--;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
