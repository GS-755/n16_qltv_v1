package com.n16.qltv.frame.config;

import com.n16.qltv.AppProperties;

import javax.swing.*;
import java.io.IOException;
import java.util.Properties;

public class ConfigFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField txtHostName;
    private JTextField txtUsrName;
    private JPasswordField txtPassword;
    private JTextField txtDbName;
    private JLabel lbConfig;
    private JLabel lbHostName;
    private JLabel lbUsrName;
    private JLabel lbPassword;
    private JLabel lbDbName;
    private JButton btnSave;

    public ConfigFrame() {
        setContentPane(mainPanel);
        setVisible(true);
        setResizable(false);
        setBounds(60, 60, 480, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Cấu hình kết nối CSDL");

        setActionAccept();
    }
    public void setActionAccept() {
        this.btnSave.addActionListener(e -> {
            try {
                Properties properties = new Properties();
                properties.setProperty("HOST_NAME", txtHostName.getText().trim());
                properties.setProperty("USER_NAME", txtUsrName.getText().trim());
                properties.setProperty("PASSWORD", txtPassword.getText());
                properties.setProperty("DB_NAME", txtDbName.getText().trim());

                AppProperties.makeConfig(properties);
                JOptionPane.showMessageDialog(null, "Lưu cấu hình CSDL Thành công!");
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra trong quá trình " +
                        "lưu cấu hình Cơ sở dữ liệu!\nVui lòng khởi động lại phần mềm...");
                System.out.println(ex.getMessage());
            }
        });
    }
}
