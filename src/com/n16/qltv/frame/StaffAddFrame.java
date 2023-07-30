package com.n16.qltv.frame;

import com.n16.qltv.adaptor.StaffAdapter;
import com.n16.qltv.model.Staff;
import javax.swing.*;

public class StaffAddFrame extends JFrame {
    public JPanel addPanel;
    private JTextField txtName, txtPhone, txtAddress, txtUsrName;
    private JRadioButton radioMale, radioFemale;
    private JPasswordField txtPassword, txtRePassword;
    private JButton btnAdd;
    private JLabel nameLabel, genderLabel;
    private JLabel usrNameLabel, passwordLabel, rePasswordLabel;
    private JLabel addressLabel, phoneLabel, dobLabel;
    private JLabel titleLabel;

    public StaffAddFrame() {
        setContentPane(addPanel);
        setTitle("Thêm Nhân viên");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnAdd.addActionListener(e -> {
            char gender = 'm';
            if (!(radioMale.isSelected()))
                gender = 'f';
            if(!(txtPassword.getText().equals(txtRePassword.getText()))) {
                JOptionPane.showMessageDialog(null, "Mật khẩu không trùng khớp!");
            }
            else {
                if(!(StaffAdapter.checkExistStaff(txtUsrName.getText()))) {
                    StaffAdapter.addStaff(new Staff(txtName.getText(), gender, txtPhone.getText(),
                            txtAddress.getText(), "2000-1-1", txtUsrName.getText(), txtPassword.getText()));
                }
                else {
                    JOptionPane.showMessageDialog(null, "Đã có tài khoản trong hệ thống!");
                }
            }
        });
    }
}
