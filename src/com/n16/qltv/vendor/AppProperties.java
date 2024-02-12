package com.n16.qltv.vendor;

import com.n16.qltv.frame.config.ConfigFrame;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {
    // Static function to make app.properties
    public static boolean makeConfig(AppProperties appProperties) {
        try {
            Properties properties = new Properties();
            properties.setProperty("HOST_NAME", appProperties.getHostName().trim());
            properties.setProperty("USER_NAME", appProperties.getUserName().trim());
            properties.setProperty("PASSWORD", appProperties.getPassword());
            properties.setProperty("DB_NAME", appProperties.getDbName().trim());
            properties.setProperty("PORT", appProperties.getPort().trim());
            properties.store(new FileWriter("app.properties"), null);
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();

            return false;
        }

        return true;
    }

    // Database attributes
    private String hostName;
    private String userName;
    private String password;
    private String dbName;
    private String port;
    // Properties attribute
    private Properties properties;

    // Reads data from the generated app.properties file
    public AppProperties() {
        this.properties = new Properties();
    }

    // UserName & Password Getter
    public String getUserName() { return this.userName; }
    public String getPassword() { return this.password; }
    // Other attributes getter
    public String getHostName() { return this.hostName; }
    public String getDbName() { return this.dbName; }
    public String getPort() { return this.port; }

    //UserName && Password Setter
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    // Other attributes Setter
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    public void setPort(String port) {
        this.port = port;
    }

    // Connection String function
    public String getConnectionString() {
        return String.format("jdbc:mysql://%s:%s/%s", this.hostName, this.port, this.dbName);
    }
    // Method to load app.properties
    public void loadConfig() throws IOException {
        // Load app.properties file
        this.properties.load(new FileReader("app.properties"));
        // Read app.properties data
        this.hostName = properties.get("HOST_NAME").toString();
        this.userName = properties.get("USER_NAME").toString();
        this.password = properties.get("PASSWORD").toString();
        this.dbName = properties.get("DB_NAME").toString();
        this.port = properties.get("PORT").toString();
    }
}
