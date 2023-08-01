package com.n16.qltv.frame.staff;

import com.n16.qltv.adaptor.StaffAdapter;
import com.n16.qltv.model.Staff;
import com.n16.qltv.vendor.SHA256;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;

public class EditFrame extends JFrame {
    public JPanel addPanel;
    private JTextField txtName, txtPhone, txtAddress, txtUsrName;
    private ButtonGroup genderRadioGroup;
    private JRadioButton radioMale, radioFemale;
    private JPasswordField txtPassword, txtRePassword;
    private JButton btnAdd;
    private JLabel nameLabel, genderLabel;
    private JLabel usrNameLabel, passwordLabel, rePasswordLabel;
    private JLabel addressLabel, phoneLabel, dobLabel;
    private JLabel titleLabel;

    public EditFrame(String usrName) {
        setGenderComponents();
        setContentPane(addPanel);
        setTitle("Chỉnh sửa Nhân viên");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setComponents(usrName);

        btnAdd.addActionListener(e -> {
            char gender = 'm';
            if (!(radioMale.isSelected()))
                gender = 'f';
            else {
                if(StaffAdapter.checkExistStaff(txtUsrName.getText())) {
                    if(txtPassword.getText().isEmpty() || txtRePassword.getText().isEmpty()) {
                        Staff staff = new Staff(txtName.getText(), gender, txtPhone.getText(), txtAddress.getText(),
                                "2000-1-1", txtUsrName.getText(), StaffAdapter.getPassword(txtUsrName.getText()));
                        StaffAdapter.editStaff(staff);
                    }
                    else {
                        try {
                            if(!(txtPassword.getText().equals(txtRePassword.getText()))) {
                                JOptionPane.showMessageDialog(null, "Mật khẩu không trùng khớp!");
                            } else {
                                Staff staff = new Staff(txtName.getText(), gender, txtPhone.getText(),
                                        txtAddress.getText(), "2000-1-1", txtUsrName.getText(),
                                        SHA256.toSHA256(SHA256.getSHA256(txtPassword.getText())));
                                StaffAdapter.editStaff(staff);
                                System.out.println(staff.toString());
                            }
                        } catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }
                else {
                    JOptionPane.showMessageDialog(null, "KHÔNG có tài khoản trong hệ thống!");
                }
            }
        });
    }
    public void setComponents(String usrName) {
        txtName.setText(StaffAdapter.getStaffName(usrName));
        txtAddress.setText(StaffAdapter.getStaffAddress(usrName));
        txtPhone.setText(StaffAdapter.getStaffPhone(usrName));
        txtUsrName.setText(usrName);
        txtUsrName.setEditable(false);
    }
    public void setGenderComponents() {
        genderRadioGroup = new ButtonGroup();
        genderRadioGroup.add(radioMale);
        genderRadioGroup.add(radioFemale);

        radioMale.addActionListener(e -> {
            genderRadioGroup.clearSelection();
            radioMale.setSelected(true);
        });
        radioFemale.addActionListener(e -> {
            genderRadioGroup.clearSelection();
            radioFemale.setSelected(true);
        });
    }
}
