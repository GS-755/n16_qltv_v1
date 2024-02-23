package com.n16.qltv.frame.customer;

import com.n16.qltv.adaptor.CustomerAdapter;
import com.n16.qltv.adaptor.StaffAdapter;
import com.n16.qltv.frame.staff.IndexFrame;
import com.n16.qltv.vendor.Session;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame{
    private JTextField txtPass;
    private JCheckBox rememberCheckbox;
    private JButton btnLogin;
    private JTextField txtUrsName;
    private JLabel usrnameLabel;
    private JLabel passwordLabel;
    private JLabel titleLabel;
    private JPanel loginPanel;

    public LoginFrame() throws HeadlessException {
        setContentPane(loginPanel);
        setTitle("Đăng nhập khách hàng");
        setVisible(true);
        setResizable(false);
        setBounds(60, 70, 560, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btnLogin.addActionListener(e->{
            try{
                String usrName = txtUrsName.getText();
                String password = txtPass.getText();

                if(CustomerAdapter.checkExistCustomer(usrName)) {
                    boolean loginStatus = CustomerAdapter.loginAccount(usrName, password);
                    if(loginStatus) {
                        dispose();
                        Session.put("Customer", usrName);
                        com.n16.qltv.frame.customer.IndexFrame indexFrame = new com.n16.qltv.frame.customer.IndexFrame();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Thông tin đăng nhập KHÔNG chính xác");
                    }
                }
                else if(txtPass.getText().isEmpty()|| txtPass.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Mật khẩu KHÔNG được để trắng!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "KHÔNG có người dùng này trên máy chủ!");
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });

    }
}
