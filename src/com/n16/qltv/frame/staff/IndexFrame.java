package com.n16.qltv.frame.staff;

import com.n16.qltv.daos.StaffDAO;
import com.n16.qltv.frame.borrowbook.BorrowBook;
import com.n16.qltv.model.Staff;
import com.n16.qltv.utils.Session;

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
    private JButton btnBorrowBook;
    private JButton btnToCustomers;
    private JButton btnManageBooks;
    private JLabel tf_NameStaff;
    private JButton btnLogout;
    private DefaultTableModel model;
    private ArrayList<Staff> staffArrayList;
    private StaffDAO staffDAO;


    public IndexFrame() {
        this.staffDAO = new StaffDAO();
        setSearchModeComponents();
        setContentPane(indexFrame);
        setTitle("Danh sách nhân viên");
        setResizable(false);
        setBounds(50, 50, 1024, 768);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.staffArrayList = this.staffDAO.getListItem();
        // info Staff //
        if(Session.get("admin") != null) {
            setVisible(true);
            tf_NameStaff.setText(Session.get("admin").toString());
        }
        else if(Session.get("staff") != null) {
            setVisible(true);
            tf_NameStaff.setText(Session.get("staff").toString());
        }
        else {
            dispose();
            LoginFrame loginFrame = new LoginFrame();
        }

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
               String SaffName  = model.getValueAt(tableStaff.getSelectedRow(), 5).toString();
                EditFrame ef = new EditFrame(SaffName);
            }
        });

        btnUpdate.addActionListener(e -> {
            refreshTableData();
        });
        btnAscUsrName.addActionListener(e -> {
            deleteTableData();
            staffArrayList = this.staffDAO.sortUsrName(1);
            //addTableStyle(model);
            addTableData(model, staffArrayList);
        });
        btnDescUsrName.addActionListener(e -> {
            deleteTableData();
            staffArrayList = this.staffDAO.sortUsrName(2);
            //addTableStyle(model);
            addTableData(model, staffArrayList);
        });
        btnSearch.addActionListener( e -> {
            // Kiểm tra chế độ tìm kiếm
            int mode = 1; String keyword = txtSearch.getText().trim();
            if(!(absoluteModeRadio.isSelected()))
                mode = 2;
            deleteTableData();
            staffArrayList = this.staffDAO.findStaffName(mode, keyword);
            addTableData(model, staffArrayList);
        });
        btnBorrowBook.addActionListener(e -> {
            if(Session.get("staff") == null )  {
                dispose();
                LoginFrame loginFrame = new LoginFrame();
            }
            else  {
                 BorrowBook borrowBook = new BorrowBook();
            }
        });
        btnLogout.addActionListener(e -> {
            tf_NameStaff.setText("");
            Session.remove("staff");
            dispose();
            LoginFrame loginFrame = new LoginFrame();
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
        staffArrayList = this.staffDAO.getListItem();
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
