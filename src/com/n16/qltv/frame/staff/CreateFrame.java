package com.n16.qltv.frame.staff;

import com.github.lgooddatepicker.components.DatePicker;
import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.utils.Validation;
import com.n16.qltv.model.Staff;
import com.n16.qltv.utils.SHA256;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

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
    private DatePicker datePicker;
    private DaoFacade daoFacade = new DaoFacade();

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
            if(!(daoFacade.staffDAO.checkExistStaff(txtUsrName.getText()))) {
                try {
                    if(!(txtPassword.getText().equals(txtRePassword.getText()))) {
                        Validation.createValidation("Mật khẩu KHÔNG trùng khớp");
                    }
                    Staff staff = new Staff();
                    try {
                        staff.setStaffName(txtName.getText().trim());
                        staff.setGender(gender);
                        staff.setStaffPhone(txtPhone.getText().trim());
                        staff.setStaffAddress(txtAddress.getText().trim());
                        staff.setStaffDob(Date.valueOf(datePicker.getDate()));
                        staff.setUsrName(txtUsrName.getText().trim());
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
                    if(Validation.getErrCount() > 0) {
                        JOptionPane.showMessageDialog(null, Validation.getStrValidation());
                    }
                    else {
                        daoFacade.staffDAO.create(staff);
                        JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công!");
                        dispose();
                    }
                }
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
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
