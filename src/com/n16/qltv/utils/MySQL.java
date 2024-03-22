package com.n16.qltv.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {
    private Connection conn;
    private static MySQL client = new MySQL();
    private AppProperties properties;

    private MySQL() {
        try {
            Thread.sleep(1200);
            this.properties = new AppProperties();
            this.properties.loadConfig();
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(properties.getConnectionString(),
                    properties.getUserName(), properties.getPassword());
            System.out.println("Connect successfully!");
        } catch (Exception ex) {
            System.out.println("Connect failure :((");
            ex.printStackTrace();
        }

        return conn;
    }
    public synchronized void closeConnection() {
        if (this.conn != null) {
            try {
                this.conn.close();
                this.conn = null;
                System.out.println("Connection closed.");
            }
            catch (Exception ex) {
                System.out.println("Error while closing connection.");
                ex.printStackTrace();
            }
        }
    }
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
