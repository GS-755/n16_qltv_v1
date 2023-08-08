package com.n16.qltv.frame.staff;

import com.n16.qltv.adaptor.StaffAdapter;
import com.n16.qltv.model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class IndexFrame extends JFrame {
    private JTable tableStaff;
    private JButton btnUpdate;
    private JButton btnAdd, btnEdit, btnDelete, btnExit;
    private JLabel indexTitle, sortTitle, searchLabel;
    public JPanel indexFrame;
    private JButton btnAscUsrName, btnDescUsrName;
    private JTextField txtSearch;
    private JButton btnSearch;
    private ButtonGroup radioSearchModeGroup;
    private JRadioButton approxModeRadio, absoluteModeRadio;
    private JLabel searchModeLabel;
    private DefaultTableModel model;
    private ArrayList<Staff> staffArrayList;
    private final int DELAY_TIME = 1800;

    public IndexFrame() {
        setSearchModeComponents();
        setContentPane(indexFrame);
        setTitle("Danh sách nhân viên");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        staffArrayList = StaffAdapter.getStaffList();

        model = new DefaultTableModel();
        addTableStyle(model);
        addTableData(model, staffArrayList);

        btnDelete.addActionListener(e -> {
            String usrName = model.getValueAt(tableStaff.getSelectedRow(), 5).toString();
            DeleteFrame df = new DeleteFrame(usrName.trim());
            try {
                if(!df.isVisible())
                    refreshTableData();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });
        btnExit.addActionListener(e -> {
            System.exit(3);
        });
        btnAdd.addActionListener(e -> {
            CreateFrame cf = new CreateFrame();
        });
        btnEdit.addActionListener(e -> {
            if(tableStaff.getSelectedRow() < 0)
                JOptionPane.showMessageDialog(null, "Vui lòng chọn đối tượng :((");
            else {
                EditFrame ef = new EditFrame(model.getValueAt(
                        tableStaff.getSelectedRow(), 5).toString());
            }
        });

        btnUpdate.addActionListener(e -> {
            refreshTableData();
        });
        btnAscUsrName.addActionListener(e -> {
            deleteTableData();
            staffArrayList = StaffAdapter.sortUsrName(1);
            //addTableStyle(model);
            addTableData(model, staffArrayList);
        });
        btnDescUsrName.addActionListener(e -> {
            deleteTableData();
            staffArrayList = StaffAdapter.sortUsrName(2);
            //addTableStyle(model);
            addTableData(model, staffArrayList);
        });
        btnSearch.addActionListener( e -> {
            // Kiểm tra chế độ tìm kiếm
            int mode = 1; String keyword = txtSearch.getText().trim();
            if(!(absoluteModeRadio.isSelected()))
                mode = 2;
            deleteTableData();
            staffArrayList = StaffAdapter
                    .findStaffName(mode, keyword);
            addTableData(model, staffArrayList);
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
    public void addTableData(DefaultTableModel model, ArrayList<Staff> staffs) {
        for(Staff staff : staffs)
            model.addRow(new Object[] {
                    staff.getStaffName(),
                    staff.getStrGender(),
                    staff.getStaffPhone(),
                    staff.getStaffAddress(),
                    staff.getStaffDob(),
                    staff.getUsrName()
            });

        tableStaff.setModel(model);
    }
    public void refreshTableData() {
        deleteTableData();
        staffArrayList = StaffAdapter.getStaffList();
        //addTableStyle(model);
        addTableData(model, staffArrayList);
    }
    public void deleteTableData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }
    public void setSearchModeComponents() {
        radioSearchModeGroup = new ButtonGroup();
        radioSearchModeGroup.add(absoluteModeRadio);
        radioSearchModeGroup.add(approxModeRadio);

        absoluteModeRadio.addActionListener(e -> {
            radioSearchModeGroup.clearSelection();
            absoluteModeRadio.setSelected(true);
        });
        approxModeRadio.addActionListener(e -> {
            radioSearchModeGroup.clearSelection();
            approxModeRadio.setSelected(true);
        });
    }


}
