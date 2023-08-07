package com.n16.qltv.frame.customer;

import com.n16.qltv.model.Customer;

import javax.swing.*;

public class CreateFrame extends JFrame {
    ButtonGroup buttonGroup;
    private JTextField txtNameCus;
    private JRadioButton radioMale;
    private JRadioButton radioFemale;
    private JTextField txtPhoneCus;
    private JTextField txtAddressCus;
    private JTextField txtUsrName;
    private JPasswordField txtPassword;
    private JButton btnAdd;
    private JLabel mainTitle;
    private JLabel labelNameCus;
    private JLabel labelGender;
    private JLabel labelPhone;
    private JLabel labelAddress;
    private JLabel labelUsrName;
    private JLabel labelPasswordCus;
    private JPasswordField txtRePassword;
    private JLabel labelRePassword;
    private JPanel createFrame;

    public CreateFrame() {
        setContentPane(createFrame);
        setVisible(true);
        setResizable(false);
        setBounds(60, 60, 480, 320);
        setTitle("Thêm khách hàng");
        setComponents();
        btnAdd.addActionListener(e -> {

        });
    }
    public void setComponents() {
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioMale);
        buttonGroup.add(radioFemale);
        radioMale.setSelected(true);
    }
}
