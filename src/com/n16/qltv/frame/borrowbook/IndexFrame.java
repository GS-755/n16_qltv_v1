package com.n16.qltv.frame.borrowbook;

import com.n16.qltv.model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class IndexFrame extends JFrame {
    private JPanel mainPanel;
    private DefaultTableModel model;
    private JTable borrowBookTable;
    private JTextField txtKeyword;
    private JButton btnRefresh;
    private JButton btnAcceptRequest;
    private JButton btnDetails;
    private JButton tìmKiếmButton;
    private JRadioButton rdDateBorrowDesc;
    private JRadioButton rdDateBorrowAsc;
    private JButton btnExit;

    public IndexFrame() {
        setContentPane(mainPanel);
        setVisible(true);
        setResizable(false);
        setBounds(60, 60, 1280, 768);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Quản lý Mượn trả sách");
        this.model = new DefaultTableModel();
        this.addTableStyle(model);
        this.setActionExit();
    }

    public void addTableStyle(DefaultTableModel model) {
        model.addColumn("Mã mượn trả");
        model.addColumn("Mã thẻ TV");
        model.addColumn("Ngày mượn");
        model.addColumn("Người duyệt");
        model.addColumn("Đã trả");
    }
    public void addTableData(DefaultTableModel model, ArrayList<Staff> staffs) {
//        for(Staff staff : staffs)
//            model.addRow(new Object[] {
//                    staff.getStaffName(),
//                    staff.getStrGender(),
//                    staff.getStaffPhone(),
//                    staff.getStaffAddress()
//            });
//
//        tableStaff.setModel(model);
    }
    public void refreshTableData() {
        deleteTableData();
//        staffArrayList = this.staffDAO.getListItem();
//        addTableData(model, staffArrayList);
    }
    public void deleteTableData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }
    private void setActionExit() {
        this.btnExit.addActionListener(e -> {
            this.dispose();
        });
    }
}
