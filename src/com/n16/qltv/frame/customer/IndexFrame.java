package com.n16.qltv.frame.customer;

import com.n16.qltv.daos.CustomerDAO;
import com.n16.qltv.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class IndexFrame extends JFrame{
    private JButton addButton;
    private JButton deleteButton;
    private JButton EditButton;
    private JTextField textField1;
    private JButton searchButton;
    private JButton updateButton;
    private JButton increButton;
    private JButton decreButton;
    private JButton escButton;
    private JTable table1;
    private JRadioButton absoluteModeRadio;
    private DefaultTableModel model;
    private JRadioButton approxModeRadio;
    private JLabel cusLabel;
    private JLabel arangeLabel;
    private JLabel searchLabel;
    private JLabel searchModeLabel;
    private JPanel IndexFrame;
    private ArrayList<Customer> customers;
    private ButtonGroup radioSearchModeGroup;
    private CustomerDAO customerDAO;

    public IndexFrame() {
        this.customerDAO = new CustomerDAO();
        this.customers = this.customerDAO.getListItem();
        setSearchModeComponents();
        setContentPane(IndexFrame);
        setTitle("Danh sách khách hàng");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        model = new DefaultTableModel();
        addTableStyle();
        addTableData(model);
        table1.setModel(model);

        updateButton.addActionListener(e -> refreshTableData());
        addButton.addActionListener(e -> {
            CreateFrame cf = new CreateFrame();
        });
        EditButton.addActionListener(e -> {
            if(table1.getSelectedRow() < 0)
                JOptionPane.showMessageDialog(null, "Vui lòng chọn đối tượng :((");
            else {
                EditFrame ef = new EditFrame(model.getValueAt(
                        table1.getSelectedRow(), 5).toString());
            }
        });
        deleteButton.addActionListener(e -> {
            String id = model.getValueAt(
                    table1.getSelectedRow(), 5).toString();
            this.customerDAO.delete(id.trim());
            refreshTableData();
        });
        escButton.addActionListener(e -> dispose());

        increButton.addActionListener(e -> {
            deleteTableData();
            customers = this.customerDAO.sortUsrName(1);
            addTableData(model);
            model.fireTableDataChanged();
        });
        decreButton.addActionListener(e -> {
            deleteTableData();
            customers = this.customerDAO.sortUsrName(2);
            addTableData(model);
            model.fireTableDataChanged();
        });
    }
    public void addTableStyle() {
        model.addColumn("Tên Độc giả");
        model.addColumn("Giới tính");
        model.addColumn("Số ĐT");
        model.addColumn("Địa chỉ");
        model.addColumn("Ngày sinh");
        model.addColumn("Tên đăng nhập");
    }
    public void addTableData(DefaultTableModel model) {
        for (Customer customer : this.customers)
            model.addRow(new Object[]{
                    customer.getNameCus(),
                    customer.getStrGender(),
                    customer.getPhoneCus(),
                    customer.getAddressCus(),
                    customer.getDobCus(),
                    customer.getUsrName()
            });
    }
    public void refreshTableData() {
        deleteTableData();
        customers = this.customerDAO.getListItem();
        addTableData(model);
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
