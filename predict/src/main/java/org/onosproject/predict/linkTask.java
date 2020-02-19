package org.onosproject.predict;

import org.onosproject.net.Link;
import org.onosproject.net.LinkKey;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.link.LinkService;
import org.onosproject.net.statistic.StatisticService;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class linkTask implements Runnable {


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
    //private int count = 0 ;
    //private float pre_data = 0;
    //private double[] data= new double[10];
    private LinkedList<Double> data = new LinkedList<>();
    private int T =5;
    private int number = 10;
    @Override
    public void run() {
        deviceService =getDeviceService();
        Link l = getLink();
        LinkKey linkKey = LinkKey.linkKey(l);
        Date now = new Date();
        String time = dateFormat.format(now);

        String linkId=linkKey.asId() ;
        double sent = deviceService.getDeltaStatisticsForPort(l.src().deviceId(),l.src().port()).bytesSent()/T;
        double received = deviceService.getDeltaStatisticsForPort(l.dst().deviceId(),l.dst().port()).bytesReceived()/T;

        double LL = sent>=received?sent:received;

        //缓存10个历史数据
        if(data.size()<number){
            data.addLast(LL);
        }
        else{
            data.removeFirst();
            data.addLast(LL);
            realLinkLoad =LL;
            //当缓存够10个数据后开始预测
            //调用python文件进行预测
            try {
                String[] args = new String[number+2];
                args[0] = "python";
                args[1] = "/home/zzq/python/lstm_pre.py";
                for(int i=2;i<args.length;i++){
                    args[i++] = String.valueOf(data.get(i-2));
                }
                Process proc = Runtime.getRuntime().exec(args);// 执行py文件
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String line = null;
                while ((line = in.readLine()) != null) {
                    log.info("real:"+String.valueOf(realLinkLoad));
                    log.info("predict:"+line);
                    prediction=Double.valueOf(line);
                    if(prediction<0)prediction=0;
                }
                in.close();
                proc.waitFor();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();


            }
//            存入数据库
//            String linkLoad = String.valueOf(prediction);
//            log.info("Save the Link Prediction");
//            linkPredictTableBean save_linkPredictTableBean = new linkPredictTableBean();
//            save_linkPredictTableBean.setTime(time);
//            save_linkPredictTableBean.setLinkId(linkId);
//            save_linkPredictTableBean.setLinkLoad(linkLoad);
//            linkPredictTableOP.save(save_linkPredictTableBean);


        }



    }


    public double getRealLinkLoad() {
        return realLinkLoad;
    }

    public void setRealLinkLoad1(double realLinkLoad) {
        this.realLinkLoad = realLinkLoad;
    }

    private double realLinkLoad =0;

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

    public double getPrediction() {
        return prediction;
    }

    public void setPrediction(double prediction) {
        this.prediction = prediction;
    }

    public double prediction=0;


}



