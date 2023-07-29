package com.n16.qltv.frame;

import javax.swing.*;

public class StaffAddFrame extends JFrame {
    public JPanel addPanel;
    private JTextField txtName;
    private JRadioButton radioMale;
    private JRadioButton radioFemale;
    private JTextField txtPhone;
    private JTextField txtAddress;
    private JTextField txtUsrName;
    private JPasswordField txtRePassword;
    private JPasswordField txtPassword;
    private JButton btnAdd;
    private JLabel genderLabel;
    private JLabel nameLabel;
    private JLabel usrNameLabel;
    private JLabel passwordLabel;
    private JLabel addressLabel;
    private JLabel phoneLabel;
    private JLabel dobLabel;
    private JLabel titleLabel;
    private JLabel rePasswordLabel;

    public StaffAddFrame() {
        setContentPane(addPanel);
        setTitle("Thêm Nhân viên");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
