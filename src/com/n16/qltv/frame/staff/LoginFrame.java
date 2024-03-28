package com.n16.qltv.frame.staff;
import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.utils.Session;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JPanel loginFrame;
    private JTextField txtUsrName;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JCheckBox checkBoxSavePassword;
    private JLabel usrNameLabel, passwordLabel, titleLabel;

    private DaoFacade daoFacade = new DaoFacade();

    public LoginFrame() throws HeadlessException {
        setContentPane(loginFrame);
        setTitle("Đăng nhập Nhân viên");
        setVisible(true);
        setResizable(false);
        setBounds(60, 70, 480, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnLogin.addActionListener(e -> {
            try {
                String usrName = txtUsrName.getText();
                String password = txtPassword.getText();
                if(daoFacade.staffDAO.checkExistStaff(usrName)) {
                    boolean loginStatus = daoFacade.staffDAO.loginAccount(usrName, password);
                    if(loginStatus) {
                        dispose();
                        Session.put("staff", usrName);
                        IndexFrame indexFrame = new IndexFrame();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Thông tin đăng nhập KHÔNG chính xác");
                    }
                } else if(txtPassword.getText().isEmpty()
                        || txtPassword.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Mật khẩu KHÔNG được để trắng!");
                } else {
                    JOptionPane.showMessageDialog(null, "KHÔNG có người dùng này trên máy chủ!");
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
