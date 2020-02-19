package org.onosproject.deviceStatistics;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class netStatisticsTableOP {
    // 查询业务信息
    public static List<netStatisticsTableBean> queryAll() {
        String sql = "select * from netStatistics;";
        List<netStatisticsTableBean> results = new LinkedList<netStatisticsTableBean>();
        Connection conn = new sqlConnect().get_Connection();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                netStatisticsTableBean netStatistics = new netStatisticsTableBean();
                netStatistics.setTime(rs.getString("time"));
                netStatistics.setNetBytesSentRate(rs.getString("netBytesSentRate"));
                netStatistics.setNetBytesReceivedRate(rs.getNString("netBytesReceivedRate"));
                netStatistics.setNetPacketsLossRate(rs.getString("netPacketsLossRate"));
                results.add(netStatistics);
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
    public static int save(netStatisticsTableBean save_netStatisticsTableBean){
        String sql = "insert into netStatistics"+" (time,netBytesSentRate,netBytesReceivedRate,netPacketsLossRate) values(?,?,?,?)";
        Connection conn = new sqlConnect().get_Connection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, save_netStatisticsTableBean.getTime());
            ps.setString(2, save_netStatisticsTableBean.getNetBytesSentRate());
            ps.setString(3, save_netStatisticsTableBean.getNetBytesReceivedRate());
            ps.setString(4, save_netStatisticsTableBean.getNetPacketsLossRate());

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
