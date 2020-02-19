package org.onosproject.deviceStatistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.onosproject.deviceStatistics.deviceStatisticsTableBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class deviceStatisticsTableOP {


    private static final Logger log = LoggerFactory.getLogger(deviceStatisticsTableOP.class);
    // 查询业务信息
    public static List<deviceStatisticsTableBean> queryAll() {
        String sql = "select * from deviceStatistics;";
        List<deviceStatisticsTableBean> results = new LinkedList<deviceStatisticsTableBean>();
        Connection conn = new sqlConnect().get_Connection();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                deviceStatisticsTableBean deviceStatistics = new deviceStatisticsTableBean();
                deviceStatistics.setTime(rs.getString("time"));
                deviceStatistics.setDeviceId(rs.getString("deviceId"));
                deviceStatistics.setPortId(rs.getString("portId"));
                deviceStatistics.setBytesReceived(rs.getString("bytesReceived"));
                deviceStatistics.setBytesSend(rs.getString("bytesSend"));
                deviceStatistics.setPacketsReceived(rs.getString("packetsReceived"));
                deviceStatistics.setPacketsSend(rs.getString("packetsSend"));
                deviceStatistics.setPacketsRxErrors(rs.getString("packetsRxErrors"));
                deviceStatistics.setPacketsTxErrors(rs.getString("packetsTxErrors"));
                deviceStatistics.setPacketsRxDropped(rs.getString("packetsRxDropped"));
                deviceStatistics.setPacketsTxDropped(rs.getString("packetsTxDropped"));

                results.add(deviceStatistics);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {// 释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }

    // 修改(更新)业务信息

    // 保存(插入)业务信息
    public static int save(deviceStatisticsTableBean save_deviceStatisticsTableBean){
        String sql = "insert into deviceStatistics"+" (time,deviceId,portId,bytesReceived,bytesSend,packetsReceived,packetsSent,packetsRxErrors,packetsTxErrors,packetsRxDropped,packetsTxDropped) values(?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = new sqlConnect().get_Connection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            log.info(save_deviceStatisticsTableBean.getTime());
            ps.setString(1, save_deviceStatisticsTableBean.getTime());
            ps.setString(2, save_deviceStatisticsTableBean.getDeviceId());
            ps.setString(3, save_deviceStatisticsTableBean.getPortId());
            ps.setString(4, save_deviceStatisticsTableBean.getBytesReceived());
            ps.setString(5, save_deviceStatisticsTableBean.getBytesSend());
            ps.setString(6, save_deviceStatisticsTableBean.getPacketsReceived());
            ps.setString(7, save_deviceStatisticsTableBean.getPacketsSend());
            ps.setString(8, save_deviceStatisticsTableBean.getPacketsRxErrors());
            ps.setString(9, save_deviceStatisticsTableBean.getPacketsRxErrors());
            ps.setString(10,save_deviceStatisticsTableBean.getPacketsRxDropped());
            ps.setString(11,save_deviceStatisticsTableBean.getPacketsTxDropped());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return -1;
    }

    // 删除业务信息(根据业务ID)
    /*public static int clear() {

        String sql = "delete from deviceStatistics";
        Connection conn = new sqlConnect().get_Connection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
*/
}
