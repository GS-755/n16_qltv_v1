package com.n16.qltv.frame.staff;

import com.n16.qltv.adaptor.StaffAdapter;
import com.n16.qltv.adaptor.Validation;
import com.n16.qltv.model.Staff;
import com.n16.qltv.vendor.SHA256;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;

public class CreateFrame extends JFrame {
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

    public CreateFrame() {
        setGenderComponents();
        setContentPane(addPanel);
        setTitle("Thêm Nhân viên");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btnAdd.addActionListener(e -> {
            Validation.clearValidation();
            char gender = 'm';
            if (!(radioMale.isSelected()))
                gender = 'f';
            if(!(StaffAdapter.checkExistStaff(txtUsrName.getText()))) {
                if(!(txtPassword.getText().equals(txtRePassword.getText()))) {
                    Validation.createValidation("Mật khẩu KHÔNG trùng khớp");
                }
                Staff staff = new Staff();
                try {
                    staff.setStaffName(txtName.getText());
                    staff.setGender(gender);
                    staff.setStaffPhone(txtPhone.getText());
                    staff.setStaffAddress(txtAddress.getText());
                    staff.setStaffDob("2000-1-1");
                    staff.setUsrName(txtUsrName.getText());
                    staff.setPassword(txtPassword.getText());
                    if(Validation.isStrongPassword(staff.getPassword())) {
                        String authTmp = SHA256.toSHA256(SHA256.
                                getSHA256(txtPassword.getText()));
                        staff.setPassword(authTmp);
                    }
                    else {
                        Validation.createValidation(
                                "Mật khẩu KHÔNG MẠNH \n (Phải có ký tự hoa, thường, đặc biệt và số)");
                    }
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }

                Validation.staffValidation(staff);
                if(Validation.getErrCount() != 0) {
                    JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                }
                else {
                    StaffAdapter.addStaff(staff);
                    JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công!");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Đã có tài khoản trong hệ thống!");
            }
        });
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
