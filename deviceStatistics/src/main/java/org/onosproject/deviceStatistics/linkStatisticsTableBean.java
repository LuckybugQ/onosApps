package org.onosproject.deviceStatistics;

public class linkStatisticsTableBean {
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getSrcDevice() {
        return srcDevice;
    }

    public void setSrcDevice(String srcDevice) {
        this.srcDevice = srcDevice;
    }

    public String getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort;
    }

    public String getDstDevice() {
        return dstDevice;
    }

    public void setDstDevice(String dstDevice) {
        this.dstDevice = dstDevice;
    }

    public String getDstPort() {
        return dstPort;
    }

    public void setDstPort(String dstPort) {
        this.dstPort = dstPort;
    }


    public String getLinkLoad() {
        return linkLoad;
    }

    public void setLinkLoad(String linkLoad) {
        this.linkLoad = linkLoad;
    }


    private String time;
    private String linkId;
    private String srcDevice;
    private String srcPort;
    private String dstDevice;
    private String dstPort;
    private String linkLoad;

}
