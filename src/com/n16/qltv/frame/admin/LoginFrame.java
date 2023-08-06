package com.n16.qltv.frame.admin;

import com.n16.qltv.adaptor.AdminAdapter;
import com.n16.qltv.frame.staff.IndexFrame;

import javax.swing.*;

public class LoginFrame extends JFrame {
    private JButton btnLogin;
    private JTextField txtUsrName;
    private JLabel labelUsrName, labelPassword;
    private JLabel mainTitle;
    private JPanel loginPanel;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setContentPane(loginPanel);
        setVisible(true);
        setResizable(false);
        setBounds(60, 60, 480, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Đăng nhập Admin");

        btnLogin.addActionListener(e -> {
            String usrName = txtUsrName.getText().trim();
            String password = txtPassword.getText();
            boolean isLoggedIn = AdminAdapter.
                    isLoggedIn(usrName, password);
            if(isLoggedIn) {
                dispose();
                IndexFrame indexFrame = new IndexFrame();
            }
            else {
                JOptionPane.showMessageDialog(null, "SAI thông tin đăng nhập.");
            }
        });
    }
}
