package com.n16.qltv;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public final class AppProperties {
    // Use if you have not any app.properties file
    public static void makeConfig(Properties paramsProperties) throws IOException {
        Properties staticProperties = paramsProperties;
        staticProperties.store(new FileWriter("app.properties"), null);
    }

    // Database attributes
    private String hostName;
    private String userName;
    private String password;
    private String dbName;
    // Properties attribute
    private Properties properties;

    public AppProperties() {
        this.properties = new Properties();
        try {
            this.properties.load(new FileReader("app.properties"));
            this.hostName = properties.get("HOST_NAME").toString();
            this.userName = properties.get("USER_NAME").toString();
            this.password = properties.get("PASSWORD").toString();
            this.dbName = properties.get("DB_NAME").toString();
        }
        catch(IOException ex) {
            System.out.println("Không tìm thấy tập tin app.properties!\n" +
                    "Vui lòng điền các thông tin kết nối CSDL");
        }
    }

    // UserName & Password Getter
    public String getUserName() { return this.userName; }
    public String getPassword() { return this.password; }
    // Properties Setter
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getConnectionString() {
        return String.format("jdbc:mysql://%s:3306/%s", this.hostName, this.dbName);
    }
}
