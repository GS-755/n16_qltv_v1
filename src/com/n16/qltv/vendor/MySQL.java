package com.n16.qltv.vendor;

import com.n16.qltv.AppProperties;

import javax.swing.*;
import java.io.File;
import java.sql.*;

public final class MySQL {
    private Connection conn;
    private static MySQL instance = new MySQL();

    private MySQL() {
        try {
            AppProperties appProperties = new AppProperties();
            // Init MySQL connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    appProperties.getConnectionString(),
                    appProperties.getUserName(),
                    appProperties.getPassword()
            );
            System.out.println("Connect successfully!");
        } catch (Exception ex) {
            File file = new File("app.properties");
            if(file.exists()) {
                JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi kết nối!" +
                        "\nVui lòng kiểm tra cấu hình network");
                ex.printStackTrace();

                return;
            }
            JOptionPane.showMessageDialog(null, "Không tìm thấy tập tin app.properties!\n" +
                    "Vui lòng điền các thông tin kết nối CSDL");
        }
    }

    public static MySQL getInstance() { return instance; }
    private static void setInstance(MySQL instance) {
        MySQL.instance = instance;
    }

    public Connection getConnection() { return this.conn; }
    public void closeConnection() throws SQLException { this.getConnection().close(); }
}