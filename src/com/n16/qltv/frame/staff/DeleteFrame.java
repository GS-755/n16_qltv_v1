package com.n16.qltv.frame.staff;

import com.n16.qltv.daos.StaffDAO;
import com.n16.qltv.model.Staff;

import javax.swing.*;
import java.util.ArrayList;

public class DeleteFrame extends JFrame {
    private JLabel labelStaffName, labelGender, labelPhoneNum, labelDob, labelAddress;
    private JLabel labelUsrName;
    private JLabel addressTitle, genderTitle,  staffNameTitle, usrNameTitle;
    private JLabel dobTitle, phoneNumTitle;
    private JLabel mainTitle, questionTitle;
    private JPanel deletePanel;
    private JButton btnDelete, btnCancel;
    private StaffDAO staffDAO;

    public DeleteFrame(String usrName) {
        this.staffDAO = new StaffDAO();
        setContentPane(deletePanel);
        setTitle("Xoá Nhân viên");
        setVisible(true);
        setBounds(80, 90, 480, 320);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Staff staff = this.staffDAO.getItem(usrName.trim());
        setComponents(staff);
        btnDelete.addActionListener(e -> {
            if(this.staffDAO.checkExistStaff(usrName.trim())) {
                this.staffDAO.delete(usrName);
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(
                        null, "Lỗi tham số :((( \n Vui lòng kiểm tra lại");
            }
        });
        btnCancel.addActionListener(e -> {
            dispose();
        });
    }
    public void setComponents(Staff staff) {
        labelStaffName.setText(staff.getStaffName());
        labelGender.setText(staff.getStrGender());
        labelDob.setText(staff.getStaffDob().toString());
        labelAddress.setText(staff.getStaffAddress());
        labelUsrName.setText(staff.getUsrName());
        labelPhoneNum.setText(staff.getStaffPhone());
    }
}
