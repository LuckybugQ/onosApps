package org.onosproject.deviceStatistics;

public class hostStatisticsTableBean {
    private String time;
    private String hostId;
    private String ipAddress;
    private String connectDeviceId;
    private String connectDevicePort;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getConnectDeviceId() {
        return connectDeviceId;
    }

    public void setConnectDeviceId(String connectDeviceId) {
        this.connectDeviceId = connectDeviceId;
    }

    public String getConnectDevicePort() {
        return connectDevicePort;
    }

    public void setConnectDevicePort(String connectDevicePort) {
        this.connectDevicePort = connectDevicePort;
    }



}
