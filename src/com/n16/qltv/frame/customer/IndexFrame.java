package com.n16.qltv.frame.customer;

import com.n16.qltv.adaptor.CustomerAdapter;
import com.n16.qltv.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class IndexFrame extends JFrame{
    private JButton addButton;
    private JButton deleteButton;
    private JButton EditButton;
    private JTextField txtKeyword;
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
    public IndexFrame() {
        this.customers = CustomerAdapter.getCustoList();
        setSearchModeComponents();
        setContentPane(IndexFrame);
        setTitle("Danh sách khách hàng");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 1024, 768);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        model = new DefaultTableModel();
        addTableStyle();
        addTableData(model, this.customers);
        table1.setModel(model);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTableData();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateFrame cf = new CreateFrame();
                refreshTableData();
            }
        });
        EditButton.addActionListener(e -> {
            if(table1.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null,
                        "Vui lòng chọn đối tượng :((");
            }
            else if(!CustomerAdapter.checkExistCustomer(model.getValueAt(
                    table1.getSelectedRow(), 5).toString().trim())) {
                JOptionPane.showMessageDialog(null,
                        "Lỗi đối tượng, vui lòng đợi bản cập nhật sau :)");
            }
            else {
                EditFrame ef = new EditFrame(model.getValueAt(
                        table1.getSelectedRow(), 5).toString());
                refreshTableData();
            }
        });
        deleteButton.addActionListener(e -> {
            String usrName = model.getValueAt(
                    table1.getSelectedRow(), 5).toString().trim();
            CustomerAdapter.deleteCustomer(usrName);
            refreshTableData();
        });
        searchButton.addActionListener(e -> {
            String keyword = this.txtKeyword.getText().trim();
            deleteTableData();
            if(this.absoluteModeRadio.isSelected()) {
                this.customers = CustomerAdapter.findCustoName(1, keyword);
            }
            else {
                this.customers = CustomerAdapter.findCustoName(2, keyword);
            }
            addTableData(model, customers);
            model.fireTableDataChanged();
        });
        escButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        increButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customers = CustomerAdapter.sortUsrName(1);
                deleteTableData();
                addTableData(model, customers);
                model.fireTableDataChanged();
            }
        });
        decreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customers = CustomerAdapter.sortUsrName(2);
                deleteTableData();
                addTableData(model, customers);
                model.fireTableDataChanged();
            }
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
    public void addTableData(DefaultTableModel model, ArrayList<Customer> customers) {
        this.customers = customers;
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
        this.customers = CustomerAdapter.getCustoList();
        addTableData(model, this.customers);
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
