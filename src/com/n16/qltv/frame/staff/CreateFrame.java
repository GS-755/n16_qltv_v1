package com.n16.qltv.frame.staff;

import com.n16.qltv.adaptor.StaffAdapter;
import com.n16.qltv.adaptor.Validation;
import com.n16.qltv.model.Staff;
import javax.swing.*;

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
            char gender = 'm';
            if (!(radioMale.isSelected()))
                gender = 'f';
            if(!(txtPassword.getText().equals(txtRePassword.getText()))) {
                JOptionPane.showMessageDialog(null, "Mật khẩu không trùng khớp!");
            }
            else if(txtPassword.getText().isEmpty() || txtRePassword.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Mật khẩu KHÔNG để trống.");
            }
            else {
                if(!(StaffAdapter.checkExistStaff(txtUsrName.getText()))) {
                    Validation.clearValidation();

                    Staff staff = new Staff();
                    staff.setStaffName(txtName.getText());
                    staff.setGender(gender);
                    staff.setStaffPhone(txtPhone.getText());
                    staff.setStaffAddress(txtAddress.getText());
                    staff.setStaffDob("2000-1-1");
                    staff.setUsrName(txtUsrName.getText());
                    staff.setPassword(txtPassword.getText());

                    Validation.staffValidation(staff);
                    if(Validation.getErrCount() != 0)
                        JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                    else
                        StaffAdapter.addStaff(staff);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Đã có tài khoản trong hệ thống!");
                }
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
