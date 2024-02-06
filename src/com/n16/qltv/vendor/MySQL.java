package com.n16.qltv.vendor;

import com.n16.qltv.AppProperties;

import java.sql.*;

public class MySQL {
    private static final String USER_NAME = AppProperties.
            getInstance().getUSER_NAME();
    private static final String PASSWORD = AppProperties.
            getInstance().getPASSWORD();
    private static final String dbUrl = AppProperties.
            getInstance().getConnectionString();

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, USER_NAME, PASSWORD);
            System.out.println("Connect successfully!");
        } catch (Exception ex) {
            System.out.println("Connect failure :((");
            ex.printStackTrace();
        }

        return conn;
    }
}