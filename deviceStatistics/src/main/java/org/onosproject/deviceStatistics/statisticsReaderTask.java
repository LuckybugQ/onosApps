package org.onosproject.deviceStatistics;

import org.onosproject.net.Device;
import org.onosproject.net.DeviceId;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.device.PortStatistics;
import org.slf4j.Logger;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * Created by kspviswa-onos on 15/8/16.
 */
public class statisticsReaderTask implements Runnable{





        private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式

        private long T=5;
        private double T1=5;
        //轮询时间为5s

        @Override
        public void run() {

            //log.info("####### Into run() ");
            List<PortStatistics> portStatisticsList = getDeviceService().getPortDeltaStatistics(getDevice().id());
            Date now = new Date();
            String time = dateFormat.format( now );
            for (PortStatistics portStats : portStatisticsList) {
                if (portStats.portNumber().toLong() == getPort()) {
                    double bytesReceived = ((portStats.bytesReceived()))/T1;
                    double bytesSend=((portStats.bytesSent()))/T1;
                    long packetsSend=(portStats.packetsSent())/T;
                    long packetsReceived=((portStats.packetsReceived()))/T;
                    long packetsRxErrors=((portStats.packetsRxErrors()))/T;
                    long packetsTxErrors=((portStats.packetsTxErrors()))/T;
                    long packetsRxDropped=((portStats.packetsRxDropped()))/T;
                    long packetsTxDropped=((portStats.packetsTxDropped()))/T;
                    DeviceId deviceId = getDevice().id();
                    log.info("Save the Device Statistics");
                    deviceStatisticsTableBean save_deviceStatisticsTableBean = new deviceStatisticsTableBean();
                    save_deviceStatisticsTableBean.setTime(time);
                    save_deviceStatisticsTableBean.setBytesReceived(String.valueOf(bytesReceived));
                    save_deviceStatisticsTableBean.setBytesSend(String.valueOf(bytesSend));
                    save_deviceStatisticsTableBean.setDeviceId(deviceId.toString());
                    save_deviceStatisticsTableBean.setPortId(String.valueOf(port));
                    save_deviceStatisticsTableBean.setPacketsReceived(String.valueOf(packetsReceived));
                    save_deviceStatisticsTableBean.setPacketsSend(String.valueOf(packetsSend));
                    save_deviceStatisticsTableBean.setPacketsRxErrors(String.valueOf(packetsRxErrors));
                    save_deviceStatisticsTableBean.setPacketsTxErrors(String.valueOf(packetsTxErrors));
                    save_deviceStatisticsTableBean.setPacketsRxDropped(String.valueOf(packetsRxDropped));
                    save_deviceStatisticsTableBean.setPacketsTxDropped(String.valueOf(packetsTxDropped));
                    deviceStatisticsTableOP.save(save_deviceStatisticsTableBean);
                    log.info("Device Id"+deviceId+";  "+"Port " + port + "Time:"+time+";  "+"Bytes Sent Rate: " + bytesSend + " B/s;  "+"Bytes Received Rate: " + bytesReceived + " B/s;  "+"Packets Received:"+packetsReceived+";  "+"Packets Send:"+packetsSend+";  "+"Packets Dropped by RX:"+ packetsRxDropped+";  " +
                            "Packets Dropped by TX"+packetsTxDropped+";  "+"transmit errors:" +packetsRxErrors+";  "+"Receive Errors:"+packetsTxErrors+";  ");

                }
            }

        }





    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    private Logger log;

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    private boolean exit;


    public PortStatistics getPortStats() {
        return portStats;
    }

    public void setPortStats(PortStatistics portStats) {
        this.portStats = portStats;
    }

    private PortStatistics portStats;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private int port;

    public DeviceService getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    protected DeviceService deviceService;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    private Device device;

}
