package com.n16.qltv.frame.admin;

import com.n16.qltv.adaptor.AdminAdapter;
import com.n16.qltv.adaptor.Validation;
import com.n16.qltv.vendor.Session;

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
            Validation.clearValidation();
            String usrName = txtUsrName.getText().trim();
            String password = txtPassword.getText();
            Validation.loginValidation(usrName, password);
            if(Validation.getErrCount() > 0) {
                JOptionPane.showMessageDialog(null, Validation.getStrValidation());
            }
            else {
                boolean isLoggedIn = AdminAdapter.
                        isLoggedIn(usrName, password);
                if(isLoggedIn) {
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công");
                    dispose();
                    IndexFrame indexFrame = new IndexFrame();
                }
                else {
                    JOptionPane.showMessageDialog(null, "SAI thông tin đăng nhập.");
                }
            }
        });
    }
}
