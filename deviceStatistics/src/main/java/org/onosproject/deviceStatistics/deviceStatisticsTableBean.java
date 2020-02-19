package org.onosproject.deviceStatistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class deviceStatisticsTableBean {


    private String time;
    private String deviceId;
    private String portId;
    private String bytesReceived;
    private String bytesSend;
    private String packetsReceived;
    private String packetsSend;
    private String packetsRxErrors;
    private String packetsTxErrors;
    private String packetsRxDropped;
    private String packetsTxDropped;
    private Logger log;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDeviceId() { return deviceId; }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public String getBytesReceived() {
        return bytesReceived;
    }

    public void setBytesReceived(String bytesReceived) {
        this.bytesReceived = bytesReceived;
    }

    public String getBytesSend() {
        return bytesSend;
    }

    public void setBytesSend(String bytesSend) {
        this.bytesSend = bytesSend;
    }

    public String getPacketsReceived() {
        return packetsReceived;
    }

    public void setPacketsReceived(String packetsReceived) {
        this.packetsReceived = packetsReceived;
    }

    public String getPacketsSend() {
        return packetsSend;
    }

    public void setPacketsSend(String packetsSend) {
        this.packetsSend = packetsSend;
    }

    public String getPacketsRxErrors() {
        return packetsRxErrors;
    }

    public void setPacketsRxErrors(String packetsRxErrors) {
        this.packetsRxErrors = packetsRxErrors;
    }

    public String getPacketsTxErrors() {
        return packetsTxErrors;
    }

    public void setPacketsTxErrors(String packetsTxErrors) {
        this.packetsTxErrors = packetsTxErrors;
    }

    public String getPacketsRxDropped() {
        return packetsRxDropped;
    }

    public void setPacketsRxDropped(String packetsRxDropped) {
        this.packetsRxDropped = packetsRxDropped;
    }

    public String getPacketsTxDropped() {
        return packetsTxDropped;
    }

    public void setPacketsTxDropped(String packetsTxDropped) {
        this.packetsTxDropped = packetsTxDropped;
    }
    
} 
