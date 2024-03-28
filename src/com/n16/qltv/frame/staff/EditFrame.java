package com.n16.qltv.frame.staff;

import com.github.lgooddatepicker.components.DatePicker;
import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.model.Staff;
import com.n16.qltv.utils.SHA256;
import com.n16.qltv.utils.Validation;

import javax.swing.*;
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

    //
    private DatePicker selectDate;
    private DaoFacade daoFacade = new DaoFacade();

    public EditFrame(String usrName) {
        setGenderComponents();
        setContentPane(addPanel);
        setTitle("Chỉnh sửa Nhân viên");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 560, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Staff staff = daoFacade.staffDAO.getItem(usrName.trim());
        this.setComponents(staff);

        btnEdit.addActionListener(e -> {
            Validation.clearValidation();
            char gender = 'm';
            if (!(radioMale.isSelected()))
                gender = 'f';
            try {
                if(daoFacade.staffDAO.checkExistStaff(usrName.trim())) {
                    staff.setStaffName(txtName.getText().trim());
                    staff.setGender(gender);
                    staff.setStaffPhone(txtPhone.getText().trim());
                    staff.setStaffAddress(txtAddress.getText().trim());
                    if (this.selectDate.getDate() != null) {
                        staff.setStaffDob(Date.valueOf(selectDate.getDate()));
                    }
                    if (!txtPassword.getText().isBlank()
                            && !txtRePassword.getText().isBlank()) {
                        staff.setPassword(txtPassword.getText());
                        if (!Validation.isStrongPassword(staff.getPassword())) {
                            Validation.createValidation(
                                    "Mật khẩu KHÔNG MẠNH \n (Phải có ký tự hoa, thường, đặc biệt và số)");
                        } else {
                            Validation.createValidation(
                                    "Mật khẩu KHÔNG để trống");
                        }
                        if (!(txtPassword.getText().equals(txtRePassword.getText()))) {
                            Validation.createValidation("Mật khẩu KHÔNG trùng khớp");
                        }
                    }
                    else {
                        staff.setPassword(SHA256.toSHA256(SHA256.getSHA256(staff.getPassword())));
                    }
                    Validation.staffValidation(staff);
                    if(Validation.getErrCount() > 0) {
                        JOptionPane.showMessageDialog(null, Validation.getStrValidation());

                        return;
                    }

                    daoFacade.staffDAO.edit(staff);
                    JOptionPane.showMessageDialog(null, "Cập nhật thông tin thành công.");
                }
                else {
                    JOptionPane.showMessageDialog(null, "KHÔNG có tài khoản trong hệ thống!");
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    }
    public void setComponents(Staff staff) {
        txtName.setText(staff.getStaffName().trim());
        txtAddress.setText(staff.getStaffAddress().trim());
        txtPhone.setText(staff.getStaffPhone().trim());
        txtUsrName.setText(staff.getUsrName().trim());
        txtUsrName.setEditable(false);
        staff.setStaffDob(staff.getStaffDob());
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
