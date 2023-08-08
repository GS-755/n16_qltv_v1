package com.n16.qltv.frame.staff;

import com.n16.qltv.adaptor.StaffAdapter;
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

    public DeleteFrame(String usrName) {
        setContentPane(deletePanel);
        setTitle("Xoá Nhân viên");
        setVisible(true);
        setBounds(80, 90, 480, 320);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setComponents(usrName);
        btnDelete.addActionListener(e -> {
            if(StaffAdapter.checkExistStaff(usrName.trim())) {
                StaffAdapter.deleteStaff(usrName.trim());
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
    public void setComponents(String usrName) {
        ArrayList<Staff> foundStaff = StaffAdapter
                .findUsrName(1, usrName);
        labelStaffName.setText(foundStaff.get(0).getStaffName());
        labelGender.setText(foundStaff.get(0).getStrGender());
        labelDob.setText(foundStaff.get(0).getStaffDob());
        labelAddress.setText(foundStaff.get(0).getStaffAddress());
        labelUsrName.setText(foundStaff.get(0).getUsrName());
        labelPhoneNum.setText(foundStaff.get(0).getStaffPhone());
    }
}
