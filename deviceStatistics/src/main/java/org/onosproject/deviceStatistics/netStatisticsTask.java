package org.onosproject.deviceStatistics;

import org.onosproject.net.Host;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.device.PortStatistics;
import org.onosproject.net.host.HostService;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class netStatisticsTask implements Runnable{


        private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        //定义历史缓存数据

        public double T=5;

        @Override
        public void run() {
            Iterable<Host> hosts = hostService.getHosts();
            double bytesSent=0;
            double bytesReceived=0;
            double packetsRxDropped=0;
            double packetsRxError=0;
            double packetsReceived=0;

            //统计与主机相连的端口的数据用于网络指标的计算
            for(Host host : hosts){
                PortStatistics port = getDeviceService().getDeltaStatisticsForPort(host.location().deviceId(),host.location().port());
                bytesSent = bytesSent + port.bytesSent();
                bytesReceived = bytesReceived + port.bytesReceived();
                packetsRxDropped =packetsRxDropped+ port.packetsRxDropped();
                packetsRxError = packetsRxError +port.packetsRxErrors();
                packetsReceived = packetsReceived + port.packetsReceived();
            }



            //计算网络收发速率
            double netBytesReceivedRate = bytesReceived/T;
            double netBytesSentRate = bytesReceived/T;
            //计算网络丢包率
            double packetsLossRate;
            if(packetsReceived==0){
                packetsLossRate = 0;
            }
            else{
                packetsLossRate=(packetsRxDropped+packetsRxError)/packetsReceived;
            }

            Date now = new Date();
            String time = dateFormat.format(now);
            String netPacketsLossRate = String.valueOf(packetsLossRate);

            //存储网络信息
            log.info("Save the Net Statistics");
            netStatisticsTableBean save_netStatisticsTableBean = new netStatisticsTableBean();

            save_netStatisticsTableBean.setTime(time);
            save_netStatisticsTableBean.setNetBytesSentRate(String.valueOf(netBytesSentRate));
            save_netStatisticsTableBean.setNetBytesReceivedRate(String.valueOf(netBytesReceivedRate));
            save_netStatisticsTableBean.setNetPacketsLossRate(netPacketsLossRate);
            netStatisticsTableOP.save(save_netStatisticsTableBean);

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
