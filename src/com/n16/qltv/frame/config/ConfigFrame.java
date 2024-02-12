package com.n16.qltv.frame.config;

import com.n16.qltv.vendor.AppProperties;

import javax.swing.*;

public class ConfigFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField txtHostName;
    private JTextField txtUsrName;
    private JPasswordField txtPassword;
    private JTextField txtPort;
    private JTextField txtDbName;
    private JButton btnSave;

    public ConfigFrame() {
        setContentPane(mainPanel);
        setVisible(true);
        setResizable(false);
        setBounds(70, 60, 480, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cấu hình Cơ sở dữ liệu");

        setActionSave();
    }
    protected void setActionSave() {
        this.btnSave.addActionListener(e -> {
            try {
                AppProperties appProperties = new AppProperties();
                appProperties.setHostName(this.txtHostName.getText());
                appProperties.setDbName(this.txtDbName.getText());
                appProperties.setUserName(this.txtUsrName.getText());
                appProperties.setPassword(this.txtPassword.getText());
                appProperties.setPort(this.txtPort.getText());

                if(AppProperties.makeConfig(appProperties)) {
                    JOptionPane.showMessageDialog(null, "Đã tạo cấu hình CSDL!" +
                            "\nKhởi động lại chương trình để sử dụng.");
                    System.exit(JFrame.EXIT_ON_CLOSE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Có lỗi xảy ra trong " +
                            "quá trình tạo cấu hình CSDL!");
                }
            }
            catch(Exception ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}
