package com.n16.qltv.vendor;

import com.n16.qltv.frame.config.ConfigFrame;

import java.io.IOException;
import java.sql.*;

public class MySQL {
    public static AppProperties appProperties = new AppProperties();

    public static Connection getConnection() {
        Connection conn = null;
        try {
            appProperties.loadConfig();
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(appProperties.getConnectionString(),
                    appProperties.getUserName(), appProperties.getPassword());
            System.out.println("Connect successfully!");
        } catch (Exception ex) {
            System.out.println("Connect failure :((");
            ex.printStackTrace();
        }

        return conn;
    }
}