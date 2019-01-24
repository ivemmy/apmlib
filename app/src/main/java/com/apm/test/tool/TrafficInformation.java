package com.apm.test.tool;

import java.io.RandomAccessFile;

public class TrafficInformation {

    long traffic = 0;
    private int uId;

    RandomAccessFile reveiveTraffic = null;
    RandomAccessFile sendTraffic = null;

    String receivePath = "";
    String sendPath = "";

    long receiveInt = 0;
    long sendInt = 0;

    long initialTraffic = 0;
    long totalTraffic = 0;


    public TrafficInformation(int uId) {
        this.uId = uId;
    }

    public long getReceiveInt() {
        return receiveInt;
    }

    public void setReceiveInt(long receiveInt) {
        this.receiveInt = receiveInt;
    }

    public long getSendInt() {
        return sendInt;
    }

    public void setSendInt(long sendInt) {
        this.sendInt = sendInt;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public long getTraffic() {
        return traffic;
    }

    public void setTraffic(long traffic) {
        this.traffic = traffic;
    }


    public RandomAccessFile getReveiveTraffic() {
        return reveiveTraffic;
    }

    public void setReveiveTraffic(RandomAccessFile reveiveTraffic) {
        this.reveiveTraffic = reveiveTraffic;
    }

    public RandomAccessFile getSendTraffic() {
        return sendTraffic;
    }

    public void setSendTraffic(RandomAccessFile sendTraffic) {
        this.sendTraffic = sendTraffic;
    }

    public String getReceivePath() {
        return receivePath;
    }

    public void setReceivePath(String receivePath) {
        this.receivePath = receivePath;
    }

    public String getSendPath() {
        return sendPath;
    }

    public void setSendPath(String sendPath) {
        this.sendPath = sendPath;
    }

    public long getInitialTraffic() {
        return initialTraffic;
    }

    public void setInitialTraffic(long initialTraffic) {
        this.initialTraffic = initialTraffic;
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }

    public void setTotalTraffic(long totalTraffic) {
        this.totalTraffic = totalTraffic;
    }


    //获取当前的运行的应用的总流量
    public long getTotalTrafficInfo() {
        receivePath = "/proc/uid_stat/" + uId + "/tcp_rcv";
        sendPath = "/proc/uid_stat/" + uId + "/tcp_snd";
        try {
            reveiveTraffic = new RandomAccessFile(receivePath, "r");
            sendTraffic = new RandomAccessFile(sendPath, "r");
            receiveInt = Long.parseLong(reveiveTraffic.readLine());
            sendInt = Long.parseLong(sendTraffic.readLine());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reveiveTraffic.close();
                sendTraffic.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return receiveInt + sendInt;
    }

    //在固定时间间隔内的流量消耗
    public long getTrafficInfo() {
        long diffTraffic = 0;
        totalTraffic = getTotalTrafficInfo();
        diffTraffic = totalTraffic - initialTraffic;
        initialTraffic = totalTraffic;
        return diffTraffic;
    }
}
