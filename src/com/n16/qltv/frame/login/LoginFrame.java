package com.n16.qltv.frame.login;

import com.n16.qltv.model.Customer;
import com.n16.qltv.model.Staff;
import com.n16.qltv.patterns.strategy.AdminLoginStrategy;
import com.n16.qltv.patterns.strategy.CustomerLoginStrategy;
import com.n16.qltv.patterns.strategy.StaffLoginStrategy;
import com.n16.qltv.patterns.strategy.interfaces.ILoginStrategy;
import com.n16.qltv.model.Admin;
import com.n16.qltv.utils.Validation;

import javax.swing.*;

public class LoginFrame extends JFrame {
    private ILoginStrategy strategy;
    private JButton btnLogin;
    private JTextField txtUsrName;
    private JLabel labelUsrName, labelPassword;
    private JLabel mainTitle;
    private JPanel loginPanel;
    private JPasswordField txtPassword;
    private JRadioButton rdCustomer;
    private JRadioButton rdStaff;
    private JRadioButton rdAdmin;

    public LoginFrame() {
        setContentPane(loginPanel);
        setResizable(false);
        setBounds(60, 60, 480, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Đăng nhập tài khoản");

        btnLogin.addActionListener(e -> {
            Validation.clearValidation();
            String usrName = txtUsrName.getText().trim();
            String password = txtPassword.getText();
            Validation.loginValidation(usrName, password);
            if(Validation.getErrCount() > 0) {
                JOptionPane.showMessageDialog(null, Validation.getStrValidation());
            }
            else {
                if(rdAdmin.isSelected()) {
                    Admin admin = new Admin(usrName, password);
                    this.strategy = new AdminLoginStrategy(this);
                    this.strategy.execute(admin);
                }
                else if(rdStaff.isSelected()) {
                    Staff staff = new Staff();
                    staff.setUsrName(usrName);
                    staff.setPassword(password);
                    this.strategy = new StaffLoginStrategy(this);
                    this.strategy.execute(staff);
                }
                else {
                    Customer customer = new Customer();
                    customer.setUsrName(usrName);
                    customer.setPassword(password);
                    this.strategy = new CustomerLoginStrategy(this);
                    this.strategy.execute(customer);
                }
            }
        });
    }
}
