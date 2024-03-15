package com.n16.qltv.frame.staff;

import com.github.lgooddatepicker.components.DatePicker;
import com.n16.qltv.adaptor.StaffAdapter;
import com.n16.qltv.adaptor.Validation;
import com.n16.qltv.model.Staff;
import com.n16.qltv.vendor.SHA256;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

public class EditFrame extends JFrame {
    public JPanel addPanel;
    private JTextField txtName, txtPhone, txtAddress, txtUsrName;
    private ButtonGroup genderRadioGroup;
    private JRadioButton radioMale, radioFemale;
    private JPasswordField txtPassword, txtRePassword;
    private JButton btnEdit;
    private JLabel nameLabel, genderLabel;
    private JLabel usrNameLabel, passwordLabel, rePasswordLabel;
    private JLabel addressLabel, phoneLabel, dobLabel;
    private JLabel titleLabel;
    private DatePicker selectDate;

    public EditFrame(String usrName) {
        setGenderComponents();
        setContentPane(addPanel);
        setTitle("Chỉnh sửa Nhân viên");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setComponents(usrName);

        btnEdit.addActionListener(e -> {
            Validation.clearValidation();

            char gender = 'm';
            if (!(radioMale.isSelected()))
                gender = 'f';

            if(StaffAdapter.checkExistStaff(usrName.trim())) {
                Staff staff = new Staff();
                if(txtPassword.getText().isBlank()
                        && txtRePassword.getText().isEmpty()) {
                    staff.setStaffName(txtName.getText());
                    staff.setGender(gender);
                    staff.setStaffDob(Date.valueOf(selectDate.getDate()));
                    staff.setStaffAddress(txtAddress.getText());
                    staff.setStaffPhone(txtPhone.getText());
                    staff.setUsrName(usrName.trim());
                    staff.setPassword(StaffAdapter.getPassword(usrName.trim()));

                    Validation.staffValidation(staff);
                    if(Validation.getErrCount() > 0) {
                        JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                    }
                    else {
                        StaffAdapter.editStaff(staff);
                        JOptionPane.showMessageDialog(null, "Cập nhật thông tin thành công.");
                    }
                }
                else {
                    try {
                        if(!(txtPassword.getText().equals(txtRePassword.getText()))) {
                            Validation.createValidation("Mật khẩu KHÔNG trùng khớp");
                        }
                        else {
                            staff.setStaffName(txtName.getText());
                            staff.setGender(gender);
                            staff.setStaffPhone(txtPhone.getText());
                            staff.setStaffAddress(txtAddress.getText());
                            staff.setStaffDob(Date.valueOf(selectDate.getDate()));
                            staff.setUsrName(usrName.trim());
                            staff.setPassword(txtPassword.getText());
                            if(Validation.isStrongPassword(staff.getPassword())) {
                                String authTmp = SHA256.toSHA256(SHA256.
                                        getSHA256(staff.getPassword()));
                                staff.setPassword(authTmp);
                            }
                            else {
                                Validation.createValidation(
                                        "Mật khẩu KHÔNG MẠNH " +
                                                "\n (Phải có ký tự hoa, thường, đặc biệt và số)");
                            }

                            Validation.staffValidation(staff);
                            if(Validation.getErrCount() > 0) {
                                JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                            }
                            else {
                                StaffAdapter.editStaff(staff);
                                JOptionPane.showMessageDialog(
                                        null, "Cập nhật thông tin thành công.");
                                dispose();
                            }
                        }
                    } catch (NoSuchAlgorithmException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "KHÔNG có tài khoản trong hệ thống!");
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
