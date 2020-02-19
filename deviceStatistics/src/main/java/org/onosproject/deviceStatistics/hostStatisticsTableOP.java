package org.onosproject.deviceStatistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class hostStatisticsTableOP {

        private static final Logger log = LoggerFactory.getLogger(hostStatisticsTableOP.class);
        // 查询业务信息
        public static List<hostStatisticsTableBean> queryAll() {
            String sql = "select * from hostStatistics;";
            List<hostStatisticsTableBean> results = new LinkedList<hostStatisticsTableBean>();
            Connection conn = new sqlConnect().get_Connection();
            Statement stat = null;
            ResultSet rs = null;
            try {
                stat = conn.createStatement();
                rs = stat.executeQuery(sql);
                while (rs.next()) {
                    hostStatisticsTableBean hostStatistics = new hostStatisticsTableBean();
                    hostStatistics.setTime(rs.getString("time"));
                    hostStatistics.setHostId(rs.getString("hostId"));
                    hostStatistics.setIpAddress(rs.getString("ipAddress"));
                    hostStatistics.setConnectDeviceId(rs.getString("connectDeviceId"));
                    hostStatistics.setConnectDevicePort(rs.getString("connectDevicePort"));

                    results.add(hostStatistics);
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
        public static int save(hostStatisticsTableBean save_hostStatisticsTableBean){
            String sql = "insert into hostStatistics"+" (time,hostId,ipAddress,connectDeviceId,connectDevicePort) values(?,?,?,?,?)";
            Connection conn = new sqlConnect().get_Connection();
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, save_hostStatisticsTableBean.getTime());
                ps.setString(2, save_hostStatisticsTableBean.getHostId());
                ps.setString(3, save_hostStatisticsTableBean.getIpAddress());
                ps.setString(4, save_hostStatisticsTableBean.getConnectDeviceId());
                ps.setString(5, save_hostStatisticsTableBean.getConnectDevicePort());
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
}
