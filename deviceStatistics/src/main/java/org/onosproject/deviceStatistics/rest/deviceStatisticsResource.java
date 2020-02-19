package org.onosproject.deviceStatistics.rest;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.onosproject.cluster.ClusterService;
import org.onosproject.cpman.ControlLoadSnapshot;
import org.onosproject.cpman.ControlMetricType;
import org.onosproject.cpman.ControlPlaneMonitorService;
import org.onosproject.net.ConnectPoint;
import org.onosproject.net.Device;
import org.onosproject.net.DeviceId;
import org.onosproject.net.Host;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.host.HostService;
import org.onosproject.net.link.LinkService;
import org.onosproject.net.device.PortStatistics;
import org.onosproject.rest.AbstractWebResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.onosproject.cpman.ControlMetricType.INBOUND_PACKET;

@Path("deviceStatistics")
public class deviceStatisticsResource extends AbstractWebResource {

    private ControlPlaneMonitorService controlPlaneMonitorService = get(ControlPlaneMonitorService.class);
    private HostService hostService = get(HostService.class);
    private DeviceService deviceService = get(DeviceService.class);
    private ClusterService cs = get(ClusterService.class);

    @GET
    @Path("netStatistics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response netStatistics(){
        Iterable<Host> hosts = hostService.getHosts();
        double bytesSent=0;
        double bytesReceived=0;
        double packetsRxDropped=0;
        double packetsRxError=0;
        double packetsReceived=0;
        double T=5;
        //统计与主机相连的端口的数据用于网络指标的计算
        for(Host host : hosts){
            PortStatistics port = deviceService.getDeltaStatisticsForPort(host.location().deviceId(),host.location().port());
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

        Iterable<Device> device = deviceService.getDevices();
        ControlMetricType cmt = INBOUND_PACKET;

        //用于计算packet_in包的数量（inbound_packet即为packet_in）
        long count= 0;
        //调用cpman应用中的服务来获得单个设备发送的packet_in的包个数，再累加获得总个数
        for(Device d:device){
            ControlLoadSnapshot cls = controlPlaneMonitorService.getLoadSync(cs.getLocalNode().id(), cmt,  Optional.of(d.id()));
            count=count+cls.latest();

        }

        ObjectNode root = mapper().createObjectNode();

        root.put("NetBytesReceivedRate", netBytesReceivedRate)
                .put("NetBytesSentRate", netBytesSentRate)
                    .put("PacketsLossRate",packetsLossRate)
                        .put("NumberOfPacketIn",count);



        return ok(root.toString()).build();
    }

}
