package org.onosproject.deviceStatistics;

import org.onosproject.cluster.ClusterService;
import org.onosproject.cpman.ControlPlaneMonitorService;
import org.onosproject.net.Device;
import org.onosproject.net.Host;
import org.onosproject.net.HostLocation;
import org.onosproject.net.Port;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.host.HostService;
import org.onosproject.net.device.PortStatistics;
import org.onosproject.net.link.LinkService;
import org.onosproject.net.statistic.StatisticService;
import org.onosproject.net.Link;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component(immediate = true)
public class deviceStatistics  {


/**
 * Skeletal ONOS application component.
 */



    private final Logger log = LoggerFactory.getLogger(getClass());


    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected DeviceService deviceService;//与现有基础设备交互服务

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected HostService hostService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected LinkService linkService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected StatisticService statsService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected ControlPlaneMonitorService controlPlaneMonitorService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected ClusterService cs;

    ScheduledExecutorService scheduledThreadPool;

    @Activate
    protected void activate() {

        log.info("Started");
        scheduledThreadPool = Executors.newScheduledThreadPool(100);

        //Read and save devices statistics

        Iterable<Device> devices = deviceService.getDevices();

        for (Device d : devices) {
            log.info("#### [viswa] Device id " + d.id().toString());

            List<Port> ports = deviceService.getPorts(d.id());
            for (Port port: ports) {
                log.info("Getting info for port" + port.number());
                PortStatistics portstat = deviceService.getStatisticsForPort(d.id(), port.number());
                PortStatistics portdeltastat = deviceService.getDeltaStatisticsForPort(d.id(), port.number());
                if (portstat != null)
                    log.info("portstat bytes recieved" + portstat.bytesReceived());
                else
                    log.info("Unable to read portStats");

                if (portdeltastat != null)
                    log.info("portdeltastat bytes recieved" + portdeltastat.bytesReceived());
                else
                    log.info("Unable to read portDeltaStats");
            }


            List<PortStatistics> portStatisticsList = deviceService.getPortDeltaStatistics(d.id());
            for (PortStatistics portStats : portStatisticsList) {
                try {
                        log.info(String.valueOf(portStats));
                        int port = (int) portStats.portNumber().toLong();
                        statisticsReaderTask task = new statisticsReaderTask();
                        task.setExit(false);
                        task.setLog(log);
                        task.setPort(port);
                        task.setDeviceService(deviceService);
                        task.setDevice(d);
                        scheduledThreadPool.scheduleAtFixedRate(task, 0,5, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


        //Read and save hosts statistics

        Iterable<Host> hosts = hostService.getHosts();
        for (Host h : hosts){
            log.info("#### [viswa] Host id " + h.id().toString());
            HostLocation hostLocation = h.location();
                try {

                    hostReaderTask task = new hostReaderTask();
                    task.setExit(false);
                    task.setLog(log);
                    task.setHost(h);
                    task.setDeviceService(deviceService);
                    scheduledThreadPool.scheduleAtFixedRate(task, 0,5, TimeUnit.SECONDS);

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }


        //Read and save link statistics
        Iterable<Link> links = linkService.getLinks();

        for(Link l :links){
            try {
                linkReaderTask task = new linkReaderTask();
                task.setExit(false);
                task.setLog(log);
                task.setLink(l);
                task.setDeviceService(deviceService);
                scheduledThreadPool.scheduleAtFixedRate(task, 0,10, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Read and save Packet-In Statistics

        try {
            packetInReaderTask task = new packetInReaderTask();
            task.setExit(false);
            task.setLog(log);
            task.setDeviceService(deviceService);
            //log.info(cs.getLocalNode().id().toString());
            task.setCs(cs);
            task.setControlPlaneMonitorService(controlPlaneMonitorService);
            scheduledThreadPool.scheduleAtFixedRate(task, 0,10, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Read and save net Statistics

        try {
            netStatisticsTask task = new netStatisticsTask();
            task.setExit(false);
            task.setLog(log);
            task.setDeviceService(deviceService);
            //log.info(cs.getLocalNode().id().toString());
            task.setHostService(hostService);
            scheduledThreadPool.scheduleAtFixedRate(task, 0,10, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Deactivate
    protected void deactivate() {
        scheduledThreadPool.shutdown();

    }


}
