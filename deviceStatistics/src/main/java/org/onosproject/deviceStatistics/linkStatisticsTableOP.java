package org.onosproject.deviceStatistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class linkStatisticsTableOP {
    private static final Logger log = LoggerFactory.getLogger(linkStatisticsTableOP.class);

    // 查询业务信息
    public static List<linkStatisticsTableBean> queryAll() {
        String sql = "select * from linkStatistics;";
        List<linkStatisticsTableBean> results = new LinkedList<linkStatisticsTableBean>();
        Connection conn = new sqlConnect().get_Connection();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                linkStatisticsTableBean linkStatistics = new linkStatisticsTableBean();
                linkStatistics.setTime(rs.getString("time"));
                linkStatistics.setLinkId(rs.getString("linkId"));
                linkStatistics.setSrcDevice(rs.getString("srcDevice"));
                linkStatistics.setSrcPort(rs.getString("srcPort"));
                linkStatistics.setDstDevice(rs.getString("dstDevice"));
                linkStatistics.setDstPort(rs.getString("dstPort"));
                linkStatistics.setLinkLoad(rs.getString("linkLoad"));

                results.add(linkStatistics);
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
    public static int save(linkStatisticsTableBean save_linkStatisticsTableBean){
        String sql = "insert into linkStatistics"+" (time,linkId,srcDevice,srcPort,dstDevice,dstPort,linkLoad) values(?,?,?,?,?,?,?)";
        Connection conn = new sqlConnect().get_Connection();
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(sql);
            ps.setString(1, save_linkStatisticsTableBean.getTime());
            ps.setString(2, save_linkStatisticsTableBean.getLinkId());
            ps.setString(3, save_linkStatisticsTableBean.getSrcDevice());
            ps.setString(4, save_linkStatisticsTableBean.getSrcPort());
            ps.setString(5, save_linkStatisticsTableBean.getDstDevice());
            ps.setString(6, save_linkStatisticsTableBean.getDstPort());
            ps.setString(7, save_linkStatisticsTableBean.getLinkLoad());

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
