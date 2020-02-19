package org.onosproject.predict;

import org.onosproject.cluster.ClusterService;
import org.onosproject.cpman.ControlPlaneMonitorService;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.host.HostService;
import org.onosproject.net.link.LinkService;
import org.onosproject.net.statistic.StatisticService;
import org.onosproject.net.Link;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component(immediate = true)
public class predict implements predictService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private double prediction=0;
    private linkTask linktask;

    public double getpredict(){
        return linktask.getPrediction();
    }
    public double getReal(){return linktask.getRealLinkLoad();}

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

    private ScheduledExecutorService scheduledThreadPool;

    @Activate
    protected void activate() {

        log.info("Started");
        scheduledThreadPool = Executors.newScheduledThreadPool(100);

        //Read and save link statistics
        Iterable<Link> links = linkService.getLinks();
        String srcId = "of:0000000000000001";
        String dstId = "of:0000000000000002";
        for(Link l :links){
            if((l.src().deviceId().toString().equals(srcId))&&(l.dst().deviceId().toString().equals(dstId))){
                try {
                    log.info("Start Predict Task");
                    linkTask task = new linkTask();
                    task.setExit(false);
                    task.setLog(log);
                    task.setLink(l);
                    task.setDeviceService(deviceService);
                    scheduledThreadPool.scheduleAtFixedRate(task, 0,5, TimeUnit.SECONDS);
                    linktask=task;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }



    @Deactivate
    protected void deactivate() {
        scheduledThreadPool.shutdown();

    }


}
