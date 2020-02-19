package org.onosproject.deviceStatistics;

import java.util.Date;
import java.util.TimerTask;


import org.onosproject.cpman.ControlLoadSnapshot;
import org.onosproject.cpman.ControlMetricType;
import org.onosproject.cpman.ControlPlaneMonitorService;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.onosproject.net.Device;
import org.onosproject.net.DeviceId;
import org.onosproject.net.device.DeviceService;

import org.onosproject.cluster.ClusterService;

import static org.onosproject.cpman.ControlMetricType.INBOUND_PACKET;
import static org.onosproject.cpman.ControlResource.CONTROL_MESSAGE_METRICS;

import org.onosproject.ui.RequestHandler;
import org.onosproject.ui.UiMessageHandler;
import org.onosproject.ui.chart.ChartModel;
import org.onosproject.ui.chart.ChartRequestHandler;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

public class packetInReaderTask implements Runnable{

        private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式

        @Override
        public void run() {

                Iterable<Device> device = deviceService.getDevices();
                ControlMetricType cmt = INBOUND_PACKET;

                //用于计算packet_in包的数量（inbound_packet即为packet_in）
                long count= 0;
                //调用cpman应用中的服务来获得单个设备发送的packet_in的包个数，再累加获得总个数
                //log.info(cs.getLocalNode().id().toString());
                for(Device d:device){
                    ControlLoadSnapshot cls = controlPlaneMonitorService.getLoadSync(cs.getLocalNode().id(), cmt,  Optional.of(d.id()));
                    count=count+cls.latest();

                }
                //将packet_in的数量转化为字符串格式再保存
                String numOfPacketIn = String.valueOf(count) ;

                Date now = new Date();
                String time = dateFormat.format(now);


                log.info("Save the Packet_In Statistics");
                packetInStatisticsTableBean save_packetInStatisticsTableBean = new packetInStatisticsTableBean();

                save_packetInStatisticsTableBean.setTime(time);

                save_packetInStatisticsTableBean.setNumOfPacketIn(numOfPacketIn);

                packetInStatisticsTableOP.save(save_packetInStatisticsTableBean);
                log.info("Time: " + time + ";  " + "Number of Packet-In per second: " + numOfPacketIn + " /s;  "  );

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


    public DeviceService deviceService;


    public DeviceService getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }


    public ControlPlaneMonitorService getControlPlaneMonitorService() {
        return controlPlaneMonitorService;
    }

    public void setControlPlaneMonitorService(ControlPlaneMonitorService controlPlaneMonitorService) {
        this.controlPlaneMonitorService = controlPlaneMonitorService;
    }

    private ControlPlaneMonitorService controlPlaneMonitorService;

    public ClusterService getCs() {
        return cs;
    }

    public void setCs(ClusterService cs) {
        this.cs = cs;
    }

    private ClusterService cs;

}
