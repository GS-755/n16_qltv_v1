package com.n16.qltv.frame.customer;

import com.n16.qltv.adaptor.CustomerAdapter;
import com.n16.qltv.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TestTable extends JFrame {
    private JPanel testPanel;
    private JTable testTable;
    private DefaultTableModel model;
    private ArrayList<Customer> customers = new ArrayList<>();

    public TestTable() {
        setContentPane(testPanel);
        setVisible(true);
        setResizable(false);
        setBounds(60, 60, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Test");
        model = new DefaultTableModel();
        addTableStyle();
        addTableData();
        testTable.setModel(model);
    }
    public void addTableStyle() {
        model.addColumn("MaDocGia");
        model.addColumn("TenDocGia");
        model.addColumn("DiaChi");
        model.addColumn("SoDT");
        model.addColumn("TenDangNhap");
        model.addColumn("GioiTinh");

    }
    public void addTableData() {
        customers = CustomerAdapter.getCustoList();
        for(Customer customer : customers) {
            model.addRow(new Object[] {
                    customer.getIdcus(),
                    customer.getNameCus(),
                    customer.getAddressCus(),
                    customer.getPhoneCus(),
                    customer.getUsrName(),
                    customer.getStrGender() });
        }
    }
    public void deleteTableData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }
}
