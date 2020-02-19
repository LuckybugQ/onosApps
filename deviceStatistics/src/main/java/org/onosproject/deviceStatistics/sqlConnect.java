package org.onosproject.deviceStatistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.cj.jdbc.Driver;

public class sqlConnect {
    protected  final Logger log = LoggerFactory.getLogger(sqlConnect.class);
    public Connection get_Connection() {
        String url = "jdbc:mysql://192.168.8.99:3306/Statistics?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "123456";
        Connection conn = null;
        try {
	        Driver dd = new Driver();
            //Class.forName("com.mysql.jdbc.Driver"); 加载数据库驱动j
            conn = DriverManager.getConnection(url,user,password);// 获得数据库连接
            return conn;
        }
        catch (SQLException e) {
            log.info("Connection to MySQL Error");
            e.printStackTrace();
        }
        return null;
    }
}
