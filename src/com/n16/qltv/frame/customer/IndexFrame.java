package com.n16.qltv.frame.customer;

import com.n16.qltv.daos.CategoryDAO;
import com.n16.qltv.daos.CustomerDAO;
import com.n16.qltv.model.Category;
import com.n16.qltv.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class IndexFrame extends JFrame{
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JTextField txtSearchKeyword;
    private JButton searchButton;
    private JButton updateButton;
    private JButton btnIncrease;
    private JButton btnDecrease;
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
    private ArrayList<Customer> customerArrayList;
    private ArrayList<Customer> foundCustomerArrayList;
    private ButtonGroup radioSearchModeGroup;
    private CustomerDAO customerDAO;

    public IndexFrame() {
        this.customerDAO = new CustomerDAO();
        this.foundCustomerArrayList = new ArrayList<>();
        this.customerArrayList = this.customerDAO.getListItem();
        setSearchModeComponents();
        setContentPane(IndexFrame);
        setTitle("Danh sách khách hàng");
        setVisible(true);
        setResizable(false);
        setBounds(50, 50, 1024, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model = new DefaultTableModel();
        addTableStyle();
        addTableData(this.customerArrayList);
        table1.setModel(model);
        this.setActionSearchCustomer();

        updateButton.addActionListener(e -> {
            this.refreshTableData();
            this.customerArrayList = this.customerDAO.getListItem();
            this.addTableData(this.customerArrayList);
        });
        addButton.addActionListener(e -> {
            CreateFrame cf = new CreateFrame();
        });
        editButton.addActionListener(e -> {
            if(table1.getSelectedRow() < 0)
                JOptionPane.showMessageDialog(null, "Vui lòng chọn đối tượng :((");
            else {
                String customerUserName = model.getValueAt(
                        table1.getSelectedRow(), 4).toString();
                Customer customer = this.customerDAO.getItem(customerUserName);
                EditFrame editFrame = new EditFrame(customer);
                editFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        super.windowDeactivated(e);
                        refreshTableData();
                        customerArrayList = customerDAO.getListItem();
                        addTableData(customerArrayList);
                    }
                });
            }
        });
        deleteButton.addActionListener(e -> {
            String id = model.getValueAt(
                    table1.getSelectedRow(), 4).toString();
            this.customerDAO.delete(id.trim());
            refreshTableData();
            this.customerArrayList = this.customerDAO.getListItem();
            addTableData(this.customerArrayList);
        });
        escButton.addActionListener(e -> System.exit(0));

        btnIncrease.addActionListener(e -> {
            deleteTableData();
            customerArrayList = this.customerDAO.sortUsrName(1);
            addTableData(this.customerArrayList);
            model.fireTableDataChanged();
        });
        btnDecrease.addActionListener(e -> {
            deleteTableData();
            customerArrayList = this.customerDAO.sortUsrName(2);
            addTableData(this.customerArrayList);
            model.fireTableDataChanged();
        });
    }
    public void addTableStyle() {
        model.addColumn("Tên Độc giả");
        model.addColumn("Giới tính");
        model.addColumn("Số ĐT");
        model.addColumn("Địa chỉ");
        model.addColumn("Tên đăng nhập");
    }
    public void addTableData(ArrayList<Customer> customers) {
        for (Customer customer : customers)
            model.addRow(new Object[]{
                    customer.getNameCus(),
                    customer.getStrGender(),
                    customer.getPhoneCus(),
                    customer.getAddressCus(),
                    customer.getUsrName()
            });
    }
    public void refreshTableData() {
        deleteTableData();
        this.customerArrayList.clear();
        this.foundCustomerArrayList.clear();
    }
    public void setActionSearchCustomer() {
        this.searchButton.addActionListener(e -> {
            String keyword = this.txtSearchKeyword.getText().toString().trim();
            this.refreshTableData();
            if(this.approxModeRadio.isSelected()) {
                this.foundCustomerArrayList = this.customerDAO.
                        findCustomerByName(keyword.toLowerCase().trim(), 2);
            }
            else {
                this.foundCustomerArrayList = this.customerDAO.
                        findCustomerByName(keyword.trim(), 1);
            }
            this.addTableData(this.foundCustomerArrayList);
        });


        // tìm kiếm
        txtSearchKeyword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String keyword = txtSearchKeyword.getText().toLowerCase().trim();
                if(keyword.isEmpty() || keyword.equals("") || keyword.isBlank() || keyword.length() == 0)
                {
                    deleteTableData();
                    customerArrayList = customerDAO.getListItem();
                    addTableData(customerArrayList);

                } else {
                    customerArrayList.clear();
                    deleteTableData();
                    ArrayList<Customer> foundCus = new ArrayList<>();
                    customerArrayList = customerDAO.getListItem();
                    for (Customer cus : customerArrayList) {
                        if(cus.getUsrName().contains(keyword))
                        {
                            foundCus.add(cus);
                        }
                    }
                    addTableData(foundCus);
                }
            }
        });

    }
    public void deleteTableData() {
        model.setRowCount(0);
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
