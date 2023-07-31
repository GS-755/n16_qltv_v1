package com.n16.qltv.frame.staff;

import com.n16.qltv.adaptor.StaffAdapter;
import com.n16.qltv.model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IndexFrame extends JFrame {
    private JTable tableStaff;
    private JButton btnUpdate;
    private JButton btnAdd, btnEdit, btnDelete, btnExit;
    private JLabel indexTitle;
    private JPanel indexFrame;
    private ArrayList<Staff> staffArrayList;
    private DefaultTableModel model;

    public IndexFrame() {
        setContentPane(indexFrame);
        setTitle("Danh sách nhân viên");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        staffArrayList = StaffAdapter.getStaffList();

        model = new DefaultTableModel();
        addTableStyle(model);
        addTableData(model);

        btnDelete.addActionListener(e -> {
            StaffAdapter.deleteStaff(model.getValueAt(tableStaff.getSelectedRow(), 5).toString());
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();

            staffArrayList = StaffAdapter.getStaffList();
            //addTableStyle(model);
            addTableData(model);
        });
        btnExit.addActionListener(e -> {
            System.exit(3);
        });
        btnAdd.addActionListener(e -> {
            CreateFrame cf = new CreateFrame();
        });
        btnEdit.addActionListener(e -> {
            EditFrame ef = new EditFrame(model.getValueAt(tableStaff.getSelectedRow(), 5).toString());
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getDataVector().removeAllElements();
                model.fireTableDataChanged();

                staffArrayList = StaffAdapter.getStaffList();
                //addTableStyle(model);
                addTableData(model);
            }
        });
    }
    public void addTableStyle(DefaultTableModel model) {
        model.addColumn("Tên Nhân viên");
        model.addColumn("Giới tính");
        model.addColumn("Số ĐT");
        model.addColumn("Địa chỉ");
        model.addColumn("Ngày sinh");
        model.addColumn("Tên đăng nhập");
    }
    public void addTableData(DefaultTableModel model) {
        for(Staff staff : staffArrayList)
            model.addRow(new Object[] {
                    staff.getStaffName(),
                    staff.getGender(),
                    staff.getStaffPhone(),
                    staff.getStaffAddress(),
                    staff.getStaffDob(),
                    staff.getUsrName()
            });

        tableStaff.setModel(model);
    }
}
