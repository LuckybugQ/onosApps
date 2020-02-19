package org.onosproject.deviceStatistics;

import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

import org.onosproject.net.Link;
import org.onosproject.net.link.LinkService;
import org.onosproject.net.device.DeviceService;

import org.onosproject.net.statistic.StatisticService;
import org.onosproject.net.statistic.Load;

import org.onosproject.net.LinkKey;

public class linkReaderTask implements Runnable {


        private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式



        @Override
        public void run() {
            deviceService =getDeviceService();
            double T =5;

            Link l = getLink();
            LinkKey linkKey = LinkKey.linkKey(l);
            Date now = new Date();
            String time = dateFormat.format(now);

            String linkId=linkKey.asId() ;
            String srcDevice = l.src().deviceId().toString();
            String srcPort = l.src().port().toString();
            String dstDevice = l.dst().deviceId().toString();
            String dstPort = l.dst().port().toString();
            double a = deviceService.getDeltaStatisticsForPort(l.src().deviceId(),l.src().port()).bytesSent()/T;
            double b = deviceService.getDeltaStatisticsForPort(l.dst().deviceId(),l.dst().port()).bytesReceived()/T;

            double LinkLoad = a>=b?a:b;
            String linkLoad = String.valueOf(LinkLoad);

//          String linkLoad = String.valueOf(getStatsService().load(l).rate());


            log.info("Save the Link Statistics");

            linkStatisticsTableBean save_linkStatisticsTableBean = new linkStatisticsTableBean();

            save_linkStatisticsTableBean.setTime(time);
            save_linkStatisticsTableBean.setLinkId(linkId);
            save_linkStatisticsTableBean.setSrcPort(srcPort);
            save_linkStatisticsTableBean.setSrcDevice(srcDevice);
            save_linkStatisticsTableBean.setDstPort(dstPort);
            save_linkStatisticsTableBean.setDstDevice(dstDevice);
            save_linkStatisticsTableBean.setLinkLoad(linkLoad);

            linkStatisticsTableOP.save(save_linkStatisticsTableBean);

            log.info("Link Id" + linkId + ";  "+"Time:" + time + ";  " + "Src Device: " + srcDevice + " ;  " + "Src Port: " + srcPort +
                        " ;  " + "Dst Device:" + dstDevice + ";  " +"Dst Port:"+ dstPort +";"+"Link Load:"+ linkLoad +";");

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


    protected Link link;

    protected LinkService linkService;

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public LinkService getLinkService() {
        return linkService;
    }

    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }

    public StatisticService getStatsService() {
        return statsService;
    }

    public void setStatsService(StatisticService statsService) {
        this.statsService = statsService;
    }

    private StatisticService statsService ;

    public DeviceService getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public DeviceService deviceService;


}
