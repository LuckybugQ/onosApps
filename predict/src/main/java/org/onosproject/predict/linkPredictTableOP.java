package org.onosproject.predict;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class linkPredictTableOP {
    // 查询业务信息
    public static List<linkPredictTableBean> queryAll() {
        String sql = "select * from linkPredict;";
        List<linkPredictTableBean> results = new LinkedList<linkPredictTableBean>();
        Connection conn = new sqlConnect().get_Connection();
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                linkPredictTableBean linkStatistics = new linkPredictTableBean();
                linkStatistics.setTime(rs.getString("time"));
                linkStatistics.setLinkId(rs.getString("linkId"));
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
    public static int save(linkPredictTableBean save_linkPredictTableBean){
        String sql = "insert into linkPredict"+" (time,linkId,linkLoad) values(?,?,?)";
        Connection conn = new sqlConnect().get_Connection();
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(sql);
            ps.setString(1, save_linkPredictTableBean.getTime());
            ps.setString(2, save_linkPredictTableBean.getLinkId());
            ps.setString(3, save_linkPredictTableBean.getLinkLoad());

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
