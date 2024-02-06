package com.n16.qltv;

import com.n16.qltv.vendor.Session;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public final class AppProperties {
    // Use if you have not any app.properties file
    public static void makeConfig() throws IOException {
        Properties staticProperties = new Properties();
        staticProperties.setProperty("HOST_NAME", getInstance().HOST_NAME);
        staticProperties.setProperty("USER_NAME", getInstance().getUSER_NAME());
        staticProperties.setProperty("PASSWORD", getInstance().getPASSWORD());
        staticProperties.setProperty("DB_NAME", getInstance().DB_NAME);

        staticProperties.store(new FileWriter("app.properties"), null);
    }

    // Database attributes
    private String HOST_NAME;
    private String USER_NAME;
    private String PASSWORD;
    private String DB_NAME;
    // Properties attribute
    private Properties properties;

    // Singleton instange
    private static AppProperties instance = new AppProperties();

    // Use if you got app.properties
    private AppProperties() {
        this.properties = new Properties();
        try {
            this.properties.load(new FileReader("app.properties"));
            this.HOST_NAME = properties.get("HOST_NAME").toString();
            this.USER_NAME = properties.get("HOST_NAME").toString();
            this.PASSWORD = properties.get("PASSWORD").toString();
            this.DB_NAME = properties.get("DB_NAME").toString();

            Session.put("config-enabled", true);
        }
        catch(IOException ex) {
            Session.put("config-enabled", false);
            System.out.println("Không tìm thấy file app.properties!");
            System.out.println("Vui lòng điền các thông tin kết nối CSDL");
        }
    }

    // Username & password Getter
    public String getUSER_NAME() { return this.USER_NAME; }
    public String getPASSWORD() { return this.PASSWORD; }

    // Singleton properties for AppProperties.instance
    public static AppProperties getInstance() {
        return instance;
    }
    private static void setInstance(AppProperties instance) {
        AppProperties.instance = instance;
    }

    public String getConnectionString() {
        return String.format("jdbc:mysql://%s:3306/%s", this.HOST_NAME, this.DB_NAME);
    }
}
