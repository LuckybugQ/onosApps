package org.onosproject.deviceStatistics;

import org.onosproject.net.Device;
import org.onosproject.net.DeviceId;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.device.PortStatistics;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

import org.onosproject.net.Host;
import org.onosproject.net.host.HostService;

import org.onosproject.net.device.PortStatistics;
import org.onosproject.net.device.DeviceService;

public class hostReaderTask implements Runnable{

        private  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式

        @Override
        public void run() {
            Host host = getHost();
            Date now = new Date();
            String time = dateFormat.format(now);
            //获取对应端口的流量
            PortStatistics port = getDeviceService().getDeltaStatisticsForPort(host.location().deviceId(),host.location().port());
            //统计吞吐量
            double bytesSent = port.bytesSent();
            double bytesReceived = port.bytesReceived();


            String hostId = host.id().toString();
            String IpAddress =host.ipAddresses().toString();
            String connectDeviceId =host.location().deviceId().toString();
            String connectPortId = host.location().port().toString();

            log.info("Save the Host Statistics");
            hostStatisticsTableBean save_hostStatisticsTableBean = new hostStatisticsTableBean();

            save_hostStatisticsTableBean.setTime(time);
            save_hostStatisticsTableBean.setHostId(hostId);
            save_hostStatisticsTableBean.setIpAddress(IpAddress);
            save_hostStatisticsTableBean.setConnectDeviceId(connectDeviceId);
            save_hostStatisticsTableBean.setConnectDevicePort(connectPortId);

            hostStatisticsTableOP.save(save_hostStatisticsTableBean);
            log.info("Host Id" + hostId + ";  "+"Time:" + time + ";  " + "Ip Address: " + IpAddress + " ;  " + "Connect Device Id: " + connectDeviceId + " ;  " + "Connect Port Id:" + connectPortId + ";  " );

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


    protected HostService hostService;

    public HostService getHostService() {
        return hostService;
    }

    public void setHostService(HostService hostService) {
        this.hostService = hostService;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    protected Host host;

    protected PortStatistics port;

    public DeviceService getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    protected DeviceService deviceService;

}