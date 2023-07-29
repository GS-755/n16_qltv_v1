package com.n16.qltv.vendor;

import com.n16.qltv.model.Staff;

import java.sql.*;
import java.util.Scanner;
import java.sql.Date;

public class MySQL {
    private static Scanner scanner = new Scanner(System.in);
    private static final String HOST_NAME = "localhost";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";
    private static final String DB_NAME = "n16_qltv";
    private static final String dbUrl = String.format("jdbc:mysql://%s:3306/%s", HOST_NAME, DB_NAME);


    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, USER_NAME, PASSWORD);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }

        return conn;
    }
}