package com.n16.qltv.frame.customer;

import com.n16.qltv.daos.CustomerDAO;
import com.n16.qltv.utils.Session;

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
    private CustomerDAO customerDAO;

    public LoginFrame() throws HeadlessException {
        this.customerDAO = new CustomerDAO();
        setContentPane(loginPanel);
        setTitle("Đăng nhập khách hàng");
        setVisible(true);
        setResizable(false);
        setBounds(60, 70, 480, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btnLogin.addActionListener(e->{
            try{
                String usrName = txtUrsName.getText();
                String password = txtPass.getText();
                if(this.customerDAO.isCustomerExist(usrName)) {
                    boolean loginStatus = this.customerDAO.loginAccount(usrName, password);
                    if(loginStatus) {
                        dispose();
                        Session.put("Customer", usrName);
                        IndexFrame indexFrame = new IndexFrame();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Thông tin đăng nhập KHÔNG chính xác");
                    }
                }
                else if(txtPass.getText().isEmpty()
                        || txtPass.getText().isBlank()) {
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
