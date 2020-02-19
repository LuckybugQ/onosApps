package org.onosproject.deviceStatistics;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class packetInStatisticsTableOP {
    public static List<packetInStatisticsTableBean> queryAll() {
        String sql = "select * from packetInStatistics;";
        List<packetInStatisticsTableBean> results = new LinkedList<packetInStatisticsTableBean>();
        Connection conn = new sqlConnect().get_Connection();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                packetInStatisticsTableBean packetInStatistics = new packetInStatisticsTableBean();
                packetInStatistics.setTime(rs.getString("time"));
                packetInStatistics.setNumOfPacketIn(rs.getString("numOfPacketIn"));

                results.add(packetInStatistics);
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
    public static int save(packetInStatisticsTableBean save_packetInStatisticsTableBean){
        String sql = "insert into packetInStatistics"+" (time,numOfPacketIn) values(?,?)";
        Connection conn = new sqlConnect().get_Connection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, save_packetInStatisticsTableBean.getTime());
            ps.setString(2, save_packetInStatisticsTableBean.getNumOfPacketIn());
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
