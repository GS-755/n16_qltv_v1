package com.n16.qltv.patterns.singleton;

import com.n16.qltv.utils.AppProperties;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {
    private static MySQL client = new MySQL();
    private Connection conn;
    private AppProperties properties;

    private MySQL() {
        try {
            Thread.sleep(300);
            this.properties = new AppProperties();
            this.properties.loadConfig();
            this.conn = this.makeConnection();
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private Connection makeConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(properties.getConnectionString(),
                    properties.getUserName(), properties.getPassword());
            System.out.println("Connect successfully!");
        } catch (Exception ex) {
            System.out.println("Connect failure :((");
            ex.printStackTrace();
        }

        return conn;
    }
    public Connection getConnection() { return this.conn; }

    public static synchronized MySQL client() {
        if(MySQL.client == null) {
            MySQL.client = new MySQL();
        }

        return MySQL.client;
    }
    private static void setClient(MySQL client) {
        MySQL.client = client;
    }
}