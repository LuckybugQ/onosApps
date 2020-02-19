package org.onosproject.deviceStatistics;

public class netStatisticsTableBean {


    private String time;
    private String netBytesSentRate;
    private String netBytesReceivedRate;
    private String netPacketsLossRate;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNetBytesSentRate() {
        return netBytesSentRate;
    }

    public void setNetBytesSentRate(String netBytesSentRate) {
        this.netBytesSentRate = netBytesSentRate;
    }

    public String getNetBytesReceivedRate() {
        return netBytesReceivedRate;
    }

    public void setNetBytesReceivedRate(String netBytesReceivedRate) {
        this.netBytesReceivedRate = netBytesReceivedRate;
    }

    public String getNetPacketsLossRate() {
        return netPacketsLossRate;
    }

    public void setNetPacketsLossRate(String netPacketsLossRate) {
        this.netPacketsLossRate = netPacketsLossRate;
    }


}
